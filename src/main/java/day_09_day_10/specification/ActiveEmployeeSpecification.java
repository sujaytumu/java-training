package day_09_day_10.specification;
import day_09_day_10.model.Employee;
public class ActiveEmployeeSpecification implements Specification<Employee> { public boolean isSatisfiedBy(Employee e){return e.isActive();} }
