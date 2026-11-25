package day_12_day_13.exception;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
@RestControllerAdvice
public class GlobalExceptionHandler {
 @ExceptionHandler(ResourceNotFoundException.class)
 public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex){return build(HttpStatus.NOT_FOUND,ex.getMessage(),Map.of());}
 @ExceptionHandler(MethodArgumentNotValidException.class)
 public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex){
  Map<String,String> errors=new LinkedHashMap<>();
  ex.getBindingResult().getFieldErrors().forEach(error->errors.putIfAbsent(error.getField(),error.getDefaultMessage()));
  return build(HttpStatus.BAD_REQUEST,String.join("; ",errors.values()),errors);
 }
 @ExceptionHandler(IllegalArgumentException.class)
 public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException ex){return build(HttpStatus.BAD_REQUEST,ex.getMessage(),Map.of());}
 @ExceptionHandler(DataIntegrityViolationException.class)
 public ResponseEntity<ErrorResponse> handleConflict(DataIntegrityViolationException ex){
  return build(HttpStatus.CONFLICT,"The request conflicts with the current database state",Map.of());
 }
 @ExceptionHandler(Exception.class)
 public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex){
  // Do not expose internal exception details to API clients.
  return build(HttpStatus.INTERNAL_SERVER_ERROR,"An unexpected error occurred",Map.of());
 }
 private ResponseEntity<ErrorResponse> build(HttpStatus status,String message,Map<String,String> fields){
  ErrorResponse body=new ErrorResponse(LocalDateTime.now(),status.value(),status.getReasonPhrase(),message,fields);
  return ResponseEntity.status(status).body(body);
 }
}