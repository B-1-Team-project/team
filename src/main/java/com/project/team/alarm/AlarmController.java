package com.project.team.alarm;

import com.project.team.User.SiteUser;
import com.project.team.User.SiteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {
    private final AlarmService alarmService;
    private final SiteUserService userService;

    @PostMapping("/confirm")
    @PreAuthorize("isAuthenticated()")
    @ResponseBody
    public void confirm(String room, Principal principal) {
        SiteUser user = userService.getUser(principal.getName());
        List<Alarm> alarmList = alarmService.getByRoom(user, room);
        for (Alarm alarm : alarmList) alarmService.confirm(alarm);
    }
}
