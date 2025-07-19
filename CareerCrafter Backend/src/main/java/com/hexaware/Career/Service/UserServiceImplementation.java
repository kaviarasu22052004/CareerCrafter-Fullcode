package com.hexaware.Career.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.Career.DTO.UserDTO;
import com.hexaware.Career.Entity.User;
import com.hexaware.Career.Enum.Role;
import com.hexaware.Career.Exception.ResourceNotFoundException;
import com.hexaware.Career.Mappper.UserMapper;
import com.hexaware.Career.Repository.UserRepository;

@Service
public class UserServiceImplementation  implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
    @Autowired
private PasswordEncoder passwordEncoder;

   @Override
public UserDTO createUser(UserDTO userDTO) {
    if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
        throw new RuntimeException("Email is already registered");
    }

    User user = userMapper.dtoToEntity(userDTO);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user.setRole(Role.valueOf(userDTO.getRole()));

    User savedUser = userRepository.save(user);
    return userMapper.entityToDTO(savedUser);
}


    @Override
    public UserDTO updateUser(UserDTO userDTO, int id) {
        User existing = userRepository.findById(id).orElse(null);
        if (existing == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        existing.setUsername(userDTO.getUsername());
        existing.setPassword(userDTO.getPassword());
        existing.setEmail(userDTO.getEmail());
        existing.setRole(Role.valueOf(userDTO.getRole()));

        User updatedUser = userRepository.save(existing);
        return userMapper.entityToDTO(updatedUser);
    }

    @Override
    public UserDTO getUserById(int id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        return userMapper.entityToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> dtos = new ArrayList<>();
        for (User u : users) {
            dtos.add(userMapper.entityToDTO(u));
        }
        return dtos;
    }

    @Override
    public void deleteUser(int id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.delete(user);
    }
    
    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return userMapper.entityToDTO(user); // ✅ Correct method
    }


}
