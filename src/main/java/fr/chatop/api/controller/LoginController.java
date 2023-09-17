package fr.chatop.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.chatop.api.model.Login;
import fr.chatop.api.service.LoginService;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {
	@Autowired
	private LoginService loginService;

	@PostMapping("/auth/login")
	public int addUser(@RequestBody Login login) {
		return loginService.verifyUser(login);
	}
}
