package goorm.wherebnb.repository;

import goorm.wherebnb.domain.dao.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPaymentId(Long paymentId);
}
