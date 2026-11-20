package day_09_day_10.repository;
import day_09_day_10.model.Payroll;
import java.util.*;
public class InMemoryPayrollRepository implements PayrollRepository {
 private final Map<Integer,Payroll> records=new HashMap<>();
 public void save(Payroll p){records.put(p.employeeId(),p);System.out.println("Payroll saved for employee ID: "+p.employeeId());}
 public Optional<Payroll> findByEmployeeId(int id){return Optional.ofNullable(records.get(id));}
}
