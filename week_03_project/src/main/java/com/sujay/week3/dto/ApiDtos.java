package com.sujay.week3.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.sujay.week3.domain.Attendance.Status;
public final class ApiDtos {
 private ApiDtos(){}
 public record EmployeeRequest(@NotBlank @Size(max=100) String name,@NotBlank @Email String email,
  @NotNull @DecimalMin("0.01") @Digits(integer=10,fraction=2) BigDecimal salary,@NotNull @Positive Long departmentId){}
 public record EmployeeResponse(Long id,String name,String email,BigDecimal salary,Long departmentId,String departmentName,long version){}
 public record DepartmentRequest(@NotBlank @Size(max=80) String name){}
 public record DepartmentResponse(Long id,String name){}
 public record AttendanceRequest(@NotNull @Positive Long employeeId,@NotNull LocalDate attendanceDate,@NotNull Status status){}
 public record AttendanceResponse(Long id,Long employeeId,LocalDate attendanceDate,Status status){}
 public record LoginRequest(@NotBlank String username,@NotBlank String password){}
 public record TokenResponse(String accessToken,String tokenType){}
}