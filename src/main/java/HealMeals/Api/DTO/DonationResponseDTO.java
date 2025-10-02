package HealMeals.Api.DTO;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
