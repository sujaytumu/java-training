package day_09_day_10.payroll.service;

import day_09_day_10.payroll.abstractfactory.PayrollComponentFactory;
import day_09_day_10.payroll.factory.SalaryStrategyFactory;
import day_09_day_10.payroll.model.Employee;
import day_09_day_10.payroll.model.Payroll;
import day_09_day_10.payroll.observer.PayrollObserver;
import day_09_day_10.payroll.repository.PayrollRepository;
import day_09_day_10.payroll.strategy.SalaryStrategy;
import day_09_day_10.payroll.validation.EmployeeValidator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

// Coordinates the use case; each pattern owns one distinct responsibility.
public final class PayrollService {
    private final EmployeeValidator validatorChain;
    private final SalaryStrategyFactory strategyFactory;
    private final PayrollComponentFactory componentFactory;
    private final PayrollRepository repository;
    private final List<PayrollObserver> observers;

    public PayrollService(EmployeeValidator validatorChain,
                          SalaryStrategyFactory strategyFactory,
                          PayrollComponentFactory componentFactory,
                          PayrollRepository repository,
                          List<PayrollObserver> observers) {
        //using objs(valchain, strafac, compfact, repo, obs)
        this.validatorChain = Objects.requireNonNull(validatorChain);
        this.strategyFactory = Objects.requireNonNull(strategyFactory);
        this.componentFactory = Objects.requireNonNull(componentFactory);
        this.repository = Objects.requireNonNull(repository);
        //im-mutable objs
        this.observers = List.copyOf(observers);
    }

    public Payroll process(Employee employee) {
        // Validate before caln or side effects.
        validatorChain.validate(employee);

        // Factory chooses/maps the Strategy; Strategy calculates gross salary.
        SalaryStrategy strategy = strategyFactory.getStrategy(employee.type());
        BigDecimal gross = strategy.calculateGrossSalary(employee);

        // Abstract Factory supplies the matching tax component.
        BigDecimal tax = componentFactory.taxCalculator().calculateTax(gross);
        BigDecimal net = gross.subtract(tax);
        Payroll payroll = new Payroll(employee.id(), gross, tax, net);


        //Repository: save updated states 1st, then notify
        //Persist first, then notify independent observers.
        repository.save(payroll);//if fails, never notify
        //Observer: notify now
        for (PayrollObserver observer : observers) {
            try {
                //payrollService depends only on PayrollObs, not on emailObsloose couple)
                //payroll observer..
                observer.onPayrollProcessed(employee, payroll);
                //if observer==email, EmailObserver method runs itself
            } //errors limited to that loop only
            catch (RuntimeException exception) {
                System.err.println("[OBSERVER ERROR] " + exception.getMessage());
            }
        }
        return payroll;
    }
}
