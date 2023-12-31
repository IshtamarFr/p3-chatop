package fr.chatop.api.service.impl;

import fr.chatop.api.config.AppConfig;
import fr.chatop.api.dto.MessageDto;
import fr.chatop.api.exceptionhandler.EntityNotFoundException;
import fr.chatop.api.model.Message;
import fr.chatop.api.model.Rental;
import fr.chatop.api.model.User;
import fr.chatop.api.repository.MessageRepository;
import fr.chatop.api.repository.RentalRepository;
import fr.chatop.api.repository.UserRepository;
import fr.chatop.api.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
	@Autowired private MessageRepository messageRepository;
	@Autowired private RentalRepository rentalRepository;
	@Autowired private UserRepository userRepository;


	@Override
	public Message saveMessage(MessageDto message) {
		Optional<User> user = userRepository.findById(message.getUser_id());
		if (user.isPresent()) {
			Optional<Rental> rental = rentalRepository.findById(message.getRental_id());
			if (rental.isPresent()) {
				Message realMessage=AppConfig.modelMapper().map(message, Message.class);
				realMessage.setUser(user.get());
				realMessage.setRental(rental.get());
				return messageRepository.save(realMessage);
			} else {
				throw new EntityNotFoundException(Rental.class,"id",message.getRental_id().toString());
			}
		} else {
			throw new EntityNotFoundException(User.class,"id",message.getUser_id().toString());
		}
	}
}
