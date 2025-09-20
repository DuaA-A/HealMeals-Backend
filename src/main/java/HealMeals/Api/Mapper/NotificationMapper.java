package HealMeals.Api.Mapper;

import HealMeals.Api.DTO.NotificationDto;
import HealMeals.Api.model.Notification;
import HealMeals.Api.model.User;

public class NotificationMapper {

    public static NotificationDto toDto (Notification notification){
        return NotificationDto.builder()
                .notification_id(notification.getNotification_id())
                .dateSent(notification.getDateSent())
                .isRead(notification.getIsRead())
                .message(notification.getMessage())
                .user_id(notification.getUser().getUserId())
                .build();
    }

    public static Notification toEntity (NotificationDto dto, User user) {
        return Notification.builder()
                .notification_id(dto.getNotification_id())
                .dateSent(dto.getDateSent())
                .user(user)
                .message(dto.getMessage())
                .isRead(dto.getIsRead())
                .build();
    }
}
