package com.hexaware.Career.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.Career.DTO.ApplicationDTO;
import com.hexaware.Career.Entity.Application;
import com.hexaware.Career.Entity.JobListing;
import com.hexaware.Career.Entity.JobSeeker;
import com.hexaware.Career.Entity.Resume;
import com.hexaware.Career.Enum.ApplicationStatus;
import com.hexaware.Career.Exception.ResourceNotFoundException;
import com.hexaware.Career.Mappper.ApplicationMapper;
import com.hexaware.Career.Repository.ApplicationRepository;
import com.hexaware.Career.Repository.JobListingRepository;
import com.hexaware.Career.Repository.JobseekerRepository;
import com.hexaware.Career.Repository.ResumeRepository;

@Service
public class ApplicationServiceImplementation implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobListingRepository jobListingRepository;

    @Autowired
    private JobseekerRepository jobSeekerRepository;

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private ApplicationMapper applicationMapper;

    @Override
    public ApplicationDTO applyForJob(ApplicationDTO applicationDTO) {
        Application application = applicationMapper.dtoToEntity(applicationDTO);

        JobListing jobListing = jobListingRepository.findById(applicationDTO.getJobListingId())
            .orElseThrow(() -> new ResourceNotFoundException("Job Listing not found with id: " + applicationDTO.getJobListingId()));
        application.setJobListing(jobListing);

        JobSeeker jobSeeker = jobSeekerRepository.findById(applicationDTO.getJobSeekerId())
            .orElseThrow(() -> new ResourceNotFoundException("Job Seeker not found with id: " + applicationDTO.getJobSeekerId()));
        application.setJobSeeker(jobSeeker);

        Resume resume = resumeRepository.findById(applicationDTO.getResumeId())
            .orElseThrow(() -> new ResourceNotFoundException("Resume not found with id: " + applicationDTO.getResumeId()));
        application.setResume(resume);

        application.setStatus(ApplicationStatus.APPLIED);

        Application savedApplication = applicationRepository.save(application);
        return applicationMapper.entityToDTO(savedApplication);
    }

    @Override
    public ApplicationDTO updateApplicationStatus(int id, String status) {
        Application existing = applicationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
        existing.setStatus(ApplicationStatus.valueOf(status));
        Application updated = applicationRepository.save(existing);
        return applicationMapper.entityToDTO(updated);
    }

    @Override
    public ApplicationDTO getApplicationById(int id) {
        Application application = applicationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
        return applicationMapper.entityToDTO(application);
    }

    @Override
    public List<ApplicationDTO> getApplicationsByJobListingId(int jobListingId) {
        List<Application> applications = applicationRepository.findByJobListingId(jobListingId);
        List<ApplicationDTO> dtos = new ArrayList<>();
        for (Application a : applications) {
            dtos.add(applicationMapper.entityToDTO(a));
        }
        return dtos;
    }

    @Override
    public List<ApplicationDTO> getApplicationsByJobSeekerId(int jobSeekerId) {
        List<Application> applications = applicationRepository.findByJobSeeker_JobSeekerId(jobSeekerId);
        List<ApplicationDTO> dtos = new ArrayList<>();
        for (Application a : applications) {
            dtos.add(applicationMapper.entityToDTO(a));
        }
        return dtos;
    }

    @Override
    public void deleteApplication(int id) {
        Application application = applicationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
        applicationRepository.delete(application);
    }
}
