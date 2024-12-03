package com.user_management_app.repository;

import com.user_management_app.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;



public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
