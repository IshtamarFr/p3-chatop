package fr.chatop.api.service;

import fr.chatop.api.dto.NewUserDto;
import fr.chatop.api.dto.UserDto;
import fr.chatop.api.model.User;

public interface UserService {
    User saveUser(NewUserDto user);
    UserDto getUser(final Long id);
}
