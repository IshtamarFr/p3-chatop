package fr.chatop.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AuthResponse {
	@Schema(example="test@test.com")
	private String email;
	@Schema(example="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyNCxzdGVwaGFuZS5ndWlsYmVydDYyQGdtYWlsLmNvbSIsImlzcyI6IklzaHRhIiwiaWF0IjoxNjk1OTA4NDY2LCJleHAiOjE2OTU5OTQ4NjZ9.hyttaJRex-rYC6vjI1-KOGG8KwXPhbj3wuXReaktnZaBBjnemRmwGgCnvSyNxDgd86pxX_4frauTqsySGbsNqA")
	private String token;

	public AuthResponse() {
	}

	public AuthResponse(String email, String accessToken) {
		this.email = email;
		this.token = accessToken;
	}
}
