package com.hexaware.Career;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.Career.DTO.JobSeekerDTO;
import com.hexaware.Career.Entity.JobSeeker;
import com.hexaware.Career.Entity.Resume;
import com.hexaware.Career.Exception.ResourceNotFoundException;
import com.hexaware.Career.Mappper.JobSeekerMapper;
import com.hexaware.Career.Mappper.ResumeMapper;
import com.hexaware.Career.Repository.JobseekerRepository;
import com.hexaware.Career.Repository.ResumeRepository;
import com.hexaware.Career.Service.JobSeekerServiceImplementation;
import com.hexaware.Career.Service.ResumeServiceImplementation;

@SpringBootTest
class CareerCrafterApplicationTests {

	 @InjectMocks
    private JobSeekerServiceImplementation jobSeekerService;

    @InjectMocks
    private ResumeServiceImplementation resumeService;

    @Mock
    private JobseekerRepository jobSeekerRepo;

    @Mock
    private ResumeRepository resumeRepo;

    @Mock
    private JobSeekerMapper jobSeekerMapper;

    @Mock
    private ResumeMapper resumeMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

 
    @Test
    void testServiceLoaded() {
        assertNotNull(jobSeekerService);
        assertNotNull(resumeService);
    }

   
    @Test
    void testSaveJobSeeker() {
        when(jobSeekerMapper.dtoToEntity(any())).thenReturn(new JobSeeker());
        when(jobSeekerRepo.save(any())).thenReturn(new JobSeeker());
        when(jobSeekerMapper.entityToDTO(any())).thenReturn(new JobSeekerDTO());

        assertNotNull(jobSeekerService.createJobSeeker(new JobSeekerDTO()));
    }

    @Test
    void testGetJobSeekerById() {
        when(jobSeekerRepo.findById(1)).thenReturn(Optional.of(new JobSeeker()));
        when(jobSeekerMapper.entityToDTO(any())).thenReturn(new JobSeekerDTO());

        assertNotNull(jobSeekerService.getJobSeekerById(1));
    }

    @Test
    void testGetAllJobSeekers() {
        when(jobSeekerRepo.findAll()).thenReturn(List.of(new JobSeeker()));
        when(jobSeekerMapper.entityToDTO(any())).thenReturn(new JobSeekerDTO());

        assertEquals(1, jobSeekerService.getAllJobSeekers().size());
    }

    @Test
    void testDeleteJobSeeker() {
        when(jobSeekerRepo.findById(1)).thenReturn(Optional.of(new JobSeeker()));

        jobSeekerService.deleteJobSeeker(1);
        verify(jobSeekerRepo, times(1)).delete(any());
    }



    @Test
    void testGetResumeByJobSeekerId() {
        when(resumeRepo.findByJobSeekerId(1)).thenReturn(new Resume());
        when(resumeMapper.entityToDTO(any())).thenReturn(new com.hexaware.Career.DTO.ResumeDTO());

        assertNotNull(resumeService.getResumeByJobSeekerId(1));
    }

 
    @Test
    void testDeleteResume() {
        when(resumeRepo.findById(1)).thenReturn(Optional.of(new Resume()));

        resumeService.deleteResume(1);
        verify(resumeRepo, times(1)).delete(any());
    }

    @Test
    void testJobSeekerNotFound() {
        when(jobSeekerRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> jobSeekerService.getJobSeekerById(99));
    }


    @Test
    void testUpdateJobSeeker() {
        when(jobSeekerRepo.findById(1)).thenReturn(Optional.of(new JobSeeker()));
        when(jobSeekerRepo.save(any())).thenReturn(new JobSeeker());
        when(jobSeekerMapper.entityToDTO(any())).thenReturn(new JobSeekerDTO());

        assertNotNull(jobSeekerService.updateJobSeeker(new JobSeekerDTO(), 1));
    }
}