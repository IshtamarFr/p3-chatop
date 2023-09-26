package fr.chatop.api.service;

import java.util.List;
import java.util.Optional;

import fr.chatop.api.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.chatop.api.model.User;
import fr.chatop.api.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AppConfig appConfig;

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUser(final long id) {
		return userRepository.findById(id);
	}

	public User saveUser(User user) {
		user.setPassword(appConfig.passwordEncoder().encode(user.getPassword()));
		return userRepository.save(user);
	}
}
