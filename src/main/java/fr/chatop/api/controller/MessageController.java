package fr.chatop.api.controller;

import fr.chatop.api.model.Message;
import fr.chatop.api.service.MessageService;
import fr.chatop.api.service.MessageServiceImpl;
import io.swagger.annotations.ApiOperation;
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
	@PostMapping("/messages")
	public String postNewMessage(@RequestBody Message message) {
			messageService.saveMessage(message);
			return "Message sent with success";
	}
}
