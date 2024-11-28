package com.user_management_app.dto;

import lombok.Data;

import java.util.List;

@Data
public class NotificationDto {

    private String title;
    private String message;
    private List<Long> userId;
}
