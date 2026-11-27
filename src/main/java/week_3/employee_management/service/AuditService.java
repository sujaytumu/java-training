package com.sujay.week3.service;
import org.slf4j.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
@Service public class AuditService {
 private static final Logger log=LoggerFactory.getLogger(AuditService.class);
 @Transactional(propagation=Propagation.REQUIRES_NEW)
 public void record(String action,Long id){log.info("AUDIT {} employee={}",action,id);}
}