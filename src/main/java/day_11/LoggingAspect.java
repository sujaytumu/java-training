package day_11;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* day_11.EmployeeService.*(..))")
    public void logBeforeServiceMethod(JoinPoint joinPoint) {
        System.out.println("[AOP] Calling service method: "
            + joinPoint.getSignature().getName());
    }
}
