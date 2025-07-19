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

import com.hexaware.Career.DTO.EmployeeDTO;
import com.hexaware.Career.Service.EmployeeServiceImplementation;

import jakarta.validation.Valid;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeServiceImplementation employeeService;

    @GetMapping("/by-email/{email}")
    public ResponseEntity<EmployeeDTO> getEmployerByEmail(@PathVariable String email) {
        EmployeeDTO dto = employeeService.getEmployeeByEmail(email);
        HttpHeaders headers = new HttpHeaders();
        headers.add("info", "Employer fetched by email");
        return new ResponseEntity<>(dto, headers, HttpStatus.OK);
    }
    
    
    
    @PostMapping("/add")
    public ResponseEntity<EmployeeDTO> addEmployer(@RequestBody @Valid EmployeeDTO employerDTO) {
        EmployeeDTO savedEmployer = employeeService.createEmployee(employerDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("info", "Employer data added successfully");

        return new ResponseEntity<>(savedEmployer, headers, HttpStatus.CREATED);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<EmployeeDTO> getEmployerById(@PathVariable int id) {
        EmployeeDTO employerDTO = employeeService.getEmployeeById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("info", "Employer fetched successfully");

        return new ResponseEntity<>(employerDTO, headers, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployers() {
        List<EmployeeDTO> employerList = employeeService.getAllEmployees();

        HttpHeaders headers = new HttpHeaders();
        headers.add("info", "All Employers fetched successfully");
        return new ResponseEntity<>(employerList, headers, HttpStatus.OK);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployer(@PathVariable int id, @RequestBody @Valid EmployeeDTO employerDTO) {
        EmployeeDTO updatedEmployer = employeeService.updateEmployee(employerDTO, id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("info", "Employer updated successfully");

        return new ResponseEntity<>(updatedEmployer, headers, HttpStatus.OK);
    }

  
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployer(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        HttpHeaders headers = new HttpHeaders();
        
        headers.add("info", "Employer deleted successfully");
        return new ResponseEntity<>("Employer deleted successfully", headers, HttpStatus.OK);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<EmployeeDTO> getByUserId(@PathVariable int userId) {
        EmployeeDTO dto = employeeService.getEmployeeByUserId(userId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("info", "Fetched by userId");
        return new ResponseEntity<>(dto, headers, HttpStatus.OK);
    }

}