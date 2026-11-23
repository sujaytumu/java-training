package day_11;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;

    // Constructor injection: Spring supplies the repository Bean.
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Employee getEmployeeById(int id) {
        if (id <= 0) throw new IllegalArgumentException("Employee ID must be positive");
        return repository.findById(id);
    }
}
