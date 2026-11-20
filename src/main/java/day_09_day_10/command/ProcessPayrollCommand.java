package day_09_day_10.command;
import day_09_day_10.model.Employee;
import day_09_day_10.service.PayrollService;
public class ProcessPayrollCommand implements Command {
 private final PayrollService service; private final Employee employee;
 public ProcessPayrollCommand(PayrollService s,Employee e){service=s;employee=e;}
 public void execute(){service.process(employee);}
}
