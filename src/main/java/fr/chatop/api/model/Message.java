package fr.chatop.api.model;

import lombok.Data;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "messages")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private long rental_id;
	private long user_id;
	private String message;

	private Timestamp created_at;
	private Timestamp updated_at;
}
