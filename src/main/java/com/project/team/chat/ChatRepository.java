package com.project.team.chat;

import com.project.team.User.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByRoom(String room);
    List<Chat> findByRoomAndTarget(String room, SiteUser target);
}
