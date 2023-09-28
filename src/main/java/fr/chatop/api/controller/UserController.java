package fr.chatop.api.controller;

import fr.chatop.api.config.util.JwtTokenUtil;
import fr.chatop.api.controller.dto.AuthResponse;
import fr.chatop.api.controller.dto.UserDto;
import fr.chatop.api.model.User;
import fr.chatop.api.service.UserService;
import fr.chatop.api.service.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
	@Autowired private UserService userService=new UserServiceImpl();
	@Autowired private JwtTokenUtil jwtUtil;

	@ApiIgnore
	@GetMapping("/user/{id}")
	public UserDto getUser(@PathVariable("id") final Long id) {
		return userService.getUser(id);
	}

	@ApiOperation("Registers new User (email must be unique)")
	@Operation(responses={
			@ApiResponse(responseCode = "200", description="User successfully registered"),
			@ApiResponse(responseCode = "409", description="Email is not available")
	})
	@PostMapping("/auth/register")
	public AuthResponse addUser(@RequestBody User user) {
		User candidate = userService.saveUser(user);
		String accessToken = jwtUtil.generateAccessToken(candidate);
		return new AuthResponse(candidate.getEmail(), accessToken);
	}

	@ApiOperation("Gets my own data if I'm logged in")
	@Operation(responses={
			@ApiResponse(responseCode = "200", description="User public data retrieval"),
			@ApiResponse(responseCode = "401", description="Bad credentials"),
	})
	@GetMapping("/auth/me")
	// Endpoint is secured so no need to check if jwt exists and is valid
	public UserDto getMe(@RequestHeader("Authorization") String jwt) {
		Long id = jwtUtil.getIdFromValidToken(jwt);
		return userService.getUser(id);
	}
}
