package fr.chatop.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Data
public class AuthRequest {
	@NotNull
	@Email
	@Length(min = 5, max = 64)
	@Schema(example="test@test.com", required=true)
	private String email;

	@NotNull
	@Length(min = 5, max = 64)
	@Schema(example="123456", required=true)
	private String password;
}
