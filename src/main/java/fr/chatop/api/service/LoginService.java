package fr.chatop.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import fr.chatop.api.model.Login;
import fr.chatop.api.model.User;

@Service
public class LoginService {
	@Autowired
	private UserService userService;

	public int verifyUser(final Login login) {
		List<User> listUsers = this.userService.getUsers();
		for (User user : listUsers) {
			if (user.getUsername().equals(login.getUsername())) {
				// login username has been found in list of users
				if (BCrypt.checkpw(login.getPassword(), user.getPassword())) {
					// (login,password) is OK
					return 1;
				} else {
					// login password doesn't match user's password
					return 0;
				}
			} else {
				// login username is not found
				return -1;
			}
		}
		// fallback if list is empty
		return -2;
	}
}
