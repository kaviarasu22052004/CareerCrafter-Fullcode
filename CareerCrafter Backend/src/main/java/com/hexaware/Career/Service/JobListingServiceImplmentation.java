package com.hexaware.Career.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.Career.DTO.JobListingDTO;
import com.hexaware.Career.Entity.Employee;
import com.hexaware.Career.Entity.JobListing;
import com.hexaware.Career.Exception.ResourceNotFoundException;
import com.hexaware.Career.Mappper.JobListingMapper;
import com.hexaware.Career.Repository.EmployeeRepository;
import com.hexaware.Career.Repository.JobListingRepository;
@Service
public class JobListingServiceImplmentation implements JobListingService {

    @Autowired
    private JobListingRepository jobListingRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JobListingMapper jobListingMapper;

    @Override
    public JobListingDTO createJobListing(JobListingDTO jobListingDTO) {
        JobListing jobListing = jobListingMapper.dtoToEntity(jobListingDTO);

        Employee employer = employeeRepository.findById(jobListingDTO.getEmployerId()).orElse(null);
        if (employer == null) {
            throw new ResourceNotFoundException("Employee not found with id: " + jobListingDTO.getEmployerId());
        }
        jobListing.setEmployer(employer);

        JobListing savedJobListing = jobListingRepository.save(jobListing);
        return jobListingMapper.entityToDTO(savedJobListing);
    }

    
    @Override
    public List<JobListingDTO> getJobListingsByEmployerId(int employerId) {
        List<JobListing> jobs = jobListingRepository.findByEmployer_EmployeeId(employerId);
        return jobs.stream().map(jobListingMapper::entityToDTO).collect(Collectors.toList());
    }
    
    
    @Override
    public JobListingDTO updateJobListing(JobListingDTO jobListingDTO, int id) {
        JobListing existing = jobListingRepository.findById(id).orElse(null);
        if (existing == null) {
            throw new ResourceNotFoundException("Job Listing not found with id: " + id);
        }

        existing.setTitle(jobListingDTO.getTitle());
        existing.setDescription(jobListingDTO.getDescription());
        existing.setQualifications(jobListingDTO.getQualifications());
        existing.setLocation(jobListingDTO.getLocation());
        existing.setPostDate(jobListingDTO.getPostDate());
        existing.setSalary(jobListingDTO.getSalary());

        Employee employer = employeeRepository.findById(jobListingDTO.getEmployerId()).orElse(null);
        if (employer == null) {
            throw new ResourceNotFoundException("Employee not found with id: " + jobListingDTO.getEmployerId());
        }
        existing.setEmployer(employer);

        JobListing updatedJobListing = jobListingRepository.save(existing);
        return jobListingMapper.entityToDTO(updatedJobListing);
    }

    @Override
    public JobListingDTO getJobListingById(int id) {
        JobListing jobListing = jobListingRepository.findById(id).orElse(null);
        if (jobListing == null) {
            throw new ResourceNotFoundException("Job Listing not found with id: " + id);
        }
        return jobListingMapper.entityToDTO(jobListing);
    }

    @Override
    public List<JobListingDTO> getAllJobListings() {
        List<JobListing> jobListings = jobListingRepository.findAll();
        List<JobListingDTO> dtos = new ArrayList<>();
        for (JobListing j : jobListings) {
            dtos.add(jobListingMapper.entityToDTO(j));
        }
        return dtos;
    }

    @Override
    public void deleteJobListing(int id) {
        JobListing jobListing = jobListingRepository.findById(id).orElse(null);
        if (jobListing == null) {
            throw new ResourceNotFoundException("Job Listing not found with id: " + id);
        }
        jobListingRepository.delete(jobListing);
    }

    @Override
    public List<JobListingDTO> getJobListingByTitle(String title) {
        List<JobListing> jobListings = jobListingRepository.findByTitleContainingIgnoreCase(title);
        List<JobListingDTO> dtos = new ArrayList<>();
        for (JobListing j : jobListings) {
            dtos.add(jobListingMapper.entityToDTO(j));
        }
        return dtos;
    }
}