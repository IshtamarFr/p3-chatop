package fr.chatop.api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "rentals")
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
