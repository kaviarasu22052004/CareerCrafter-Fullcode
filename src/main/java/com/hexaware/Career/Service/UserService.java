package com.hexaware.Career.Service;

import java.util.List;

import com.hexaware.Career.DTO.UserDTO;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO, int id);
    UserDTO getUserById(int id);
    List<UserDTO> getAllUsers();
    void deleteUser(int id);
    UserDTO getUserByEmail(String email);

}
