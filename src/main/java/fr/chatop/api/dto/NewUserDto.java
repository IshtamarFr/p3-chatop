package fr.chatop.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class NewUserDto {
    @Schema(example="test@test.com", required=true)
    private String email;
    @Schema(example="Jean Dupont", required=true)
    private String name;
    @Schema(example="123456", required=true)
    private String password;
}
