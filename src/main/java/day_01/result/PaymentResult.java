package day_01.result;

//sealed classes
public sealed interface PaymentResult
        permits PaymentResult.Success,
                PaymentResult.Failure {

    //records
    record Success(
            int paymentId,
            String transactionId,
            double amount
    ) implements PaymentResult {

    }

    record Failure(
            int paymentId,
            String reason
    ) implements PaymentResult {

    }
}
