package HealMeals.Api.Controller;

import HealMeals.Api.DTO.NotificationDto;
import HealMeals.Api.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/notification")
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/{userMail}")
    public NotificationDto createNotification (
            @PathVariable String userMail, @RequestBody NotificationDto dto)
    {
        return notificationService.createNotification(dto, userMail);
    }
}
