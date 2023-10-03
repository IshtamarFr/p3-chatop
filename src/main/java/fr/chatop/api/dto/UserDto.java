package fr.chatop.api.dto;

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
    @Schema(hidden=true)
    private Timestamp created_at;
    @Schema(hidden=true)
    private Timestamp updated_at;
}
