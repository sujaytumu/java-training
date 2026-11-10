package day_01.model;

public record Payment(
        int paymentId,
        String customerName,
        double amount,
        PaymentMode paymentMode
) {
}
