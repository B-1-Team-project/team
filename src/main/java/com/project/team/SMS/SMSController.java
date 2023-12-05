package com.project.team.SMS;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RequiredArgsConstructor
public class SMSController {

    private final SMSService smsService;

    public String createRandomNumber() {
        Random rand = new Random();
        String randomNum = "";
        for (int i = 0; i < 4; i++) {
            String random = Integer.toString(rand.nextInt(10));
            randomNum += random;
        }

        return randomNum;
    }

    @GetMapping("/send")
    public String send(@RequestParam String phoneNumber, HttpSession session) {
        String verKey = createRandomNumber();
        smsService.sendMessage(phoneNumber, verKey);

        session.setAttribute("verKey", verKey);

        return "redirect:/";
    }


    @GetMapping("/sendVerKey")
    public ResponseEntity<Map<String, String>> getVerificationKey(HttpSession session) {
        String verKey = (String) session.getAttribute("verKey");

        Map<String, String> response = new HashMap<>();
        response.put("verKey", verKey);
        return ResponseEntity.ok(response);
    }

}
