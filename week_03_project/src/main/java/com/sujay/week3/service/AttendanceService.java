package com.sujay.week3.service;
import com.sujay.week3.domain.*;
import com.sujay.week3.dto.ApiDtos.*;
import com.sujay.week3.repository.*;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service public class AttendanceService {
 private final AttendanceRepository repo;private final EmployeeRepository employees;
 public AttendanceService(AttendanceRepository r,EmployeeRepository e){repo=r;employees=e;}
 @Transactional public AttendanceResponse record(AttendanceRequest r){Employee e=employees.findById(r.employeeId()).orElseThrow(()->new EntityNotFoundException("Employee not found"));Attendance a=repo.save(new Attendance(e,r.attendanceDate(),r.status()));return map(a);}
 @Transactional(readOnly=true) public List<AttendanceResponse> list(Long id){if(!employees.existsById(id))throw new EntityNotFoundException("Employee not found");return repo.findByEmployeeIdOrderByAttendanceDateDesc(id).stream().map(this::map).toList();}
 private AttendanceResponse map(Attendance a){return new AttendanceResponse(a.getId(),a.getEmployee().getId(),a.getAttendanceDate(),a.getStatus());}
}