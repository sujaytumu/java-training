package com.sujay.week3.domain;
import jakarta.persistence.*;
import java.time.LocalDate;
@Entity @Table(name="attendance",uniqueConstraints=@UniqueConstraint(columnNames={"employee_id","attendance_date"}))
public class Attendance {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="employee_id",nullable=false) private Employee employee;
 @Column(name="attendance_date",nullable=false) private LocalDate attendanceDate;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=16) private Status status;
 protected Attendance(){}
 public Attendance(Employee e,LocalDate d,Status s){employee=e;attendanceDate=d;status=s;}
 public Long getId(){return id;} public LocalDate getAttendanceDate(){return attendanceDate;} public Status getStatus(){return status;}
 public enum Status{PRESENT,ABSENT,LEAVE}
}