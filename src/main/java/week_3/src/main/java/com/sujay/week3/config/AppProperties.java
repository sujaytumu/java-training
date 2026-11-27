package com.sujay.week3.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotBlank;
@Validated @ConfigurationProperties(prefix="app")
public record AppProperties(@NotBlank String companyName,@NotBlank String employeeCodePrefix) {}