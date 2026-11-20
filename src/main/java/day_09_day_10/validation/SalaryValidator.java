package day_09_day_10.validation;
import day_09_day_10.model.Employee;
public class SalaryValidator extends PayrollValidator { protected void check(Employee e){if(e.getBasicSalary()<0)throw new IllegalArgumentException("Negative salary");} }
