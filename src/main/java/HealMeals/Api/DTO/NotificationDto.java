package HealMeals.Api.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NotificationDto {
    private UUID notification_id;
    private UUID user_id;
    private String message;
    private LocalDateTime dateSent;
    private Boolean isRead;
}
