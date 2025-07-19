package com.hexaware.Career.Mappper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hexaware.Career.DTO.ApplicationDTO;
import com.hexaware.Career.Entity.Application;

@Component
public class ApplicationMapper {
    
    @Autowired
    private ModelMapper modelMapper;

    public ApplicationDTO entityToDTO(Application application) {
        return modelMapper.map(application, ApplicationDTO.class);
    }

    public Application dtoToEntity(ApplicationDTO applicationDTO) {
        return modelMapper.map(applicationDTO, Application.class);
    }

}
