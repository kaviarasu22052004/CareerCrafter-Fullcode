package com.hexaware.Career.Service;

import java.util.List;

import com.hexaware.Career.DTO.EmployeeDTO;

public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO updateEmployee(EmployeeDTO employeeDTO, int id);
    EmployeeDTO getEmployeeById(int id);
    List<EmployeeDTO> getAllEmployees();
    void deleteEmployee(int id);
    EmployeeDTO getEmployeeByUserId(int userId);
    EmployeeDTO getEmployeeByEmail(String email);


}
