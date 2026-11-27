package com.sujay.week3.domain;
import jakarta.persistence.*;
import java.util.*;
@Entity @Table(name="departments")
public class Department {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=80) private String name;
 // Persisting a new Department also persists new Employees attached through addEmployee.
 // REMOVE is deliberately excluded: deleting a department must not silently delete its employees.
 @OneToMany(mappedBy="department",fetch=FetchType.LAZY,cascade=CascadeType.PERSIST)
 private List<Employee> employees=new ArrayList<>();
 protected Department(){}
 public Department(String name){this.name=name;}
 public void addEmployee(Employee employee){employees.add(employee);employee.assignDepartment(this);}
 public Long getId(){return id;} public String getName(){return name;}
}