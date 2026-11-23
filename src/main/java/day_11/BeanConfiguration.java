package day_11;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
    @Bean
    public String applicationMessage() {
        return "Employee Management Application is running";
    }
}
