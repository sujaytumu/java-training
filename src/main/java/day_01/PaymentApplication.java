package day_01;

import day_01.model.Payment;
import day_01.model.PaymentMode;
import day_01.result.PaymentResult;
import day_01.service.PaymentService;

public class PaymentApplication {
    public static void main(String[] args) {

        // create payment
        Payment payment = new Payment(
                101,
                "Sujay",
                2500.0,
                PaymentMode.UPI
        );

        //create service object
        PaymentService paymentService = new PaymentService();

        //process payment
        PaymentResult result = paymentService.processPayment(payment);

        System.out.println(payment.paymentId());
        System.out.println(payment.customerName());
        System.out.println(payment.amount());
        System.out.println(payment.paymentMode());


        System.out.println("Welcome to day-01");
    }
}