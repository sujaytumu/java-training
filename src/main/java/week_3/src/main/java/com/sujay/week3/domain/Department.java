package com.sujay.week3.domain;
import jakarta.persistence.*;
import java.util.*;
@Entity @Table(name="departments")
public class Department {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,unique=true,length=80) private String name;
 @OneToMany(mappedBy="department",fetch=FetchType.LAZY) private List<Employee> employees=new ArrayList<>();
 protected Department(){} public Department(String name){this.name=name;}
 public Long getId(){return id;} public String getName(){return name;}
}