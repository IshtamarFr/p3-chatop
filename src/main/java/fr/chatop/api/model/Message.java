package fr.chatop.api.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "messages")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long rental_id;
	private Long user_id;
	private String message;

	private Timestamp created_at;
	private Timestamp updated_at;
}
