package fr.chatop.api.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Entity
@Table(name = "messages")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private long id;

	@Column(name = "rental_id")
	private long rentalId;

	@Column(name = "user_id")
	private long userId;

	private String message;

	@Column(name = "created_at")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Timestamp createdAt;

	@Column(name = "updated_at")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Timestamp updatedAt;
}
