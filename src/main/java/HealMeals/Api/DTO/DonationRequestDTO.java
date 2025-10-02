package HealMeals.Api.DTO;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;
import java.math.BigDecimal;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
