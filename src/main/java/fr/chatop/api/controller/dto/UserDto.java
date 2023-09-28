package fr.chatop.api.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDto {
    @Schema(example="2")
    private long id;
    @Schema(example="test@test.com")
    private String email;
    @Schema(example="Jean Dupont")
    private String name;
    private Timestamp created_at;
    private Timestamp updated_at;
}
