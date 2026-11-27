package day_09_day_10.payroll.factory;

import day_09_day_10.payroll.model.EmployeeType;
import day_09_day_10.payroll.strategy.*;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

//Simple Factory selects one salary algorithm for an emp category.
public final class SalaryStrategyFactory {
    private final Map<EmployeeType, SalaryStrategy> strategies;

    public SalaryStrategyFactory() {
        //enumMap to stores enums
        Map<EmployeeType, SalaryStrategy> map = new EnumMap<>(EmployeeType.class);
        map.put(EmployeeType.FULL_TIME, new FullTimeSalaryStrategy());
        map.put(EmployeeType.CONTRACT, new ContractSalaryStrategy());
        map.put(EmployeeType.INTERN, new InternSalaryStrategy());
        //makes immutable
        strategies = Map.copyOf(map);
        // limitation: adding a new category still requires registering
        // its strategy in this factory. This implementation
        // does not automatically discover new strategy classes.
    }

    //checks emp_type and returns req strategy
    public SalaryStrategy getStrategy(EmployeeType type) {
        //strategies r null..throws nullptr exception
        SalaryStrategy strategy = strategies.get(Objects.requireNonNull(type));
        //not null, but no map...(newemp but no strategy)
        if (strategy == null) throw new IllegalArgumentException("No strategy for " + type);
        return strategy;

    }
}
