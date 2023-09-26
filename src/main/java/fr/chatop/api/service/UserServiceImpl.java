package fr.chatop.api.service;

import fr.chatop.api.config.AppConfig;
import fr.chatop.api.controller.dto.UserDto;
import fr.chatop.api.model.User;
import fr.chatop.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
	@Autowired private UserRepository userRepository;
	@Autowired private AppConfig appConfig;

	@Override
	public Optional<UserDto> getUser(final long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			UserDto userDto=appConfig.modelMapper().map(user.get(), UserDto.class);
			return Optional.of(userDto);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public User saveUser(User user) {
		user.setPassword(appConfig.passwordEncoder().encode(user.getPassword()));
		return userRepository.save(user);
	}
}
