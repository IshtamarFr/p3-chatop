package fr.chatop.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class NewUserDto {
    @Schema(hidden=true)
    private Long id;
    @Schema(example="test@test.com", required=true)
    private String email;
    @Schema(example="Jean Dupont", required=true)
    private String name;
    @Schema(example="123456", required=true)
    private String password;
    @Schema(hidden=true)
    private Timestamp created_at;
    @Schema(hidden=true)
    private Timestamp updated_at;
}
