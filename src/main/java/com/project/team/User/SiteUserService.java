package com.project.team.User;

import com.project.team.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SiteUserService {
    private final SiteUserRepository siteUserRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String loginId, String password, String name, String email, String authority){
        SiteUser siteUser = new SiteUser();
        siteUser.setLoginId(loginId);
        siteUser.setName(name);
        siteUser.setEmail(email);
        siteUser.setPassword(passwordEncoder.encode(password));
        siteUser.setCreateDate(LocalDateTime.now());
        siteUser.setAuthority(authority);
        siteUserRepository.save(siteUser);
        return siteUser;
    }

    public SiteUser getUser(String loginId) {
        Optional<SiteUser> user = this.siteUserRepository.findByLoginId(loginId);
        if (user.isPresent()) {
            return user.get();
        } else
            throw new DataNotFoundException("user not found");
    }

    public void modifyUser(SiteUser siteUser,  String name, String email, String password, String authority){

        siteUser.setPassword(passwordEncoder.encode(password));
        siteUser.setName(name);
        siteUser.setEmail(email);
        siteUser.setAuthority(authority);

        this.siteUserRepository.save(siteUser);
    }

    public SiteUser getTestUser() {
        Optional<SiteUser> user = this.siteUserRepository.findByLoginId("testUser");
        if (user.isPresent()) {
            return user.get();
        } else return create("testUser", "temp", "익명", null, "손님");
    }

}
