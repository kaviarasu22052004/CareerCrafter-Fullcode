package com.hexaware.Career.Exception;

import lombok.Data;

@Data
public class ApiError {

    private int status;
    private String error;
    private String message;
    private String path;

   }
