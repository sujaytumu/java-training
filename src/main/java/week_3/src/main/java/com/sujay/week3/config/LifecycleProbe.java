package com.sujay.week3.config;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.*;
import org.springframework.stereotype.Component;
@Component public class LifecycleProbe {
 private static final Logger log=LoggerFactory.getLogger(LifecycleProbe.class);
 @PostConstruct void init(){log.info("LifecycleProbe initialized after dependency injection");}
 @PreDestroy void close(){log.info("LifecycleProbe is being destroyed");}
}