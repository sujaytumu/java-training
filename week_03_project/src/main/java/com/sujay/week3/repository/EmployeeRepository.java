package com.sujay.week3.repository;
import com.sujay.week3.domain.Employee;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
public interface EmployeeRepository extends JpaRepository<Employee,Long>,JpaSpecificationExecutor<Employee>{
 boolean existsByEmail(String email);
 @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select e from Employee e where e.id=:id")
 Optional<Employee> findByIdForUpdate(@Param("id") Long id);
 @EntityGraph(attributePaths="department") @Query("select e from Employee e") java.util.List<Employee> findAllWithDepartment();
}