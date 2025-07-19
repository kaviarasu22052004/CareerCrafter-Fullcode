package com.hexaware.Career.Entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
public class JobSeeker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jobSeekerId;

    private String name;
    private String education;
    private String experience;
    private String skill;
    private LocalDate dob;
    private String mobileNumber;
    private String address;

    @OneToOne
    @JoinColumn(name = "user_id") 
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @OneToMany(mappedBy = "jobSeeker", cascade = CascadeType.ALL)
    private List<Application> applications;
}
