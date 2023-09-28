package fr.chatop.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
	@Schema(hidden = true)
	private Long id;

	@Schema(name="rental id",example = "1", required = true)
	private Long rental_id;
	@Schema(name="receiver user id",example = "1", required = true)
	private Long user_id;
	@Schema(name="message",example = "I love this house", required = true)
	private String message;

	@Schema(hidden = true)
	private Timestamp created_at;
	@Schema(hidden = true)
	private Timestamp updated_at;
}
