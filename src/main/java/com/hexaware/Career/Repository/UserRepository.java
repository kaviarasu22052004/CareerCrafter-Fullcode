package com.hexaware.Career.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.Career.Entity.User;
@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
	 Optional<User> findByEmail(String email); 

}
