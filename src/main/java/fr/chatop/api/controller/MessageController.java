package fr.chatop.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.chatop.api.model.Message;
import fr.chatop.api.service.MessageService;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
public class MessageController {
	@Autowired
	private MessageService messageService;

	@ApiOperation("Creates a new message")
	@PostMapping("/messages")
	public ResponseEntity<?> postNewMessage(@RequestBody Message message) {
		try {
			messageService.saveMessage(message);
			return ResponseEntity.ok().body("Message sent with success");
		} catch (Exception e) {
			return ResponseEntity.status(409).body(e);
		}
	}
}
