package com.praveen.UserService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.praveen.UserService.models.User;

@Repository
public interface UserServiceRepository extends JpaRepository<User,Long>{
	@Override
	User save(User user);

	User findUserByEmail(String email);
}
