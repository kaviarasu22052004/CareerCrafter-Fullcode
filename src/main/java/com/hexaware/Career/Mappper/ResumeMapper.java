package com.hexaware.Career.Mappper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hexaware.Career.DTO.ResumeDTO;
import com.hexaware.Career.Entity.Resume;
@Component
public class ResumeMapper {
    
    @Autowired
    private ModelMapper modelMapper;

    public ResumeDTO entityToDTO(Resume resume) {
        return modelMapper.map(resume, ResumeDTO.class);
    }

    public Resume dtoToEntity(ResumeDTO resumeDTO) {
        return modelMapper.map(resumeDTO, Resume.class);
    }

}
