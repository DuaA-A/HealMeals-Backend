package HealMeals.Api.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class DonationResponseDTO {
    private UUID donationId;
    private String firstName;
    private String secondName;
    private String phoneNumber;
    private String email;
    private BigDecimal amount;
    private String message;
    private String paymentMethod;
}
