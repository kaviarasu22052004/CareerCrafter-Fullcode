
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

import com.hexaware.Career.DTO.JobListingDTO;
import com.hexaware.Career.Service.JobListingServiceImplmentation;

import jakarta.validation.Valid;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/joblisting")
public class JobListingController {

    @Autowired
    private JobListingServiceImplmentation jobListingService;


    @PostMapping("/add")
    public ResponseEntity<JobListingDTO> createJobListing(@RequestBody @Valid JobListingDTO jobListingDTO) {
        JobListingDTO created = jobListingService.createJobListing(jobListingDTO);
        HttpHeaders header = new HttpHeaders();
        header.add("info", "Job Listing created successfully");
        return new ResponseEntity<>(created, header, HttpStatus.CREATED);
    }
    
    
    @GetMapping("/employer/{employerId}")
    public ResponseEntity<List<JobListingDTO>> getJobsByEmployer(@PathVariable int employerId) {
        List<JobListingDTO> list = jobListingService.getJobListingsByEmployerId(employerId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }



    @PutMapping("/update/{id}")
    public ResponseEntity<JobListingDTO> updateJobListing(@RequestBody @Valid JobListingDTO jobListingDTO, @PathVariable int id) {
        JobListingDTO updated = jobListingService.updateJobListing(jobListingDTO, id);
        HttpHeaders header = new HttpHeaders();
        header.add("info", "Job Listing updated successfully");
        return new ResponseEntity<>(updated, header, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<JobListingDTO> getJobListingById(@PathVariable int id) {
        JobListingDTO jobListing = jobListingService.getJobListingById(id);
        HttpHeaders header = new HttpHeaders();
        header.add("info", "Job Listing fetched successfully");
        return new ResponseEntity<>(jobListing, header, HttpStatus.OK);
    }


    @GetMapping("/getall")
    public ResponseEntity<List<JobListingDTO>> getAllJobListings() {
        List<JobListingDTO> list = jobListingService.getAllJobListings();
        HttpHeaders header = new HttpHeaders();
        header.add("info", "All Job Listings fetched successfully");
        return new ResponseEntity<>(list, header, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJobListing(@PathVariable int id) {
        jobListingService.deleteJobListing(id);
        HttpHeaders header = new HttpHeaders();
        header.add("info", "Job Listing deleted successfully");
        return new ResponseEntity<>("Job Listing Deleted Successfully", header, HttpStatus.OK);
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<List<JobListingDTO>> getJobListingByTitle(@PathVariable String title) {
        List<JobListingDTO> list = jobListingService.getJobListingByTitle(title);
        HttpHeaders header = new HttpHeaders();
        header.add("info", "Job Listings fetched by title");
        return new ResponseEntity<>(list, header, HttpStatus.OK);
    }
}
