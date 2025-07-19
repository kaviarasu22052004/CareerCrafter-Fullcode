package com.hexaware.Career.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hexaware.Career.DTO.ResumeDTO;
import com.hexaware.Career.Entity.JobSeeker;
import com.hexaware.Career.Entity.Resume;
import com.hexaware.Career.Exception.ResourceNotFoundException;
import com.hexaware.Career.Mappper.ResumeMapper;
import com.hexaware.Career.Repository.JobseekerRepository;
import com.hexaware.Career.Repository.ResumeRepository;

@Service
public class ResumeServiceImplementation implements ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private JobseekerRepository jobSeekerRepository;

    @Autowired
    private ResumeMapper resumeMapper;

    private static final String UPLOAD_DIR = "uploads";

    @Override
    public ResumeDTO uploadResume(MultipartFile file, int jobSeekerId) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(jobSeekerId).orElse(null);
        if (jobSeeker == null) {
            throw new ResourceNotFoundException("JobSeeker not found with id: " + jobSeekerId);
        }

        String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        File savePath = new File(UPLOAD_DIR, uniqueFilename);

        try {
            file.transferTo(savePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save resume file: " + e.getMessage());
        }

        Resume resume = new Resume();
        resume.setFilePath(savePath.getAbsolutePath());
        resume.setUploadDate(LocalDate.now());
        resume.setJobSeeker(jobSeeker);

        Resume savedResume = resumeRepository.save(resume);
        return resumeMapper.entityToDTO(savedResume);
    }

    @Override
    public ResumeDTO updateResume(ResumeDTO resumeDTO, int id) {
        Resume existing = resumeRepository.findById(id).orElse(null);
        if (existing == null) {
            throw new ResourceNotFoundException("Resume not found with id: " + id);
        }

        existing.setFilePath(resumeDTO.getFilePath());
        existing.setUploadDate(resumeDTO.getUploadDate());

        Resume updated = resumeRepository.save(existing);
        return resumeMapper.entityToDTO(updated);
    }

    @Override
    public ResumeDTO getResumeById(int id) {
        Resume resume = resumeRepository.findById(id).orElse(null);
        if (resume == null) {
            throw new ResourceNotFoundException("Resume not found with id: " + id);
        }
        return resumeMapper.entityToDTO(resume);
    }

    @Override
    public ResumeDTO getResumeByJobSeekerId(int jobSeekerId) {
        Resume resume = resumeRepository.findByJobSeekerId(jobSeekerId);
        if (resume == null) {
            throw new ResourceNotFoundException("Resume not found for JobSeeker id: " + jobSeekerId);
        }
        return resumeMapper.entityToDTO(resume);
    }

    @Override
    public void deleteResume(int id) {
        Resume resume = resumeRepository.findById(id).orElse(null);
        if (resume == null) {
            throw new ResourceNotFoundException("Resume not found with id: " + id);
        }
        resumeRepository.delete(resume);
    }
    
    @Override
    public ResumeDTO saveManualResume(ResumeDTO resumeDTO) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(resumeDTO.getJobSeekerId())
            .orElseThrow(() -> new ResourceNotFoundException("JobSeeker not found"));

        Resume resume = new Resume();
        resume.setFilePath(resumeDTO.getFilePath());
        resume.setUploadDate(resumeDTO.getUploadDate() != null
            ? resumeDTO.getUploadDate()
            : LocalDate.now());
        resume.setJobSeeker(jobSeeker);

        Resume saved = resumeRepository.save(resume);
        return resumeMapper.entityToDTO(saved);
    }

}
