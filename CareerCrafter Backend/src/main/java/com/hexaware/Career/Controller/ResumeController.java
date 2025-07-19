package com.hexaware.Career.Controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hexaware.Career.DTO.ResumeDTO;
import com.hexaware.Career.Service.ResumeServiceImplementation;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/resume")
public class ResumeController {

    @Autowired
    private ResumeServiceImplementation resumeService;

    @PostMapping("/upload")
    public ResponseEntity<ResumeDTO> uploadResume(
        @RequestParam("file") MultipartFile file,
        @RequestParam("jobSeekerId") int jobSeekerId) {

        ResumeDTO created = resumeService.uploadResume(file, jobSeekerId);
        HttpHeaders header = new HttpHeaders();
        header.add("info", "Resume uploaded successfully");
        return new ResponseEntity<>(created, header, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResumeDTO> updateResume(@RequestParam("filePath") String filePath,
                                                  @RequestParam("uploadDate") String uploadDate,
                                                  @PathVariable int id) {
        ResumeDTO dto = new ResumeDTO();
        dto.setFilePath(filePath);
        dto.setUploadDate(java.time.LocalDate.parse(uploadDate));
        ResumeDTO updated = resumeService.updateResume(dto, id);
        HttpHeaders header = new HttpHeaders();
        header.add("info", "Resume updated successfully");
        return new ResponseEntity<>(updated, header, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResumeDTO> getResumeById(@PathVariable int id) {
        ResumeDTO resume = resumeService.getResumeById(id);
        HttpHeaders header = new HttpHeaders();
        header.add("info", "Resume fetched successfully");
        return new ResponseEntity<>(resume, header, HttpStatus.OK);
    }

    @GetMapping("/getByJobSeeker/{jobSeekerId}")
    public ResponseEntity<ResumeDTO> getResumeByJobSeekerId(@PathVariable int jobSeekerId) {
        ResumeDTO resume = resumeService.getResumeByJobSeekerId(jobSeekerId);
        HttpHeaders header = new HttpHeaders();
        header.add("info", "Resume fetched for JobSeekerId: " + jobSeekerId);
        return new ResponseEntity<>(resume, header, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteResume(@PathVariable int id) {
        resumeService.deleteResume(id);
        HttpHeaders header = new HttpHeaders();
        header.add("info", "Resume deleted successfully");
        return new ResponseEntity<>("Resume Deleted Successfully", header, HttpStatus.OK);
    }
    
    @PostMapping("/add")
    public ResponseEntity<ResumeDTO> addResume(@RequestBody ResumeDTO dto) {
        ResumeDTO created = resumeService.saveManualResume(dto);
        HttpHeaders header = new HttpHeaders();
        header.add("info", "Resume saved via JSON");
        return new ResponseEntity<>(created, header, HttpStatus.CREATED);
    }
    



}
