package com.user_management_app.service;

import com.user_management_app.entity.Notification;
import com.user_management_app.entity.User;
import com.user_management_app.repository.NotificationRepository;
import com.user_management_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {


    private final NotificationRepository notificationRepository;

    private final   UserRepository userRepository;


    public Notification createNotification(Long userId, Notification notification) {
        Optional<User> users = userRepository.findById(userId);
        if (users.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + userId);
        }

        User user = users.get();
        notification.setUser(user);
        notification.setCreatedAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    public List<Notification> getAllNotification(){
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(Long notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + notificationId));
    }


    public void deleteNotification(Long notificationId) {
        if (!notificationRepository.existsById(notificationId)) {
            throw new RuntimeException("Notification not found with ID: " + notificationId);
        }
        notificationRepository.deleteById(notificationId);
    }

    public Notification updateNotification(Long notificationId, Notification notification) {

        Notification existingNotification = notificationRepository.findById(notificationId)
                .orElseThrow(()-> new RuntimeException("notification not found with id: " + notificationId));

        existingNotification.setTitle(notification.getTitle());
        existingNotification.setMessage(notification.getMessage());

        return notificationRepository.save(existingNotification);
    }

    public List<Notification> getAllNotificationForSpecificUser(Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found with this id: " + userId));

        return new ArrayList<>(user.getNotifications());
    }
}
