package com.hexaware.Career.Entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class JobListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private String qualifications;
    private String location;
    private LocalDate postDate;
    private Double salary;

    @ManyToOne
    @JoinColumn(name = "employer_id") 
    private Employee employer;

    @OneToMany(mappedBy = "jobListing", cascade = CascadeType.ALL)
    private List<Application> applications;
}
