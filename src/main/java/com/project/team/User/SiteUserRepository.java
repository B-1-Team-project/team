package com.project.team.User;

import com.project.team.User.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SiteUserRepository extends JpaRepository<SiteUser, Integer> {
    Optional<SiteUser> findByLoginId(String LoginId);
    SiteUser findByNameAndEmail(String name, String email);
}
