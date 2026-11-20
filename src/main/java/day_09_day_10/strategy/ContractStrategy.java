package day_09_day_10.strategy;
import day_09_day_10.model.Employee;
public class ContractStrategy implements SalaryStrategy { public double calculateGrossSalary(Employee e){return e.getBasicSalary()*1.2;} }
