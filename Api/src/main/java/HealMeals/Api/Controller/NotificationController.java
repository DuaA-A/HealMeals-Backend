package HealMeals.Api.Controller;

import HealMeals.Api.DTO.NotificationDto;
import HealMeals.Api.model.User;
import HealMeals.Api.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/{userMail}")
    public ResponseEntity<NotificationDto> createNotification
            (@PathVariable String userMail, @RequestBody NotificationDto dto) {
        return ResponseEntity.ok(notificationService.createNotification(dto, userMail));
    }

    @GetMapping("/{mail}/unread")
    public ResponseEntity<List<NotificationDto>> getUnreadNotification(@PathVariable String mail){
        return ResponseEntity.ok(notificationService.getUnreadNotification(mail));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<String> markAsRead (@PathVariable UUID id){
        notificationService.markAsRead(id);
        return ResponseEntity.ok("Notification Marked As Read");
    }
}
