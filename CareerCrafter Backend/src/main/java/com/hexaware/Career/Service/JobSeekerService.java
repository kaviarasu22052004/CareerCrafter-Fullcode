package com.hexaware.Career.Service;

import java.util.List;

import com.hexaware.Career.DTO.JobSeekerDTO;

public interface JobSeekerService {
    JobSeekerDTO createJobSeeker(JobSeekerDTO jobSeekerDTO);
    JobSeekerDTO updateJobSeeker(JobSeekerDTO jobSeekerDTO, int id);
    JobSeekerDTO getJobSeekerById(int id);
    List<JobSeekerDTO> getAllJobSeekers();
    void deleteJobSeeker(int id);
     JobSeekerDTO getByEmail(String email);
}
