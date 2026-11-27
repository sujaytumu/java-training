package com.sujay.week3.service;
import com.sujay.week3.dto.ApiDtos.EmployeeResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;
/**
 * Teaching example: process-local idempotency registry.
 * Production systems should persist keys/results with expiry and coordinate across instances.
 */
@Service public class IdempotencyKeyRegistry {
 private record Stored(String fingerprint,EmployeeResponse response){}
 private final Map<String,Stored> completed=new ConcurrentHashMap<>();
 public synchronized EmployeeResponse execute(String key,String fingerprint,Supplier<EmployeeResponse> action){
  if(key==null||key.isBlank()||key.length()>120)throw new IllegalArgumentException("A non-empty Idempotency-Key (max 120 chars) is required");
  Stored previous=completed.get(key);
  if(previous!=null){
   if(!previous.fingerprint().equals(fingerprint))throw new IllegalArgumentException("Idempotency-Key was already used with a different request");
   return previous.response();
  }
  EmployeeResponse result=action.get();
  completed.put(key,new Stored(fingerprint,result));
  return result;
 }
 public static String fingerprint(String value){
  try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8)));}
  catch(NoSuchAlgorithmException impossible){throw new IllegalStateException(impossible);}
 }
}