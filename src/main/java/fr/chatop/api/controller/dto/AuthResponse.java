package fr.chatop.api.controller.dto;

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
