package com.sujay.week3.repository;
import com.sujay.week3.domain.Employee;
import jakarta.persistence.LockModeType;
import java.util.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
public interface EmployeeRepository extends JpaRepository<Employee,Long>,JpaSpecificationExecutor<Employee>{
 boolean existsByEmail(String email);
 // JPQL uses Java entity and property names, not physical table/column names.
 @Query("select e from Employee e where lower(e.name) like lower(concat('%', :name, '%'))")
 List<Employee> searchByName(@Param("name") String name);
 // Pessimistic locking holds a database write lock for this row until transaction completion.
 @Lock(LockModeType.PESSIMISTIC_WRITE)
 @Query("select e from Employee e where e.id=:id")
 Optional<Employee> findByIdForUpdate(@Param("id") Long id);
 // Fetch department in the page query to avoid one lazy SELECT per employee (this use case's N+1).
 @Override @EntityGraph(attributePaths="department")
 Page<Employee> findAll(Specification<Employee> spec,Pageable pageable);
 @EntityGraph(attributePaths="department") @Query("select e from Employee e")
 List<Employee> findAllWithDepartment();
 // Native SQL uses the actual table name.
 @Query(value="select count(*) from employees",nativeQuery=true)
 long countEmployeesNative();
}