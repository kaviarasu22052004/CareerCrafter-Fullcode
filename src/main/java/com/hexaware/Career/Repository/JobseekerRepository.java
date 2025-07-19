package com.hexaware.Career.Repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.hexaware.Career.Entity.JobSeeker;

@Repository
public interface JobseekerRepository extends JpaRepository<JobSeeker,Integer> {

	Optional<JobSeeker> findByUserEmail(String email);

	
	
}
