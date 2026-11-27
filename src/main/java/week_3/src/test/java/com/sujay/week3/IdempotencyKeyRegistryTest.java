package com.sujay.week3;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;
import com.sujay.week3.dto.ApiDtos.EmployeeResponse;
import com.sujay.week3.service.IdempotencyKeyRegistry;
import org.junit.jupiter.api.Test;
class IdempotencyKeyRegistryTest {
 @Test void repeatedKeyAndSamePayloadReturnsOriginalResultWithoutRepeatingWork(){
  IdempotencyKeyRegistry registry=new IdempotencyKeyRegistry();
  AtomicInteger executions=new AtomicInteger();
  var expected=new EmployeeResponse(1L,"Asha","asha@example.com",new BigDecimal("65000"),1L,"Engineering",0);
  var first=registry.execute("req-001","payload-hash",()->{executions.incrementAndGet();return expected;});
  var second=registry.execute("req-001","payload-hash",()->{executions.incrementAndGet();return expected;});
  assertSame(first,second);
  assertEquals(1,executions.get());
 }
 @Test void sameKeyWithDifferentPayloadIsRejected(){
  IdempotencyKeyRegistry registry=new IdempotencyKeyRegistry();
  var expected=new EmployeeResponse(1L,"Asha","asha@example.com",new BigDecimal("65000"),1L,"Engineering",0);
  registry.execute("req-001","hash-one",()->expected);
  assertThrows(IllegalArgumentException.class,()->registry.execute("req-001","hash-two",()->expected));
 }
}