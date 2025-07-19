package com.hexaware.Career.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.Career.Entity.JobListing;
@Repository
public interface JobListingRepository extends JpaRepository<JobListing,Integer>{
	
	List<JobListing> findByTitleContainingIgnoreCase(String title);
	List<JobListing> findByEmployer_EmployeeId(Integer employerId);


}
