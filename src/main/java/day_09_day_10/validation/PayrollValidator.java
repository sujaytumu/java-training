package day_09_day_10.validation;
import day_09_day_10.model.Employee;
public abstract class PayrollValidator {
 private PayrollValidator next;
 public PayrollValidator setNext(PayrollValidator n){next=n;return n;}
 public final void validate(Employee e){check(e);if(next!=null)next.validate(e);}
 protected abstract void check(Employee e);
}
