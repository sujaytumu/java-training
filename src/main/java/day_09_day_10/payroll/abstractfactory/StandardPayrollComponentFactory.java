package day_09_day_10.payroll.abstractfactory;

import day_09_day_10.payroll.model.Employee;
import day_09_day_10.payroll.model.Payroll;
import java.math.BigDecimal;

public final class StandardPayrollComponentFactory implements PayrollComponentFactory {
    private static final BigDecimal RATE = new BigDecimal("0.10");

    @Override
    public TaxCalculator taxCalculator() {
        return gross -> gross.multiply(RATE);
    }

    @Override
    public PayslipFormatter payslipFormatter() {
        return (Employee employee, Payroll payroll) -> "Standard payslip | " + employee.name()
                + " | gross=" + payroll.gross() + " | tax=" + payroll.tax()
                + " | net=" + payroll.net();
    }
}
