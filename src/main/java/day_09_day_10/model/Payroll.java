package day_09_day_10.model;
public record Payroll(int employeeId, double grossSalary, double tax, double netSalary) {
 public Payroll { if(employeeId<=0 || !Double.isFinite(grossSalary) || !Double.isFinite(tax) || !Double.isFinite(netSalary) || grossSalary<0 || tax<0 || Math.abs(grossSalary-tax-netSalary)>0.001) throw new IllegalArgumentException("Invalid payroll"); }
}
