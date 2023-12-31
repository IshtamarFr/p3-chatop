package fr.chatop.api.service.impl;

import fr.chatop.api.config.AppConfig;
import fr.chatop.api.dto.NewUserDto;
import fr.chatop.api.dto.UserDto;
import fr.chatop.api.exceptionhandler.EmailAlreadyUsedException;
import fr.chatop.api.exceptionhandler.EntityNotFoundException;
import fr.chatop.api.model.User;
import fr.chatop.api.repository.UserRepository;
import fr.chatop.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	@Autowired private UserRepository userRepository;

	@Override
	public UserDto getUser(final Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return AppConfig.modelMapper().map(user.get(), UserDto.class);
		} else {
			throw new EntityNotFoundException(User.class,"id",id.toString());
		}
	}

	@Override
	public User saveUser(NewUserDto user) {
		Optional<User> candidate = userRepository.findByEmail(user.getEmail());
		if (candidate.isPresent()) {
			throw new EmailAlreadyUsedException();
		} else {
			user.setPassword(AppConfig.passwordEncoder().encode(user.getPassword()));
			return userRepository.save(AppConfig.modelMapper().map(user,User.class));
		}
	}
}
