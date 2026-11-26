package day_09_day_10.payroll.model;

import java.math.BigDecimal;
import java.util.Objects;

public record Payroll(int employeeId, BigDecimal gross, BigDecimal tax, BigDecimal net) {
    public Payroll {
        Objects.requireNonNull(gross);
        Objects.requireNonNull(tax);
        Objects.requireNonNull(net);
        if (employeeId <= 0 || gross.signum() < 0 || tax.signum() < 0 || net.signum() < 0
                || gross.subtract(tax).compareTo(net) != 0) {
            throw new IllegalArgumentException("Invalid payroll amounts");
        }
    }
}
