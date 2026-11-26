package day_09_day_10.payroll.repository;

import day_09_day_10.payroll.model.Payroll;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class InMemoryPayrollRepository implements PayrollRepository {
    private final Map<Integer, Payroll> records = new ConcurrentHashMap<>();

    @Override
    public void save(Payroll payroll) {
        records.put(payroll.employeeId(), payroll);
        System.out.println("[REPOSITORY] Saved payroll for employee " + payroll.employeeId());
    }

    @Override
    public Optional<Payroll> findByEmployeeId(int employeeId) {
        return Optional.ofNullable(records.get(employeeId));
    }
}
