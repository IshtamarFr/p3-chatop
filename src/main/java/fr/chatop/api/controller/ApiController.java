package fr.chatop.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
	@GetMapping("/test")
	public String getTest() {
		return "Test OK";
	}
}
