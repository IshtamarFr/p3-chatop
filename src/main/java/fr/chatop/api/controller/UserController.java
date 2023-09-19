package fr.chatop.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.chatop.api.model.User;
import fr.chatop.api.service.UserService;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
	@Autowired
	private UserService userService;

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
	public void addUser(@RequestBody User user) {
		userService.saveUser(user);
	}

}
