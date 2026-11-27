package com.sujay.week3.repository;
import com.sujay.week3.domain.Attendance;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AttendanceRepository extends JpaRepository<Attendance,Long>{
 List<Attendance> findByEmployeeIdOrderByAttendanceDateDesc(Long employeeId);
}