package fr.chatop.api.service;

import fr.chatop.api.controller.dto.UserDto;
import fr.chatop.api.model.User;

public interface UserService {
    User saveUser(User user);
    UserDto getUser(final Long id);
}
