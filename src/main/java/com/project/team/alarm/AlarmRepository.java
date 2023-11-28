package com.project.team.alarm;

import com.project.team.User.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
    List<Alarm> findByUser(SiteUser user);
}
