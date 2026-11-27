package com.sujay.week3.repository;
import com.sujay.week3.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DepartmentRepository extends JpaRepository<Department,Long>{}