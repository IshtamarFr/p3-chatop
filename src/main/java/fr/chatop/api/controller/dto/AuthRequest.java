package fr.chatop.api.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

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
