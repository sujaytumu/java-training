package day_09_day_10.state;
public class PayrollWorkflow {
 public enum Status { DRAFT, APPROVED, PROCESSING, COMPLETED, FAILED }
 private Status status=Status.DRAFT;
 public void approve(){if(status!=Status.DRAFT)throw new IllegalStateException("Only draft payroll can be approved");status=Status.APPROVED;}
 public void start(){if(status!=Status.APPROVED)throw new IllegalStateException("Approval required");status=Status.PROCESSING;}
 public void complete(){if(status!=Status.PROCESSING)throw new IllegalStateException("Not processing");status=Status.COMPLETED;}
 public void fail(){if(status!=Status.PROCESSING)throw new IllegalStateException("Not processing");status=Status.FAILED;}
 public Status status(){return status;}
}
