package com.user_management_app.controller;


import com.user_management_app.entity.Notification;
import com.user_management_app.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<Notification> createNotification(@PathVariable Long userId, @RequestBody Notification notification) {
        Notification createdNotification = notificationService.createNotification(userId, notification);
        return ResponseEntity.ok(createdNotification);
    }

    @GetMapping("/{notificationId}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long notificationId) {
        Notification notification = notificationService.getNotificationById(notificationId);
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/getAll")
    public List<Notification> getAll(){
        return notificationService.getAllNotification();
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId) {
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{notificationId}")
    public ResponseEntity<Notification> updateNotification(@PathVariable Long notificationId, @RequestBody Notification notificationDetails){

        Notification updatedNotification =  notificationService.updateNotification(notificationId, notificationDetails);
        return ResponseEntity.ok(updatedNotification);
    }

    @GetMapping("/get-all-notification-for-specific-user/{userId}")
    public ResponseEntity<List<Notification>> getAllNotificationForSpecificUser(@PathVariable Long userId){

        List<Notification> notificationList =  notificationService.getAllNotificationForSpecificUser(userId);
        return ResponseEntity.ok(notificationList);
    }
}


