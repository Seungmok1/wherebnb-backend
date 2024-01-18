package goorm.wherebnb.service;

import goorm.wherebnb.domain.dao.Payment;
import goorm.wherebnb.domain.dao.User;
import goorm.wherebnb.domain.dto.request.PaymentRequest;
import goorm.wherebnb.repository.PaymentRepository;
import goorm.wherebnb.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;


    public Payment createPayment(PaymentRequest paymentRequest) {

        User findUser = userRepository.findByEmail(paymentRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("User not found."));

        Payment newPayment = Payment.builder()
                .totalPrice(paymentRequest.getTotalPrice())
                .paymentMethod(paymentRequest.getPaymentMethod())
                .user(findUser)
                .build();

        paymentRepository.save(newPayment);

        return newPayment;
    }
}
