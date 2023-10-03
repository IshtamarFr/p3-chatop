package fr.chatop.api.controller;

import fr.chatop.api.dto.MessageDto;
import fr.chatop.api.service.MessageService;
import fr.chatop.api.service.impl.MessageServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class MessageController {
	@Autowired private MessageService messageService=new MessageServiceImpl();

	@ApiOperation("Creates a new message")
	@Operation(responses={
			@ApiResponse(responseCode = "200", description="Message sent successfully"),
			@ApiResponse(responseCode = "401", description="Bad credentials"),
			@ApiResponse(responseCode = "404", description="User or rental not found")
	})
	@PostMapping("/messages")
	public ResponseEntity<String> postNewMessage(@RequestBody MessageDto message, @RequestHeader("Authorization") String jwt) {
			messageService.saveMessage(message);
			return ResponseEntity.ok().body("Message sent with success");
	}
}
