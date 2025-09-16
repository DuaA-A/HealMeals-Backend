package HealMeals.Api.service;

import HealMeals.Api.DTO.NotificationDto;
import HealMeals.Api.Mapper.NotificationMapper;
import HealMeals.Api.Repo.NotificationRepository;
import HealMeals.Api.Repo.UserRepository;
import HealMeals.Api.model.Notification;
import HealMeals.Api.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    public NotificationDto createNotification(NotificationDto dto, String userMail){
        User user = userRepository.findByEmail(userMail)
                .orElseThrow(()->new RuntimeException("User Mail Not Found"));
        Notification notification = NotificationMapper.toEntity(dto, user);
        notification.setDateSent(LocalDateTime.now());
        notification.setIsRead(false);

        Notification saved = notificationRepository.save(notification);

        sendMail(userMail, saved.getMessage());

        return NotificationMapper.toDto(saved);
    }

    public void sendMail(String userMail, String msg){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userMail);
        mailMessage.setSubject("New Notification From HealMeals");
        mailMessage.setText(msg);
        mailSender.send(mailMessage);
    }

    public List<NotificationDto> getUnreadNotification(String mail){
        User user = userRepository.findByEmail(mail)
                .orElseThrow(()->new RuntimeException("User Not Found"));

        return notificationRepository.findByIsReadFalseAndUser_UserId(user.getUserId())
                .stream().map(NotificationMapper::toDto)
                .collect(Collectors.toList());
    }

    public void markAsRead(UUID notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(()->new RuntimeException("Notification Not Found"));

        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

}
