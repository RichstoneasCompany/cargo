package com.richstone.cargo.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class NotificationMessage {
    private String token;
    private String title;
    private String message;
    private String topic;
}
