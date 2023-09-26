package fr.chatop.api.controller.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDto {
    private long id;
    private String email;
    private String name;
    private Timestamp created_at;
    private Timestamp updated_at;
}
