package HealMeals.Api.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {
    private UUID notification_id;
    private UUID user_id;
    private String message;
    private LocalDateTime dateSent;
    private Boolean isRead;
}
