package com.project.team.alarm;

import com.project.team.User.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;

    public List<Alarm> getByUser(SiteUser user) {
        return this.alarmRepository.findByUser(user);
    }

    public void create(SiteUser user, SiteUser target, String type) {
        Alarm alarm = new Alarm();
        alarm.setUser(user);
        alarm.setTarget(target);
        alarm.setType(type);
        alarmRepository.save(alarm);
    }
}
