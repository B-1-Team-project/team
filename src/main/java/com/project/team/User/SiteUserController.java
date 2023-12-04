package com.project.team.User;

import com.project.team.DataNotFoundException;
import com.project.team.Reservation.Reservation;
import com.project.team.Reservation.ReservationService;
import com.project.team.Restaurant.Restaurant;
import com.project.team.Restaurant.RestaurantService;
import com.project.team.chat.ChatService;
import com.project.team.test.MailDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class SiteUserController {
    private final SiteUserService siteUserService;
    private final ReservationService reservationService;
    private final RestaurantService restaurantService;
    private final ChatService chatService;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;


    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("signupError", "에러 메시지");
            return "start";
        }
        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
            model.addAttribute("signupError", "에러 메시지");
            return "start";
        }
        try {
            siteUserService.create(userCreateForm.getLoginId(), userCreateForm.getPassword1(), userCreateForm.getName(), userCreateForm.getEmail(), userCreateForm.getAuthority());
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자 입니다.");
            model.addAttribute("signupError", "에러 메시지");
            return "start";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            model.addAttribute("signupError", "에러 메시지");
            return "start";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(UserCreateForm userCreateForm) {
        return "start";
    }

    @GetMapping("/userDetail/{loginId}")
    public String userDetail(Model model, @PathVariable("loginId") String loginId, Principal principal) {
        SiteUser siteUser = this.siteUserService.getUser(loginId);
        SiteUser loginUser = this.siteUserService.getUser(principal.getName());
        List<Reservation> userReservation = this.reservationService.getAllByUser(siteUser);
        model.addAttribute("userReservation", userReservation);
        model.addAttribute("user", siteUser);
        model.addAttribute("loginUser", loginUser);
        return "userDetail";
    }

    @GetMapping("/userModify/{loginId}")
    @PreAuthorize("isAuthenticated()")
    public String userModify(Model model, UserModifyForm userModifyForm, @PathVariable("loginId") String loginId, Principal principal) {
        SiteUser siteUser = this.siteUserService.getUser(principal.getName());
        model.addAttribute("user", siteUser);
        if (!siteUser.getLoginId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        userModifyForm.setAuthority(siteUser.getAuthority());
        userModifyForm.setName(siteUser.getName());
        userModifyForm.setEmail(siteUser.getEmail());
        return "userModify";
    }

    @GetMapping("/snsUserModify/{loginId}")
    @PreAuthorize("isAuthenticated()")
    public String snsUserModify(Model model, SnsUserModifyForm snsUserModifyForm, @PathVariable("loginId") String loginId, Principal principal){
        SiteUser user = this.siteUserService.getUser(principal.getName());
        model.addAttribute("user", user);
        if (!user.getLoginId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        snsUserModifyForm.setAuthority(user.getAuthority());
        return "snsUserModify";
    }

    @PostMapping("/snsUserModify/{loginId}")
    @PreAuthorize("isAuthenticated()")
    public String snsUserModify(Model model, @Valid SnsUserModifyForm snsUserModifyForm, BindingResult bindingResult, @PathVariable("loginId") String loginId, Principal principal){
        SiteUser user = siteUserService.getUser(loginId);
        model.addAttribute("user", user);
        if(bindingResult.hasErrors()){
            return "snsUserModify";
        }
        siteUserService.saveAuthority(user, snsUserModifyForm.getAuthority());
        return "userDetail";
    }

    @PostMapping("/userModify/{loginId}")
    @PreAuthorize("isAuthenticated()")
    public String userModify(Model model, @Valid UserModifyForm userModifyForm, BindingResult bindingResult, @PathVariable("loginId") String loginId, Principal principal) {
        SiteUser siteUser = siteUserService.getUser(loginId);
        model.addAttribute("user", siteUser);
        if (bindingResult.hasErrors()) {
            return "userModify";
        }
        if (!siteUser.getLoginId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        if (!userModifyForm.getPassword1().equals(userModifyForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
            return "userModify";
        }
        this.siteUserService.modifyUser(siteUser, userModifyForm.getName(), userModifyForm.getEmail(), userModifyForm.getPassword1(), userModifyForm.getAuthority());
        return String.format("redirect:/user/userDetail/%s", siteUser.getLoginId());
    }


    @GetMapping("/userCheckPassword/{loginId}")
    @PreAuthorize("isAuthenticated()")
    public String userCheckPassword(Model model, @PathVariable("loginId") String loginId, Principal principal) {
        SiteUser siteUser = this.siteUserService.getUser(loginId);
        if (!siteUser.getLoginId().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        model.addAttribute("loginId", loginId);
        return "userDetail";
    }

    @PostMapping("/userCheckPassword/{loginId}")
    @PreAuthorize("isAuthenticated()")
    public String userCheckPassword(Model model, @PathVariable("loginId") String loginId, Principal principal, String password) {
        SiteUser siteUser = siteUserService.getUser(principal.getName());
        if (passwordEncoder.matches(password, siteUser.getPassword())) {
            return "redirect:/user/userModify/" + loginId;
        } else {
            List<Reservation> userReservation = this.reservationService.getAllByUser(siteUser);
            model.addAttribute("userReservation", userReservation);
            model.addAttribute("user", siteUser);
            model.addAttribute("loginId", loginId);
            model.addAttribute("error", true);
            return "userDetail";
        }
    }

    @GetMapping("/findIdAndPw")
    public String findIdAndPw() {
        return "findIdAndPw";
    }

    @PostMapping("/sendEmail")
    public String findPw(Model model, String loginId) {
        try {
            if (this.siteUserService.findUserByLoginID(loginId) == null) {
                model.addAttribute("idError", "가입되지 않은 아이디입니다.");
                return "findIdAndPw";
            }

            String email = siteUserService.getUser(loginId).getEmail();
            MailDto dto = siteUserService.createMail(email);
            siteUserService.sendPasswordResetEmail(loginId);

            model.addAttribute("emailSent", true);

            return "findIdAndPw";
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return "findIdAndPw";
        }
    }

    @GetMapping("/resetPassword/{token}")
    public String showResetPasswordForm(UserResetPwForm userResetPwForm, Model model) {
        SiteUser user = siteUserService.getUserByToken(userResetPwForm.getToken());
        model.addAttribute("token", userResetPwForm.getToken());
        return "resetPasswordForm";

    }

    @PostMapping("/resetPassword/{token}")
    public String resetPassword(@Valid UserResetPwForm userResetPwForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "resetPasswordForm";
        }

        if (!userResetPwForm.getPassword1().equals(userResetPwForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 비밀번호가 일치하지 않습니다.");
            model.addAttribute("token", userResetPwForm.getToken());
            return "resetPasswordForm";
        }


        try {
            // 비밀번호를 재설정
            siteUserService.resetPassword(userResetPwForm.getToken(), userResetPwForm.getPassword1());
            SiteUser user = siteUserService.getUserByToken(userResetPwForm.getToken());
            user.setToken(siteUserService.createToken(user.getLoginId()));

            return "redirect:/"; // 비밀번호 재설정이 성공한 경우 로그인 페이지로 리다이렉트

        } catch (DataNotFoundException e) {
            // 토큰이 유효하지 않은 경우 처리
            bindingResult.reject("token.invalid", "토큰이 유효하지 않습니다.");
            return "resetPasswordForm";
        } catch (Exception e) {
            // 기타 예외 처리
            bindingResult.reject("password.reset.error", "비밀번호 재설정 중 오류가 발생했습니다.");
            return "resetPasswordForm";
        }
    }

    @PostMapping("/findId")
    public String findId(Model model, String email) {
        try {
            if (siteUserService.getUserByEmail(email) == null) {
                model.addAttribute("emailError", "가입되지 않은 이메일입니다.");
                return "findIdAndPw";
            }
            SiteUser user = siteUserService.getUserByEmail(email);
            model.addAttribute("user", user);
            return "findIdAndPw";
        } catch (DataNotFoundException e) {
            e.printStackTrace();
            return "findIdAndPw";
        }
    }

    @GetMapping("/favorite/{restaurantId}")
    @PreAuthorize("isAuthenticated()")
    public String favorite(@PathVariable("restaurantId") Integer restaurantId, Principal principal, Model model) {
        SiteUser user = siteUserService.getUser(principal.getName());
        Restaurant restaurant = restaurantService.getRestaurant(restaurantId);
        siteUserService.toggleFavorite(user, restaurant);
        System.out.println(user.getFavorite());

        return "redirect:/restaurant/detail/" + restaurantId;
    }


    @GetMapping("/selectAuthority")
    public String selectAuthority(UserSelectForm userSelectForm, Principal principal, Model model) {
        SiteUser user = siteUserService.getUser(principal.getName());
        model.addAttribute("user", user);
        return "selectAuthority";
    }

    @PostMapping("/selectAuthority/{loginId}")
    @PreAuthorize("isAuthenticated()")
    public String selectAuthority(@PathVariable("loginId") String loginId, @Valid UserSelectForm userSelectForm, BindingResult bindingResult, Model model) {
        SiteUser user = siteUserService.getUser(loginId);
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return "selectAuthority";
        }
        this.siteUserService.saveAuthority(user, userSelectForm.getAuthority());
        return "redirect:/interprocess";
    }

    //테스트용 코드
    @GetMapping("/test2")
    public String test2() {
        return "test2";
    }
}