package fr.chatop.api.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class AuthRequest {
	@NotNull
	@Email
	@Length(min = 5, max = 64)
	private String email;

	@NotNull
	@Length(min = 5, max = 64)
	private String password;
}
