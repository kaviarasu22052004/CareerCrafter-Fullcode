package com.hexaware.Career.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.Career.DTO.UserDTO;
import com.hexaware.Career.Service.UserServiceImplementation;

import jakarta.validation.Valid;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImplementation userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid UserDTO userDTO) {
        UserDTO savedUser = userService.createUser(userDTO);
    HttpHeaders header = new HttpHeaders();
        header.add("info", "User registered successfully");
         return new ResponseEntity<>(savedUser, header, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        UserDTO userDTO = userService.getUserById(id);
        if (userDTO != null) {
            HttpHeaders header = new HttpHeaders();
            header.add("info", "User fetched successfully");
            return new ResponseEntity<>(userDTO, header, HttpStatus.OK);
        } else {
            HttpHeaders header = new HttpHeaders();
            header.add("info", "User not found");
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/getall")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userList = userService.getAllUsers();
        HttpHeaders header = new HttpHeaders();
     header.add("info", "Fetched all users successfully");
        return new ResponseEntity<>(userList, header, HttpStatus.OK);
    }

 @PutMapping("/update/{id}")
public ResponseEntity<String> updateUser(@PathVariable int id, @RequestBody @Valid UserDTO userDTO) {
    userService.updateUser(userDTO, id); 
    HttpHeaders header = new HttpHeaders();
     header.add("info", "User updated successfully");
    return new ResponseEntity<>("User updated successfully", header, HttpStatus.OK);
}

@DeleteMapping("/delete/{id}")
public ResponseEntity<String> deleteUser(@PathVariable int id) {
    userService.deleteUser(id);
     HttpHeaders header = new HttpHeaders();
    header.add("info", "User deleted successfully");
    return new ResponseEntity<>("User deleted successfully", header, HttpStatus.OK);
}


@GetMapping("/by-email/{email:.+}")
public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
    UserDTO user = userService.getUserByEmail(email);
    return new ResponseEntity<>(user, HttpStatus.OK);
}

}
