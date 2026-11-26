package day_09_day_10.payroll.repository;

import day_09_day_10.payroll.model.Payroll;
import java.util.Optional;

public interface PayrollRepository {
    void save(Payroll payroll);
    Optional<Payroll> findByEmployeeId(int employeeId);
}
