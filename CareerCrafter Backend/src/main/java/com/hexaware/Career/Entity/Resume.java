package com.hexaware.Career.Entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String filePath;
    private LocalDate uploadDate;

    @ManyToOne
@JoinColumn(name = "job_seeker_id")  
private JobSeeker jobSeeker;

}
