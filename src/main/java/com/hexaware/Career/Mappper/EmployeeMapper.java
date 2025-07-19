package com.hexaware.Career.Mappper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hexaware.Career.DTO.EmployeeDTO;
import com.hexaware.Career.Entity.Employee;
@Component
public class EmployeeMapper {
        @Autowired
    private ModelMapper modelMapper;

    public EmployeeDTO entityToDTO(Employee employee) {
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public Employee dtoToEntity(EmployeeDTO employeeDTO) {
        return modelMapper.map(employeeDTO, Employee.class);
    }

}
