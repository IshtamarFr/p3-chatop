package fr.chatop.api.model;

import java.sql.Timestamp;

import javax.persistence.*;

import lombok.Data;

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
