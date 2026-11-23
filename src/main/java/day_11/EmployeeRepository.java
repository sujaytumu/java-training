package day_11;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {
    // In-memory sample data; database integration comes in a later lesson.
    public Employee findById(int id) {
        if (id == 1) return new Employee(1, "Sujay", 50000);
        return null;
    }
}
