package com.sujay.week3.config;
import java.time.Clock;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.annotation.*;
@Configuration
public class AppConfig {
 @Bean @Primary public Clock systemClock(){return Clock.systemUTC();}
 @Bean("businessClock") public Clock businessClock(){return Clock.systemDefaultZone();}
 @Bean @Scope("prototype") public TraceToken traceToken(){return new TraceToken();}
 @Bean @ConditionalOnMissingBean(name="featureDescription") public String featureDescription(){return "Default employee API";}
 @Bean @ConditionalOnProperty(prefix="app.features",name="attendance",havingValue="true",matchIfMissing=true)
 public AttendanceFeature attendanceFeature(){return new AttendanceFeature(true);}
 public record AttendanceFeature(boolean enabled){}
}