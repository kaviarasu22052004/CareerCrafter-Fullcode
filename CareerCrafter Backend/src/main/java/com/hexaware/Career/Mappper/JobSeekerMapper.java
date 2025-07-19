package com.hexaware.Career.Mappper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hexaware.Career.DTO.JobSeekerDTO;
import com.hexaware.Career.Entity.JobSeeker;
@Component
public class JobSeekerMapper {
        @Autowired
    private ModelMapper modelMapper;

    public JobSeekerDTO entityToDTO(JobSeeker jobSeeker) {
        return modelMapper.map(jobSeeker, JobSeekerDTO.class);
    }

    public JobSeeker dtoToEntity(JobSeekerDTO jobSeekerDTO) {
        return modelMapper.map(jobSeekerDTO, JobSeeker.class);
    }

}
