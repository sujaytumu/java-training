package com.sujay.week3.security;
import java.util.List;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;
@Configuration public class SecurityConfig {
 @Bean PasswordEncoder encoder(){return new BCryptPasswordEncoder();}
 @Bean UserDetailsService users(PasswordEncoder e){return new InMemoryUserDetailsManager(User.withUsername("admin").password(e.encode("admin123")).roles("ADMIN","USER").build(),User.withUsername("reader").password(e.encode("reader123")).roles("USER").build());}
 @Bean AuthenticationManager authenticationManager(AuthenticationConfiguration c)throws Exception{return c.getAuthenticationManager();}
 @Bean SecurityFilterChain chain(HttpSecurity h,JwtFilter f)throws Exception{
  h.csrf(c->c.disable()).cors(c->c.configurationSource(corsSource())).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
   .authorizeHttpRequests(a->a.requestMatchers("/api/v1/auth/login","/swagger-ui/**","/v3/api-docs/**","/actuator/health","/h2-console/**").permitAll().anyRequest().authenticated())
   .headers(x->x.frameOptions(y->y.sameOrigin())).addFilterBefore(f,UsernamePasswordAuthenticationFilter.class);
  return h.build();
 }
 @Bean CorsConfigurationSource corsSource(){CorsConfiguration c=new CorsConfiguration();c.setAllowedOrigins(List.of("http://localhost:3000"));c.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));c.setAllowedHeaders(List.of("Authorization","Content-Type"));UrlBasedCorsConfigurationSource s=new UrlBasedCorsConfigurationSource();s.registerCorsConfiguration("/**",c);return s;}
}