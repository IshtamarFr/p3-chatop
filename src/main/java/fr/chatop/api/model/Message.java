package fr.chatop.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name="rental_id",referencedColumnName = "id")
	private Rental rental;

	@ManyToOne
	@JoinColumn(name="user_id",referencedColumnName = "id")
	private User user;

	private String message;
	private Timestamp created_at;
	private Timestamp updated_at;
}
