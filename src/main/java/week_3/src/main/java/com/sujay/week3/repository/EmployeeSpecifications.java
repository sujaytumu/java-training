package com.sujay.week3.repository;
import com.sujay.week3.domain.Employee;
import org.springframework.data.jpa.domain.Specification;
public final class EmployeeSpecifications {
 private EmployeeSpecifications(){}
 public static Specification<Employee> nameContains(String value){return (r,q,c)->value==null||value.isBlank()?null:c.like(c.lower(r.get("name")),"%"+value.toLowerCase()+"%");}
 public static Specification<Employee> departmentIs(Long id){return (r,q,c)->id==null?null:c.equal(r.get("department").get("id"),id);}
}