package day_09_day_10.repository;
import day_09_day_10.model.Payroll;
import java.util.Optional;
public interface PayrollRepository { void save(Payroll payroll); Optional<Payroll> findByEmployeeId(int id); }
