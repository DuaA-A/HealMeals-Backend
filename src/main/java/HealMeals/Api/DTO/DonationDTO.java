package HealMeals.Api.DTO;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;
import java.math.BigDecimal;

@Data
public class DonationDTO {
    private UUID donationId;
    private UUID userId;
    private BigDecimal amount;
    private LocalDateTime date;
    private String paymentMethod;
    private String transactionId;
}
