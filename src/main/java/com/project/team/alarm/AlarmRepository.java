package com.project.team.alarm;

import com.project.team.User.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
    List<Alarm> findByUserOrderByRegDateDesc(SiteUser user);
    List<Alarm> findByUserAndTargetAndTypeAndConfirm(SiteUser user, SiteUser target, String type, Boolean confirm);
    List<Alarm> findByUserAndChatRoom(SiteUser user, String room);
    List<Alarm> findByChatRoom(String room);
}
