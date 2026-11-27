package com.sujay.week3.service;
import com.sujay.week3.domain.*;
import com.sujay.week3.dto.ApiDtos.*;
import com.sujay.week3.repository.*;
import jakarta.persistence.EntityNotFoundException;
import java.util.Set;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
@Service public class EmployeeService {
 private static final Set<String> SORT=Set.of("name","salary","email","id");
 private final EmployeeRepository employees; private final DepartmentRepository departments; private final AuditService audit;
 public EmployeeService(EmployeeRepository e,DepartmentRepository d,AuditService a){employees=e;departments=d;audit=a;}
 @Transactional public EmployeeResponse create(EmployeeRequest r){
  String email=r.email().trim().toLowerCase();
  if(employees.existsByEmail(email))throw new IllegalArgumentException("Email already registered");
  Department d=departments.findById(r.departmentId()).orElseThrow(()->new EntityNotFoundException("Department not found"));
  Employee e=new Employee(r.name().trim(),email,r.salary(),d);
  // CascadeType.PERSIST on Department.employees persists this new Employee with the managed Department.
  d.addEmployee(e);
  departments.save(d);
  audit.record("CREATED",e.getId());
  return map(e);
 }
 @Transactional(readOnly=true,isolation=Isolation.READ_COMMITTED)
 public Page<EmployeeResponse> list(String name,Long dept,int page,int size,String sortBy,String dir){
  if(page<0||size<1||size>100)throw new IllegalArgumentException("page >= 0 and size must be 1..100");
  if(!SORT.contains(sortBy))throw new IllegalArgumentException("Unsupported sort field");
  Sort sort="desc".equalsIgnoreCase(dir)?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
  Specification<Employee> spec=Specification.where(EmployeeSpecifications.nameContains(name)).and(EmployeeSpecifications.departmentIs(dept));
  return employees.findAll(spec,PageRequest.of(page,size,sort)).map(this::map);
 }
 @Transactional(readOnly=true) public EmployeeResponse get(Long id){return employees.findById(id).map(this::map).orElseThrow(()->new EntityNotFoundException("Employee not found: "+id));}
 @Transactional public EmployeeResponse replace(Long id,EmployeeRequest r){
  Employee e=employees.findById(id).orElseThrow(()->new EntityNotFoundException("Employee not found: "+id));
  Department d=departments.findById(r.departmentId()).orElseThrow(()->new EntityNotFoundException("Department not found"));
  String email=r.email().trim().toLowerCase();
  if(!e.getEmail().equalsIgnoreCase(email)&&employees.existsByEmail(email))throw new IllegalArgumentException("Email already registered");
  e.replace(r.name().trim(),email,r.salary(),d);e=employees.saveAndFlush(e);
  audit.record("UPDATED",e.getId());return map(e);
 }
 @Transactional public void delete(Long id){Employee e=employees.findById(id).orElseThrow(()->new EntityNotFoundException("Employee not found: "+id));employees.delete(e);audit.record("DELETED",id);}
 @Transactional public EmployeeResponse updateSalaryWithWriteLock(Long id,java.math.BigDecimal salary){
  Employee e=employees.findByIdForUpdate(id).orElseThrow(()->new EntityNotFoundException("Employee not found: "+id));
  e.replace(e.getName(),e.getEmail(),salary,e.getDepartment());return map(employees.save(e));
 }
 private EmployeeResponse map(Employee e){return new EmployeeResponse(e.getId(),e.getName(),e.getEmail(),e.getSalary(),e.getDepartment().getId(),e.getDepartment().getName(),e.getVersion());}
}