package day_09_day_10.payroll.abstractfactory;

import day_09_day_10.payroll.model.Employee;
import day_09_day_10.payroll.model.Payroll;
import java.math.BigDecimal;

public final class AlternatePayrollComponentFactory implements PayrollComponentFactory {
    private static final BigDecimal DEMO_RATE = new BigDecimal("0.15");

    @Override
    public TaxCalculator taxCalculator() {
        return gross -> gross.multiply(DEMO_RATE);
    }

    @Override
    public PayslipFormatter payslipFormatter() {
        return (Employee employee, Payroll payroll) -> "[ALTERNATE] " + employee.name()
                + " | take-home pay=" + payroll.net();
    }
}
