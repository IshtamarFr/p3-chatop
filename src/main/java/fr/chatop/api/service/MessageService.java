package fr.chatop.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.chatop.api.model.Message;
import fr.chatop.api.repository.MessageRepository;

@Service
public class MessageService {
	@Autowired
	private MessageRepository messageRepository;

	public Message saveMessage(Message message) {
		return messageRepository.save(message);
	}
}
