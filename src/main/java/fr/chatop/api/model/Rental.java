package fr.chatop.api.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "rentals")
public class Rental {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private float surface;
	private float price;
	private String picture;
	private String description;
	private Long owner_id;

	private Timestamp created_at;
	private Timestamp updated_at;
}
