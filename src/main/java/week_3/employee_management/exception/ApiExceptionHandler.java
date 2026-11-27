package com.sujay.week3.exception;
import com.sujay.week3.dto.ApiDtos;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.*;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
@RestControllerAdvice public class ApiExceptionHandler {
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<?> validation(MethodArgumentNotValidException e,HttpServletRequest r){Map<String,String> fields=new LinkedHashMap<>();e.getBindingResult().getFieldErrors().forEach(x->fields.put(x.getField(),x.getDefaultMessage()));return error(HttpStatus.BAD_REQUEST,"Validation failed",r,fields);}
 @ExceptionHandler(EntityNotFoundException.class) ResponseEntity<?> missing(Exception e,HttpServletRequest r){return error(HttpStatus.NOT_FOUND,e.getMessage(),r,Map.of());}
 @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<?> bad(Exception e,HttpServletRequest r){return error(HttpStatus.BAD_REQUEST,e.getMessage(),r,Map.of());}
 private ResponseEntity<?> error(HttpStatus s,String m,HttpServletRequest r,Map<String,String> f){return ResponseEntity.status(s).body(new ErrorBody(Instant.now(),s.value(),s.getReasonPhrase(),m,r.getRequestURI(),f));}
 public record ErrorBody(Instant timestamp,int status,String error,String message,String path,Map<String,String> fieldErrors){}
}