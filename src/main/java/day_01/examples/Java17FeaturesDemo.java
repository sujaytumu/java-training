package day_01.examples;

import day_01.model.Payment;
import day_01.model.PaymentMode;
import day_01.result.PaymentResult;

public class Java17FeaturesDemo {

    public static void main(String[] args) {

        // 1. RECORDS
        Payment payment = new Payment(
                101,
                "Sujay",
                2500.0,
                PaymentMode.UPI
        );

        System.out.println("RECORD DEMO");
        System.out.println(payment);


        // 2. SEALED INTERFACE
        PaymentResult result =
                new PaymentResult.Success(
                        101,
                        "UPI12345",
                        2500.0
                );

        System.out.println("\nSEALED INTERFACE DEMO");
        System.out.println(result);


        // 3. PATTERN MATCHING FOR INSTANCEOF
        System.out.println("\nINSTANCEOF PATTERN MATCHING");

        if (result instanceof PaymentResult.Success success) {

            System.out.println(
                    "Transaction ID: " + success.transactionId()
            );

            System.out.println(
                    "Amount: " + success.amount()
            );
        }


        // 4. TEXT BLOCKS
        System.out.println("\nTEXT BLOCK DEMO");

        String paymentDetails = """
                {
                    "paymentId": 101,
                    "customerName": "Sujay",
                    "amount": 2500.0,
                    "paymentMode": "UPI"
                }
                """;

        System.out.println(paymentDetails);


        // 5. SWITCH EXPRESSIONS
        System.out.println("SWITCH EXPRESSION DEMO");

        double fee = switch (payment.paymentMode()) {

            case UPI -> 0.0;
            case CARD -> 20.0;
            case CASH -> 0.0;
            case NET_BANKING -> 10.0;

        };

        System.out.println("Payment fee: " + fee);

    }

}