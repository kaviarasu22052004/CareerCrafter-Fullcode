package com.hexaware.Career.Mappper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hexaware.Career.DTO.JobListingDTO;
import com.hexaware.Career.Entity.JobListing;
@Component
public class JobListingMapper {

        @Autowired
    private ModelMapper modelMapper;

    public JobListingDTO entityToDTO(JobListing jobListing) {
        return modelMapper.map(jobListing, JobListingDTO.class);
    }

    public JobListing dtoToEntity(JobListingDTO jobListingDTO) {
        return modelMapper.map(jobListingDTO, JobListing.class);
    }
}
