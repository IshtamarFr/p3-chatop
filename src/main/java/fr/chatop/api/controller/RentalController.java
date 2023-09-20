package fr.chatop.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fr.chatop.api.model.Rental;
import fr.chatop.api.service.RentalService;

@RestController
@CrossOrigin(origins = "*")
public class RentalController {
	@Autowired
	private RentalService rentalService;

	@GetMapping("/rentals")
	public ResponseEntity<?> getAllRentals() {
		return ResponseEntity.ok().body(rentalService.getRentals());
	}

	@GetMapping("/rentals/{id}")
	public ResponseEntity<?> getRental(@PathVariable("id") final long id) {
		Optional<Rental> candidate = rentalService.getRental(id);
		if (candidate.isPresent()) {
			return ResponseEntity.ok().body(candidate);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
