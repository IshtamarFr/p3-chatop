package fr.chatop.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MessageDto {
    @Schema(example = "1", required = true)
    private Long rental_id;
    @Schema(example = "2", required = true)
    private Long user_id;
    @Schema(example = "I love this house", required = true)
    private String message;
}
