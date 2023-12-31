package com.project.team.alarm;

import com.project.team.User.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public List<Alarm> getByUser(SiteUser user) {
        return this.alarmRepository.findByUserOrderByRegDateDesc(user);
    }
    public List<Alarm> get(SiteUser user, SiteUser target, String type, Boolean confirm){
        return this.alarmRepository.findByUserAndTargetAndTypeAndConfirm(user, target, type, confirm);
    }

    public List<Alarm> getByRoom(SiteUser user, String room) {
        return this.alarmRepository.findByUserAndChatRoom(user, room);
    }

    public void create(SiteUser user, SiteUser target, String type, String room) {
        Alarm alarm = new Alarm();
        alarm.setUser(user);
        alarm.setTarget(target);
        alarm.setType(type);
        alarm.setRegDate(LocalDateTime.now());
        alarm.setConfirm(false);
        alarm.setChatRoom(room);
        alarmRepository.save(alarm);
        simpMessagingTemplate.convertAndSend("/topic/main/" + user.getLoginId(), "alarm");
    }

    public void confirm(Alarm alarm) {
        alarm.setConfirm(true);
        alarmRepository.save(alarm);
    }

    public void delete(Alarm alarm) {
        alarmRepository.delete(alarm);
    }

    public void deleteAll(List<Alarm> alarmList) {
        alarmRepository.deleteAll(alarmList);
    }

    public List<Alarm> getAllByRoom(String room) {
        return alarmRepository.findByChatRoom(room);
    }
}
