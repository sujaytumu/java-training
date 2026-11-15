package day_05.exception;

public class EmployeeException extends RuntimeException {

    public EmployeeException(String message) {
        super(message);
    }

    public static class InvalidEmployeeException extends EmployeeException {
        public InvalidEmployeeException(String message) {
            super(message);
        }
    }

    public static class EmployeeNotFoundException extends EmployeeException {
        public EmployeeNotFoundException(String message) {
            super(message);
        }
    }
}