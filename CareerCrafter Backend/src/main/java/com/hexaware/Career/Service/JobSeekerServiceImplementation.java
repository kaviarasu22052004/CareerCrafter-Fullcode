package com.hexaware.Career.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.Career.DTO.JobSeekerDTO;
import com.hexaware.Career.Entity.JobSeeker;
import com.hexaware.Career.Exception.ResourceNotFoundException;
import com.hexaware.Career.Mappper.JobSeekerMapper;
import com.hexaware.Career.Repository.JobseekerRepository;

@Service
public class JobSeekerServiceImplementation  implements JobSeekerService {

    @Autowired
    private JobseekerRepository jobSeekerRepository;

    @Autowired
    private JobSeekerMapper jobSeekerMapper;
    
    
    public JobSeekerDTO getByEmail(String email) {
        JobSeeker js = jobSeekerRepository.findByUserEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Job seeker not found"));
        return jobSeekerMapper.entityToDTO(js);
    }

    @Override
    public JobSeekerDTO createJobSeeker(JobSeekerDTO jobSeekerDTO) {
        JobSeeker jobSeeker = jobSeekerMapper.dtoToEntity(jobSeekerDTO);
        JobSeeker savedJobSeeker = jobSeekerRepository.save(jobSeeker);
        return jobSeekerMapper.entityToDTO(savedJobSeeker);
    }

    @Override
    public JobSeekerDTO updateJobSeeker(JobSeekerDTO jobSeekerDTO, int id) {
        JobSeeker existing = jobSeekerRepository.findById(id).orElse(null);
        if (existing == null) {
            throw new ResourceNotFoundException("JobSeeker not found with id: " + id);
        }
        existing.setName(jobSeekerDTO.getName());
        existing.setEducation(jobSeekerDTO.getEducation());
        existing.setExperience(jobSeekerDTO.getExperience());
        existing.setSkill(jobSeekerDTO.getSkill());
        existing.setDob(jobSeekerDTO.getDob());
        existing.setMobileNumber(jobSeekerDTO.getMobileNumber());
        existing.setAddress(jobSeekerDTO.getAddress());

        JobSeeker updatedJobSeeker = jobSeekerRepository.save(existing);
        return jobSeekerMapper.entityToDTO(updatedJobSeeker);
    }

    @Override
    public JobSeekerDTO getJobSeekerById(int id) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(id).orElse(null);
        if (jobSeeker == null) {
            throw new ResourceNotFoundException("JobSeeker not found with id: " + id);
        }
        return jobSeekerMapper.entityToDTO(jobSeeker);
    }

    @Override
    public List<JobSeekerDTO> getAllJobSeekers() {
        List<JobSeeker> jobSeekers = jobSeekerRepository.findAll();
        List<JobSeekerDTO> dtos = new ArrayList<>();
        for (JobSeeker j : jobSeekers) {
            dtos.add(jobSeekerMapper.entityToDTO(j));
        }
        return dtos;
    }

    @Override
    public void deleteJobSeeker(int id) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(id).orElse(null);
        if (jobSeeker == null) {
            throw new ResourceNotFoundException("JobSeeker not found with id: " + id);
        }
        jobSeekerRepository.delete(jobSeeker);
    }
}