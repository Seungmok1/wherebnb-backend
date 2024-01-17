package goorm.wherebnb.domain.dto.request;

import goorm.wherebnb.domain.dao.PaymentMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentRequest {

    private PaymentMethod paymentMethod;

    private String email;

    private String cardNumber;

    private String expirationMonth;

    private String expirationYear;

    private int cvc;

    private int totalPrice;

    private String nation;

    @Builder

    public PaymentRequest(PaymentMethod paymentMethod, String email, String cardNumber, String expirationMonth,
                          String expirationYear, int cvc, int totalPrice, String nation) {
        this.paymentMethod = paymentMethod;
        this.email = email;
        this.cardNumber = cardNumber;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.cvc = cvc;
        this.totalPrice = totalPrice;
        this.nation = nation;
    }
}
