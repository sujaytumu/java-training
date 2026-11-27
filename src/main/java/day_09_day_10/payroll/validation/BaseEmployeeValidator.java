package day_09_day_10.payroll.validation;

import day_09_day_10.payroll.model.Employee;
import java.util.Objects;

public abstract class BaseEmployeeValidator implements EmployeeValidator {
    private EmployeeValidator next;

    @Override
    public EmployeeValidator setNext(EmployeeValidator next) {
        //receive salvaln & store
        this.next = Objects.requireNonNull(next);
        return next;
    }

    @Override
    public final void validate(Employee employee) {
        //validate emp obj
        Objects.requireNonNull(employee, "employee is required");
        //method-override
        check(employee);//**specific validn logic
        //curr=true, next valid there..
        if (next != null) next.validate(employee);
    }

    protected abstract void check(Employee employee);
}
