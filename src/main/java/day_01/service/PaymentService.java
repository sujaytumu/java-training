package day_01.service;

import day_01.model.Payment;
import day_01.result.PaymentResult;

import java.util.UUID;

public class PaymentService {
    //process payment method
    public PaymentResult processPayment(Payment payment) {

        //validate amount
        if(payment.amount() < 0){
            return new PaymentResult.Failure(
                    payment.paymentId(),
                    "Invalid payment amount"
            );
        }
        //payment string
        String transactionId = switch(payment.paymentMode()){
            case UPI -> "UPI-" + UUID.randomUUID().toString();//UUID genrates random unique id
            case  CARD -> "CARD-" + UUID.randomUUID().toString();
            case CASH -> "CASH-" + UUID.randomUUID().toString();
            case NET_BANKING -> "NET_BANKING-" + UUID.randomUUID().toString();
        };

        //success case
        return new PaymentResult.Success(
                payment.paymentId(),
                transactionId,
                payment.amount()
        );
    }

    //payment result
    // Display payment result
    public void displayResult(PaymentResult result) {

        switch (result) {

            case PaymentResult.Success success -> {

                System.out.println("Payment Successful!");

                System.out.println(
                        "Payment ID: " + success.paymentId()
                );

                System.out.println(
                        "Transaction ID: " + success.transactionId()
                );

                System.out.println(
                        "Amount: " + success.amount()
                );
            }

            case PaymentResult.Failure failure -> {

                System.out.println("Payment Failed!");

                System.out.println(
                        "Payment ID: " + failure.paymentId()
                );

                System.out.println(
                        "Reason: " + failure.reason()
                );
            }

        }
    }
}
