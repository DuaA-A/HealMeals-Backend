package HealMeals.Api.DTO;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;
import java.math.BigDecimal;

@Data
public class DonationRequestDTO {
    private String firstName;
    private String secondName;
    private String phoneNumber;
    private String email;
    private BigDecimal amount;
    private String message;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String paymentMethod;
}
