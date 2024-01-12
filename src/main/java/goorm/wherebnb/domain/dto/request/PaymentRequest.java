package goorm.wherebnb.domain.dto.request;

import goorm.wherebnb.domain.dao.PaymentMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentRequest {

    private PaymentMethod paymentMethod;

    private String cardNumber;

    private String expireDay;

    private int cvvNumber;

    private String zipcode;

    private String nation;

    @Builder
    public PaymentRequest(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Builder
    public PaymentRequest(PaymentMethod paymentMethod, String cardNumber, String expireDay,
                          int cvvNumber, String zipcode, String nation) {
        this.paymentMethod = paymentMethod;
        this.cardNumber = cardNumber;
        this.expireDay = expireDay;
        this.cvvNumber = cvvNumber;
        this.zipcode = zipcode;
        this.nation = nation;
    }
}
