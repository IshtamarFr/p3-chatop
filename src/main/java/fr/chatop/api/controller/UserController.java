package fr.chatop.api.controller;

import fr.chatop.api.config.util.JwtTokenUtil;
import fr.chatop.api.controller.dto.AuthResponse;
import fr.chatop.api.controller.dto.UserDto;
import fr.chatop.api.model.User;
import fr.chatop.api.service.UserService;
import fr.chatop.api.service.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
	@Autowired private UserService userService=new UserServiceImpl();
	@Autowired private JwtTokenUtil jwtUtil;

	@ApiOperation("[Test-Only] Gets data from User by Id")
	@GetMapping("/user/{id}")
	public UserDto getUser(@PathVariable("id") final Long id) {
		return userService.getUser(id);
	}

	@ApiOperation("Registers new User (email must be unique)")
	@PostMapping("/auth/register")
	public AuthResponse addUser(@RequestBody User user) {
		User candidate = userService.saveUser(user);
		String accessToken = jwtUtil.generateAccessToken(candidate);
		return new AuthResponse(user.getEmail(), accessToken);
	}

	@ApiOperation("Gets my own data if I'm logged in")
	@GetMapping("/auth/me")
	// Endpoint is secured so no need to check if jwt exists and is valid
	public UserDto getMe(@RequestHeader("Authorization") String jwt) {
		Long id = jwtUtil.getIdFromValidToken(jwt);
		return userService.getUser(id);
	}
}
