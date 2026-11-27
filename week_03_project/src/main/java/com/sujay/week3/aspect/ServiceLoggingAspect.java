package com.sujay.week3.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.*;
import org.springframework.stereotype.Component;
@Aspect @Component
public class ServiceLoggingAspect {
 private static final Logger log=LoggerFactory.getLogger(ServiceLoggingAspect.class);
 @Around("within(com.sujay.week3.service..*)")
 public Object log(ProceedingJoinPoint p) throws Throwable {
  long start=System.nanoTime(); try{return p.proceed();}
  finally{log.info("{} took {} µs",p.getSignature().toShortString(),(System.nanoTime()-start)/1000);}
 }
}