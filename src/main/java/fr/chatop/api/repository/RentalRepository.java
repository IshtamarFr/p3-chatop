package fr.chatop.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.chatop.api.model.Rental;

public interface RentalRepository extends JpaRepository<Rental, Long> {

}
