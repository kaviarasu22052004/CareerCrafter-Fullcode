package com.hexaware.Career.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.Career.Entity.Application;
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
List<Application> findByJobListingId(int jobListingId);
List<Application> findByJobSeeker_JobSeekerId(int jobSeekerId);


}
