package com.sujay.week3;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.util.Optional;
import com.sujay.week3.domain.Department;
import com.sujay.week3.domain.Employee;
import com.sujay.week3.dto.ApiDtos.EmployeeRequest;
import com.sujay.week3.repository.DepartmentRepository;
import com.sujay.week3.repository.EmployeeRepository;
import com.sujay.week3.service.AuditService;
import com.sujay.week3.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
@ExtendWith(MockitoExtension.class)
class EmployeeServiceUnitTest {
 @Mock EmployeeRepository employees;
 @Mock DepartmentRepository departments;
 @Mock AuditService audit;
 @InjectMocks EmployeeService service;
 @Test void createsEmployeeAndRecordsAudit() {
  Department department=new Department("Engineering");
  Employee employee=new Employee("Asha Rao","asha@example.com",new BigDecimal("65000.00"),department);
  when(employees.existsByEmail("asha@example.com")).thenReturn(false);
  when(departments.findById(1L)).thenReturn(Optional.of(department));
  when(employees.save(any(Employee.class))).thenReturn(employee);
  var result=service.create(new EmployeeRequest("Asha Rao","asha@example.com",new BigDecimal("65000.00"),1L));
  assertEquals("Asha Rao",result.name());
  assertEquals("Engineering",result.departmentName());
  verify(audit).record("CREATED",null);
 }
 @Test void rejectsDuplicateEmailBeforeSaving() {
  when(employees.existsByEmail("asha@example.com")).thenReturn(true);
  assertThrows(IllegalArgumentException.class,()->service.create(new EmployeeRequest("Asha Rao","asha@example.com",new BigDecimal("65000.00"),1L)));
  verify(employees,never()).save(any());
  verifyNoInteractions(departments);
 }
}