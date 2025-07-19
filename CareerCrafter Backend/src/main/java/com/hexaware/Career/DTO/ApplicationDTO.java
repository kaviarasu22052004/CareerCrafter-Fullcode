package com.hexaware.Career.DTO;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
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
public class ApplicationDTO {
    private int id;

    @NotNull(message = "Applied date is mandatory")
    @FutureOrPresent(message = "Applied date cannot be in the past")
    private LocalDate appliedDate;

   
    private String status;

    @NotNull(message = "Job Listing ID is mandatory")
    private Integer jobListingId;

    @NotNull(message = "Job Seeker ID is mandatory")
    private Integer jobSeekerId;

    @NotNull(message = "Resume ID is mandatory")
    private Integer resumeId;
}