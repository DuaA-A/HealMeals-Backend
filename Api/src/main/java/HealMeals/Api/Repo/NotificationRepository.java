package HealMeals.Api.Repo;

import HealMeals.Api.DTO.NotificationDto;
import HealMeals.Api.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByIsReadFalseAndUser_UserId(UUID userId);
}
