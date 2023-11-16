package com.project.team;

import com.project.team.Restaurant.Restaurant;
import com.project.team.Restaurant.RestaurantRepository;
import com.project.team.Restaurant.RestaurantService;
import com.project.team.User.SiteUser;
import com.project.team.User.SiteUserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest

class TeamApplicationTests {
	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private SiteUserRepository siteUserRepository;


	@Test

	void contextLoads() {
		SiteUser owner = new SiteUser();
		this.siteUserRepository.save(owner);


		Restaurant r1 = new Restaurant();
		r1.setName("설해돈");
		r1.setAddress("라온채빌딩");
		r1.setNumber("0507-1385-1119");
		r1.setOwner(owner);
		r1.setRegDate(LocalDateTime.now());
		r1.setMain("모듬가스");








		this.restaurantRepository.save(r1);
	}

}
