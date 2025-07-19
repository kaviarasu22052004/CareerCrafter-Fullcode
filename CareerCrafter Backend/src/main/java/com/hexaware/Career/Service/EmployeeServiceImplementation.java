package com.hexaware.Career.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.Career.DTO.EmployeeDTO;
import com.hexaware.Career.Entity.Employee;
import com.hexaware.Career.Exception.ResourceNotFoundException;
import com.hexaware.Career.Mappper.EmployeeMapper;
import com.hexaware.Career.Repository.EmployeeRepository;

@Service
public class EmployeeServiceImplementation implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;
    
    
    @Override
    public EmployeeDTO getEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findByUserEmail(email);
        if (employee == null) {
            throw new ResourceNotFoundException("No employer found with email: " + email);
        }
        return employeeMapper.entityToDTO(employee);
    }


    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.dtoToEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.entityToDTO(savedEmployee);
    }

    @Override
    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO, int id) {
        Employee existing = employeeRepository.findById(id).orElse(null);
        if (existing == null) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        existing.setCompanyName(employeeDTO.getCompanyName());
        existing.setCompanyDetails(employeeDTO.getCompanyDetails());
        existing.setContactNumber(employeeDTO.getContactNumber());
        existing.setAddress(employeeDTO.getAddress());

        Employee updatedEmployee = employeeRepository.save(existing);
        return employeeMapper.entityToDTO(updatedEmployee);
    }

    @Override
    public EmployeeDTO getEmployeeById(int id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        return employeeMapper.entityToDTO(employee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> dtos = new ArrayList<>();
        for (Employee e : employees) {
            dtos.add(employeeMapper.entityToDTO(e));
        }
        return dtos;
    }

    @Override
    public void deleteEmployee(int id) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee == null) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.delete(employee);
    }
    
 
    @Override
    public EmployeeDTO getEmployeeByUserId(int userId) {
        Employee employee = employeeRepository.findByUserId(userId); // ✅ fixed
        if (employee == null) {
            throw new ResourceNotFoundException("No employee found for user ID: " + userId);
        }
        return employeeMapper.entityToDTO(employee);
    }

}