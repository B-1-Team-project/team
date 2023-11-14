package com.project.team;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface SiteUserRepository extends JpaRepository<SiteUser,Long> {
    Optional<SiteUser> findByLoginId(String loginId);
}
