package com.hexaware.Career.Service;

import java.util.List;

import com.hexaware.Career.DTO.JobListingDTO;

public interface JobListingService {
    JobListingDTO createJobListing(JobListingDTO jobListingDTO);
    JobListingDTO updateJobListing(JobListingDTO jobListingDTO, int id);
    JobListingDTO getJobListingById(int id);
    List<JobListingDTO> getAllJobListings();
    void deleteJobListing(int id);
    List<JobListingDTO> getJobListingByTitle(String title);
    List<JobListingDTO> getJobListingsByEmployerId(int employerId) ;

}
