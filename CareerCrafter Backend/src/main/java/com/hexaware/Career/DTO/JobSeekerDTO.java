package com.hexaware.Career.DTO;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
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
public class JobSeekerDTO {
    private int jobSeekerId;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Education is mandatory")
    private String education;

    @NotBlank(message = "Experience is mandatory")
    private String experience;

    @NotBlank(message = "Skill is mandatory")
    private String skill;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @NotBlank(message = "Mobile number is mandatory")
    @Pattern(regexp="(^$|[0-9]{10})", message = "Mobile number should be valid")
    private String mobileNumber;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @Valid
    private ResumeDTO resume;

    @NotNull(message = "User ID is mandatory")
    private Integer userId;
}
