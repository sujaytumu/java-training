
import day_01.model.Payment;
import day_01.model.PaymentMode;

public class PaymentApplication {
    public static void main(String[] args) {

        Payment payment = new Payment(
                101,
                "Sujay",
                2500.0,
                PaymentMode.UPI
        );

        System.out.println(payment.paymentId());
        System.out.println(payment.customerName());
        System.out.println(payment.amount());
        System.out.println(payment.paymentMode());


        System.out.println("Welcome to day-01");
    }
}