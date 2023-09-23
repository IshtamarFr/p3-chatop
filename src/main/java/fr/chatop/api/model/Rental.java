package fr.chatop.api.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

	@Column(name = "owner_id")
	private long ownerId;

	private Timestamp created_at;
	private Timestamp updated_at;
}
