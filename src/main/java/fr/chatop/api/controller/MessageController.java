package fr.chatop.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.chatop.api.model.Message;
import fr.chatop.api.service.MessageService;

@RestController
@CrossOrigin("*")
public class MessageController {
	@Autowired
	private MessageService messageService;

	@PostMapping("/message")
	public ResponseEntity<?> postNewMessage(Message message) {
		try {
			messageService.saveMessage(message);
			return ResponseEntity.ok().body(message);
		} catch (Exception e) {
			return ResponseEntity.status(409).body(e);
		}
	}
}
