package com.hexaware.Career.Service;

import java.util.List;

import com.hexaware.Career.DTO.ApplicationDTO;

public interface ApplicationService {
    ApplicationDTO applyForJob(ApplicationDTO applicationDTO);
    ApplicationDTO updateApplicationStatus(int id, String status);
    ApplicationDTO getApplicationById(int id);
    List<ApplicationDTO> getApplicationsByJobListingId(int jobListingId);
    List<ApplicationDTO> getApplicationsByJobSeekerId(int jobSeekerId);
    void deleteApplication(int id);

}
