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

import com.hexaware.Career.DTO.JobSeekerDTO;
import com.hexaware.Career.Service.JobSeekerService;

import jakarta.validation.Valid;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/jobseeker")
public class JobseekerController {

    @Autowired
    private JobSeekerService jobSeekerService;
    
    
    @GetMapping("/by-email/{email}")
    public ResponseEntity<JobSeekerDTO> getByEmail(@PathVariable String email) {
        JobSeekerDTO dto = jobSeekerService.getByEmail(email);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<JobSeekerDTO> addJobSeeker(@RequestBody @Valid JobSeekerDTO jobSeekerDTO) {
        JobSeekerDTO savedDTO = jobSeekerService.createJobSeeker(jobSeekerDTO);

        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Adding new jobseeker to DB");
        return new ResponseEntity<>(savedDTO, header, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<JobSeekerDTO> getJobSeekerById(@PathVariable int id) {
        JobSeekerDTO jobSeekerDTO = jobSeekerService.getJobSeekerById(id);
        if (jobSeekerDTO != null) {
            HttpHeaders header = new HttpHeaders();
            header.add("desc", "Fetching jobseeker by ID");
            return new ResponseEntity<>(jobSeekerDTO, header, HttpStatus.OK);
        } else {
            HttpHeaders header = new HttpHeaders();
            header.add("desc", "Jobseeker not found");
            return new ResponseEntity<>(header, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<JobSeekerDTO>> getAllJobSeekers() {
        List<JobSeekerDTO> list = jobSeekerService.getAllJobSeekers();

        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Fetching all jobseekers");
        return new ResponseEntity<>(list, header, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<JobSeekerDTO> updateJobSeeker(@PathVariable int id, @RequestBody @Valid JobSeekerDTO jobSeekerDTO) {
        JobSeekerDTO updatedDTO = jobSeekerService.updateJobSeeker(jobSeekerDTO,id);

        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Updating jobseeker in DB");
        return new ResponseEntity<>(updatedDTO, header, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJobSeeker(@PathVariable int id) {
        jobSeekerService.deleteJobSeeker(id);
        
        HttpHeaders header = new HttpHeaders();
        header.add("desc", "Deleting jobseeker from DB");
        return new ResponseEntity<>("Jobseeker deleted successfully", header, HttpStatus.OK);
    }
}
