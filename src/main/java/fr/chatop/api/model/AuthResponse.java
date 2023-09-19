package fr.chatop.api.model;

import lombok.Data;

@Data
public class AuthResponse {
	private String email;
	private String token;

	public AuthResponse() {
	}

	public AuthResponse(String email, String accessToken) {
		this.email = email;
		this.token = accessToken;
	}
}
