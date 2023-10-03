package fr.chatop.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class RentalDto {
    @Schema(example="1")
    private long id;

    @Schema(example="My dream house")
    private String name;
    @Schema(example="700.0")
    private float surface;
    @Schema(example="850.0")
    private float price;
    @Schema(example="https://something.com")
    private String picture;
    @Schema(example="My house at the seaside")
    private String description;
    @Schema(example="2")
    private long owner_id;

    @Schema(hidden=true)
    private Timestamp created_at;
    @Schema(hidden=true)
    private Timestamp updated_at;
}
