package fr.chatop.api.model;

import java.sql.Timestamp;

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
	private long id;
	private long rental_id;
	private long user_id;

	private String message;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Timestamp created_at;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Timestamp updated_at;
}
