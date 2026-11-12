package day_03.repository;

//import employeerepo from day02 model
import java.util.*;
//imported employee class
import day_02.model.Employee;
public class EmployeeRepository {
      // obj "employees(List)" created from class "Employee" and employeeMap, employeeId
      private final List <Employee> employees = new ArrayList<>();
      private final Map<Long, Employee> employeeMap = new HashMap<>();
      private final Set<Long> employeeIds = new HashSet<>();
      // Queue for pending employee processing
      private final Queue<Employee> pendingEmployees = new LinkedList<>();

      //FUNCN:- now add emploee is funcn used to add vals
      public boolean addEmployee(Employee employee){

          if (!employeeIds.add(employee.getEmployeeId()))  return false;
          //add all things inside object(which obj "employee" created from class "Employee"
          employees.add(employee);//its just one val adding inside list/map/set
          employeeMap.put(employee.getEmployeeId(), employee);

          //
          return true;
      }

      //
      // Search employee using HashMap
      public Employee findById(long employeeId) {
          return employeeMap.get(employeeId);
      }

    // Return immutable employee list
    public List<Employee> findAll() {
        return List.copyOf(employees);
    }

    // Get employee IDs in sorted order
    public Set<Long> getSortedEmployeeIds() {
        return new TreeSet<>(employeeIds);
    }

    // Process next employee from Queue
    public Employee processNextEmployee() {
        return pendingEmployees.poll();
    }

    // Remove employees safely using Iterator
    public void removeEmployeesBelowSalary(double salary) {

        Iterator<Employee> iterator = employees.iterator();

        while (iterator.hasNext()) {

            Employee employee = iterator.next();

            if (employee.getSalary() < salary) {

                iterator.remove();

                employeeMap.remove(employee.getEmployeeId());
                employeeIds.remove(employee.getEmployeeId());
                pendingEmployees.remove(employee);
            }
        }
    }



}
