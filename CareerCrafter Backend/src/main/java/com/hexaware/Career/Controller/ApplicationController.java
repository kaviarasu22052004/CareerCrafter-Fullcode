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

import com.hexaware.Career.DTO.ApplicationDTO;
import com.hexaware.Career.Service.ApplicationServiceImplementation;

import jakarta.validation.Valid;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    private ApplicationServiceImplementation applicationService;

    @PostMapping("/apply")
    public ResponseEntity<ApplicationDTO> applyForJob(@RequestBody @Valid ApplicationDTO applicationDTO) {
        ApplicationDTO created = applicationService.applyForJob(applicationDTO);
        HttpHeaders header = new HttpHeaders();
        header.add("info", "Job Application created successfully");
        return new ResponseEntity<>(created, header, HttpStatus.CREATED);
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<ApplicationDTO> updateApplicationStatus(
            @PathVariable int id,
            @RequestBody ApplicationDTO statusDTO) {

        ApplicationDTO updated = applicationService.updateApplicationStatus(id, statusDTO.getStatus());
        HttpHeaders header = new HttpHeaders();
        header.add("info", "Application status updated successfully");
        return new ResponseEntity<>(updated, header, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApplicationDTO> getApplicationById(@PathVariable int id) {
        ApplicationDTO application = applicationService.getApplicationById(id);
        HttpHeaders header = new HttpHeaders();
        header.add("info", "Application fetched successfully");
        return new ResponseEntity<>(application, header, HttpStatus.OK);
    }

    @GetMapping("/getByJobListing/{jobListingId}")
    public ResponseEntity<List<ApplicationDTO>> getApplicationsByJobListingId(@PathVariable int jobListingId) {
        List<ApplicationDTO> list = applicationService.getApplicationsByJobListingId(jobListingId);
        HttpHeaders header = new HttpHeaders();
        header.add("info", "Applications fetched for JobListingId: " + jobListingId);
        return new ResponseEntity<>(list, header, HttpStatus.OK);
    }

    @GetMapping("/getByJobSeeker/{jobSeekerId}")
    public ResponseEntity<List<ApplicationDTO>> getApplicationsByJobSeekerId(@PathVariable int jobSeekerId) {
        List<ApplicationDTO> list = applicationService.getApplicationsByJobSeekerId(jobSeekerId);
        HttpHeaders header = new HttpHeaders();
        header.add("info", "Applications fetched for JobSeekerId: " + jobSeekerId);
        return new ResponseEntity<>(list, header, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteApplication(@PathVariable int id) {
        applicationService.deleteApplication(id);
        HttpHeaders header = new HttpHeaders();
        header.add("info", "Application deleted successfully");
        return new ResponseEntity<>("Application Deleted Successfully", header, HttpStatus.OK);
    }

}
