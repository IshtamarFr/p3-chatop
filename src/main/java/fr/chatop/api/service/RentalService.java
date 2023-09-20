package fr.chatop.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.chatop.api.model.Rental;
import fr.chatop.api.repository.RentalRepository;

@Service
public class RentalService {
	@Autowired
	private RentalRepository rentalRepository;

	public List<Rental> getRentals() {
		return rentalRepository.findAll();
	}

	public Optional<Rental> getRentals(final long id) {
		return rentalRepository.findById(id);
	}

	public Rental saveRental(Rental rental) {
		return rentalRepository.save(rental);
	}
}
