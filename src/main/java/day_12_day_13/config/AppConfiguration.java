package day_12_day_13.config;
import java.time.Clock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class AppConfiguration {
 @Bean @ConditionalOnMissingBean
 public Clock applicationClock(){return Clock.systemDefaultZone();}
 @Bean @ConditionalOnProperty(prefix="app.features",name="employee-api",havingValue="true")
 public EmployeeApiFeature employeeApiFeature(){return new EmployeeApiFeature(true);}
 public record EmployeeApiFeature(boolean enabled){}
}