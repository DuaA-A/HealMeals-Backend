package HealMeals.Api.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;
import java.math.BigDecimal;

@Entity
@Table(name = "donation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Donation {

    @Id
    @GeneratedValue
    private UUID donationId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String secondName;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    private String message;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private String expiryDate;

    @Column(nullable = false)
    private String cvv;

    // الربط مع الـ User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Optional: if you plan to extend payments
    private String paymentMethod;
    private String transactionId;
}
