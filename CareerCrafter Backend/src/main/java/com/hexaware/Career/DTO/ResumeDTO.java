package com.hexaware.Career.DTO;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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

public class ResumeDTO {
    private int id;

    @NotBlank(message = "File path is mandatory")
    private String filePath;

    @NotNull(message = "Upload date is mandatory")
    @PastOrPresent(message = "Upload date cannot be in the future")
    private LocalDate uploadDate;

    @NotNull(message = "JobSeeker ID is mandatory")
    private Integer jobSeekerId;  
}

