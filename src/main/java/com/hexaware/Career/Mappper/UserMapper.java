package com.hexaware.Career.Mappper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hexaware.Career.DTO.UserDTO;
import com.hexaware.Career.Entity.User;
@Component
public class UserMapper {
    
    @Autowired
    private ModelMapper modelMapper;

    public UserDTO entityToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User dtoToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

}
