package day_09_day_10.payroll.abstractfactory;

/** Abstract Factory creates a family of related payroll components. */
public interface PayrollComponentFactory {
    TaxCalculator taxCalculator();
    PayslipFormatter payslipFormatter();
}
