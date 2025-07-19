package com.hexaware.Career.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexaware.Career.Entity.Resume;
@Repository
public interface ResumeRepository extends JpaRepository<Resume,Integer>{
	@Query("SELECT r FROM Resume r WHERE r.jobSeeker.jobSeekerId = :jobSeekerId")
Resume findByJobSeekerId(@Param("jobSeekerId") int jobSeekerId);


}
