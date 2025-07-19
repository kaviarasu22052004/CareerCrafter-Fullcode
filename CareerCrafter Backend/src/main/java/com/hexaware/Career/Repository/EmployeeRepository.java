package com.hexaware.Career.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexaware.Career.DTO.EmployeeDTO;
import com.hexaware.Career.Entity.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
	
	

	Employee findByUserId(int id);
	
	@Query("SELECT e FROM Employee e WHERE e.user.email = :email")
	Employee findByUserEmail(@Param("email") String email);




}
