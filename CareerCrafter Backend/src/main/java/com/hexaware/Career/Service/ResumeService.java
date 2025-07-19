package com.hexaware.Career.Service;

import org.springframework.web.multipart.MultipartFile;

import com.hexaware.Career.DTO.ResumeDTO;

public interface ResumeService {
    ResumeDTO uploadResume(MultipartFile file, int jobSeekerId);  // Multipart
    ResumeDTO saveManualResume(ResumeDTO resumeDTO);              // JSON
    ResumeDTO updateResume(ResumeDTO resumeDTO, int id);
    ResumeDTO getResumeById(int id);
    ResumeDTO getResumeByJobSeekerId(int jobSeekerId);
    void deleteResume(int id);
}

