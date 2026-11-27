package com.sujay.week3.repository;
import com.sujay.week3.domain.Employee;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
public interface EmployeeRepository extends JpaRepository<Employee,Long>,JpaSpecificationExecutor<Employee>{
 boolean existsByEmail(String email);
 @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select e from Employee e where e.id=:id")
 Optional<Employee> findByIdForUpdate(@Param("id") Long id);
 // EntityGraph fetches the department in the page query, avoiding one lazy SELECT per employee (N+1).
 @Override @EntityGraph(attributePaths="department")
 Page<Employee> findAll(Specification<Employee> spec,Pageable pageable);
 @EntityGraph(attributePaths="department") @Query("select e from Employee e")
 java.util.List<Employee> findAllWithDepartment();
 // Native SQL query example; JPQL above refers to entity/field names instead of table/column names.
 @Query(value="select count(*) from employees",nativeQuery=true)
 long countEmployeesNative();
}