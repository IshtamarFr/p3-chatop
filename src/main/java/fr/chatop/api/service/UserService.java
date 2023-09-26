package fr.chatop.api.service;

import fr.chatop.api.controller.dto.UserDto;
import fr.chatop.api.model.User;

import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<UserDto> getUser(final long id);
}
