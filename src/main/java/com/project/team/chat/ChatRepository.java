package com.project.team.chat;

import com.project.team.User.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByRoom(String room);
    @Query("SELECT c FROM Chat c WHERE writer = :user AND type = 'info'")
    List<Chat> findChatRoom(@Param("user") SiteUser user);
    List<Chat> findByRoomAndType(String room, String type);
    List<Chat> findByRoomAndTypeAndWriter(String room, String type, SiteUser user);
}
