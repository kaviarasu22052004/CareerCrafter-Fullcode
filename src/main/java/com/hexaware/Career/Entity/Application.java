package com.hexaware.Career.Entity;

import java.time.LocalDate;

import com.hexaware.Career.Enum.ApplicationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate appliedDate;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @ManyToOne
    @JoinColumn(name = "job_listing_id") 
    private JobListing jobListing;

    @ManyToOne
    @JoinColumn(name = "job_seeker_id") 
    private JobSeeker jobSeeker;

    @ManyToOne
    @JoinColumn(name = "resume_id") 
    private Resume resume;
}
