package fr.chatop.api.controller;

import fr.chatop.api.model.Message;
import fr.chatop.api.service.MessageService;
import fr.chatop.api.service.MessageServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
	public String postNewMessage(@RequestBody Message message) {
			messageService.saveMessage(message);
			return "Message sent with success";
	}
}
