package com.hexaware.Career.DTO;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter    
@Setter    
@AllArgsConstructor    
@NoArgsConstructor    
@ToString 
public class JobListingDTO {

    private int id;

    @NotBlank(message = "Job title is mandatory")
    private String title;

    @NotBlank(message = "Job description is mandatory")
    @Size(max = 1000, message = "Description max length is 1000")
    private String description;

    @NotBlank(message = "Qualifications are mandatory")
    private String qualifications;

    @NotBlank(message = "Location is mandatory")
    private String location;

    @NotNull(message = "Post date is mandatory")
    @FutureOrPresent(message = "Post date cannot be in the past")
    private LocalDate postDate;

    @NotNull(message = "Salary is mandatory")
    @Min(value = 0, message = "Salary must be positive or zero")
    private Double salary;

    @NotNull(message = "Employer ID is mandatory")
    private Integer employerId;
}
