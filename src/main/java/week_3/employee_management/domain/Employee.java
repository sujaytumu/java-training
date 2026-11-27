package com.sujay.week3.domain;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.*;
@Entity @Table(name="employees")
public class Employee {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=100) private String name;
 @Column(nullable=false,unique=true,length=160) private String email;
 @Column(nullable=false,precision=12,scale=2) private BigDecimal salary;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="department_id",nullable=false) private Department department;
 @OneToMany(mappedBy="employee",fetch=FetchType.LAZY) private List<Attendance> attendanceRecords=new ArrayList<>();
 // Hibernate increments this value and detects stale concurrent updates (optimistic locking).
 @Version private long version;
 protected Employee(){}
 public Employee(String n,String e,BigDecimal s,Department d){name=n;email=e;salary=s;department=d;}
 void assignDepartment(Department d){department=d;}
 public void replace(String n,String e,BigDecimal s,Department d){name=n;email=e;salary=s;department=d;}
 public Long getId(){return id;} public String getName(){return name;} public String getEmail(){return email;}
 public BigDecimal getSalary(){return salary;} public Department getDepartment(){return department;} public long getVersion(){return version;}
}