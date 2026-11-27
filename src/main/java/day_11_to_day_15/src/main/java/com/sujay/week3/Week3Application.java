package com.sujay.week3;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
@SpringBootApplication @ConfigurationPropertiesScan @EnableMethodSecurity
public class Week3Application { public static void main(String[] args){ SpringApplication.run(Week3Application.class,args); } }