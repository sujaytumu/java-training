package day_09_day_10.factory;
import day_09_day_10.model.EmployeeType;
import day_09_day_10.strategy.*;
import java.util.Objects;
public final class SalaryStrategyFactory {
 private SalaryStrategyFactory(){}
 public static SalaryStrategy getStrategy(EmployeeType type) { return switch(Objects.requireNonNull(type)) { case FULL_TIME -> new FullTimeStrategy(); case INTERN -> new InternStrategy(); case CONTRACT -> new ContractStrategy(); }; }
}
