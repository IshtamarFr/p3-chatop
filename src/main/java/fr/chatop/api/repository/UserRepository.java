package fr.chatop.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.chatop.api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
