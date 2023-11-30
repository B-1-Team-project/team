package com.project.team;

import com.project.team.User.SiteUser;
import com.project.team.User.SiteUserService;
import com.project.team.User.UserCreateForm;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final SiteUserService siteUserService;

    @GetMapping("/")
    public String index(UserCreateForm userCreateForm) {
        return "start";
    }

    @GetMapping("/main")
    public String mainPage() {
        return "redirect:/interprocess";
    }

    @GetMapping("/test")
    public String test(Model model) throws IOException, ParseException {
        String doc = Jsoup.connect("https://place.map.kakao.com/main/v/10344306").ignoreContentType(true).execute().body();
        JSONParser jsonParser = new JSONParser();
        JSONObject detail = (JSONObject) jsonParser.parse(doc);
        JSONObject basicInfo = (JSONObject) detail.get("basicInfo");
        String target = ((JSONObject) ((JSONArray) ((JSONObject) ((JSONArray) ((JSONObject) basicInfo.get("openHour")).get("periodList")).get(0)).get("timeList")).get(0)).get("timeSE").toString();
        model.addAttribute("data", detail);
        return "test";
    }

    @GetMapping("/process")
    public String interProcess() {
        return "interprocess";
    }


    @GetMapping("/weatherMenu")
    public String weatherMenu(Model model, Principal principal) {
        if (principal != null) {
            SiteUser user = this.siteUserService.getUser(principal.getName());
            model.addAttribute("user", user);
        }
        return "weatherMenu";
    }

    @PostMapping("/recommendMenu")
    public ResponseEntity<String> recommendMenu(@RequestBody List<Map<String, String>> dataList) {
        // 처리 로직
        String jsonResponse = "{ \"message\": \"Success\" }";
        return ResponseEntity.ok(jsonResponse);
    }

}
