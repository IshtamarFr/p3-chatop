package fr.chatop.api.controller;

import fr.chatop.api.config.util.JwtTokenUtil;
import fr.chatop.api.controller.dto.AuthResponse;
import fr.chatop.api.controller.dto.UserDto;
import fr.chatop.api.model.User;
import fr.chatop.api.service.UserService;
import fr.chatop.api.service.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@ControllerAdvice
@CrossOrigin(origins = "*")
public class UserController {
	@Autowired private UserService userService=new UserServiceImpl();
	@Autowired private JwtTokenUtil jwtUtil;

	@ApiOperation("[Test-Only] Gets data from User by Id")
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUser(@PathVariable("id") final long id) {
		Optional<UserDto> candidate = userService.getUser(id);
		if (candidate.isPresent()) {
			return ResponseEntity.ok().body(candidate);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation("Registers new User (email must be unique)")
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

	@ApiOperation("Gets my own data if I'm logged in")
	@GetMapping("/auth/me")
	// Endpoint is secured so no need to check if jwt exists and is valid
	public ResponseEntity<?> getMe(@RequestHeader("Authorization") String jwt) {
		try {
			long id = jwtUtil.getIdFromValidToken(jwt);
			Optional<UserDto> candidate = userService.getUser(id);
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
