package com.project.team;

import com.project.team.User.UserCreateForm;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class MainController {

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
        String target = ((JSONObject)((JSONArray)((JSONObject)((JSONArray)((JSONObject)basicInfo.get("openHour")).get("periodList")).get(0)).get("timeList")).get(0)).get("timeSE").toString();
        model.addAttribute("data", detail);
        return "test";
    }

    @GetMapping("/process")
    public String interProcess() {
        return "interprocess";
    }
}
