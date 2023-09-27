package fr.chatop.api.controller.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class RentalDto {
    private long id;

    private String name;
    private float surface;
    private float price;
    private String picture;
    private String description;
    private long owner_id;

    private Timestamp created_at;
    private Timestamp updated_at;
}