package fr.chatop.api.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class AuthRequest {
	@NotNull
	@Email
	@Length(min = 5, max = 64)
	@Schema(name="user email", example="test@test.com", required=true)
	private String email;

	@NotNull
	@Length(min = 5, max = 64)
	@Schema(name="user password", example="123456", required=true)
	private String password;
}
