package day_09_day_10.template;
import day_09_day_10.model.Employee;
public abstract class PayrollTemplate {
 public final double process(Employee e){if(!e.isActive())throw new IllegalArgumentException("Inactive");double gross=calculateGross(e);double tax=gross*0.10;return gross-tax;}
 protected abstract double calculateGross(Employee employee);
}
