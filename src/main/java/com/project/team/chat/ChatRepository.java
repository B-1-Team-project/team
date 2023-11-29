package com.project.team.chat;

import com.project.team.User.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findByRoom(String room);
    List<Chat> findByRoomAndTarget(String room, SiteUser target);

    @Query("SELECT c FROM Chat c WHERE writer = :user OR target = :user GROUP BY room")
    List<Chat> findChatRoom(@Param("user") SiteUser user);

}
