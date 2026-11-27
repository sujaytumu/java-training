package com.sujay.week3.config;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
/**
 * Example of application-provided auto-configuration registered through
 * META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports.
 */
@AutoConfiguration
@ConditionalOnProperty(prefix="app.features",name="welcome-banner",havingValue="true")
public class EmployeeFeatureAutoConfiguration {
 @Bean
 @ConditionalOnMissingBean
 WelcomeBanner welcomeBanner(AppProperties properties){
  return new WelcomeBanner("Welcome to "+properties.companyName());
 }
 public record WelcomeBanner(String message){}
}