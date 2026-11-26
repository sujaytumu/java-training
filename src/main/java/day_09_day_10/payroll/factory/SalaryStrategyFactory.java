package day_09_day_10.payroll.factory;

import day_09_day_10.payroll.model.EmployeeType;
import day_09_day_10.payroll.strategy.*;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/** Simple Factory selects one salary algorithm for an employee category. */
public final class SalaryStrategyFactory {
    private final Map<EmployeeType, SalaryStrategy> strategies;

    public SalaryStrategyFactory() {
        Map<EmployeeType, SalaryStrategy> map = new EnumMap<>(EmployeeType.class);
        map.put(EmployeeType.FULL_TIME, new FullTimeSalaryStrategy());
        map.put(EmployeeType.CONTRACT, new ContractSalaryStrategy());
        map.put(EmployeeType.INTERN, new InternSalaryStrategy());
        strategies = Map.copyOf(map);
    }

    public SalaryStrategy getStrategy(EmployeeType type) {
        SalaryStrategy strategy = strategies.get(Objects.requireNonNull(type));
        if (strategy == null) throw new IllegalArgumentException("No strategy for " + type);
        return strategy;
    }
}
