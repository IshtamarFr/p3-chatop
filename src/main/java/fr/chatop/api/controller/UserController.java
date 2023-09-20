package fr.chatop.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import fr.chatop.api.config.JwtTokenFilter;
import fr.chatop.api.config.JwtTokenUtil;
import fr.chatop.api.model.AuthResponse;
import fr.chatop.api.model.User;
import fr.chatop.api.service.UserService;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private JwtTokenUtil jwtUtil;
	@Autowired
	private JwtTokenFilter jwtTokenFilter;

	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUser(@PathVariable("id") final long id) {
		Optional<User> candidate = userService.getUser(id);
		if (candidate.isPresent()) {
			return ResponseEntity.ok().body(candidate);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/auth/register")
	public ResponseEntity<?> addUser(@RequestBody User user) {
		try {
			User candidate = userService.saveUser(user);
			String accessToken = jwtUtil.generateAccessToken(candidate);
			AuthResponse response = new AuthResponse(user.getEmail(), accessToken);
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			return ResponseEntity.status(409).body(e);
		}
	}

	@GetMapping("/auth/me")
	// Endpoint is secured so no need to check if jwt exists and is valid
	public ResponseEntity<?> getMe(@RequestHeader("Authorization") String jwt) {
		try {
			String token = jwt.split(" ")[1].trim();
			String[] jwtSubject = jwtUtil.getSubject(token).split(",");
			Long id = Long.parseLong(jwtSubject[0]);

			Optional<User> candidate = userService.getUser(id);
			if (candidate.isPresent()) {
				return ResponseEntity.ok().body(candidate);
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(409).body(e);
		}
	}
}
