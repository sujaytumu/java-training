package day_12_day_13.employee;
import jakarta.persistence.*;
@Entity @Table(name="employees")
public class Employee {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false,length=100) private String name;
 @Column(nullable=false) private Double salary;
 protected Employee(){}
 public Employee(String name,Double salary){this.name=name;this.salary=salary;}
 public Long getId(){return id;} public String getName(){return name;} public Double getSalary(){return salary;}
 public void replaceDetails(String name,Double salary){this.name=name;this.salary=salary;}
}