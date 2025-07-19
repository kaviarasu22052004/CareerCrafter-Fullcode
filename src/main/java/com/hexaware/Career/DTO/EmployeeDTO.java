package com.hexaware.Career.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class EmployeeDTO {
    private int employeeId;

    @NotBlank(message = "Company name is mandatory")
    private String companyName;

    @NotBlank(message = "Company details are mandatory")
    @Size(max = 500, message = "Company details can be max 500 characters")
    private String companyDetails;

    @NotBlank(message = "Contact number is mandatory")
    @Pattern(regexp = "[0-9]{10}", message = "Mobile number should be valid")
    private String contactNumber;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotNull(message = "User ID is mandatory")
    private Integer userId;
}
