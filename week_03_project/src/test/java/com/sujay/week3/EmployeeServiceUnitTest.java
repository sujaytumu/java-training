package com.sujay.week3;
import com.sujay.week3.domain.Department;
import com.sujay.week3.domain.Employee;
import com.sujay.week3.dto.ApiDtos.EmployeeRequest;
import com.sujay.week3.repository.*;
import com.sujay.week3.service.*;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class EmployeeServiceUnitTest {
 @Mock EmployeeRepository employees; @Mock DepartmentRepository departments; @Mock AuditService audit;
 @InjectMocks EmployeeService service;
 @Test void createSavesEmployeeAndRecordsAudit() {
  var request=new EmployeeRequest("Sujay","sujay@example.com",new BigDecimal("50000.00"),1L);
  when(employees.existsByEmail(request.email())).thenReturn(false);
  when(departments.findById(1L)).thenReturn(Optional.of(new Department("Engineering")));
  when(employees.save(any(Employee.class))).thenAnswer(i->i.getArgument(0));
  assertDoesNotThrow(()->service.create(request));
  verify(employees).save(any(Employee.class)); verify(audit).record("CREATED",null);
 }
}