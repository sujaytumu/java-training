package day_09_day_10.validation;
import day_09_day_10.model.Employee;
public class ActiveValidator extends PayrollValidator { protected void check(Employee e){if(!e.isActive())throw new IllegalArgumentException("Inactive employee");} }
