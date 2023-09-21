package fr.chatop.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

	@PostMapping("/rentals/{owner_id}")
	public ResponseEntity<?> createRental(@RequestParam("picture") MultipartFile multipartFile,
			@RequestParam("name") String name, @RequestParam("surface") float surface,
			@RequestParam("price") float price, @RequestParam("description") String description,
			@PathVariable("owner_id") long ownerId) {
		try {
			Rental candidate = new Rental();
			candidate.setName(name);
			candidate.setSurface(surface);
			candidate.setPrice(price);
			candidate.setPicture(rentalService.savePicture(multipartFile));
			candidate.setDescription(description);
			candidate.setOwnerId(ownerId);
			rentalService.saveRental(candidate);
			return ResponseEntity.ok().body(candidate);
		} catch (Exception e) {
			return ResponseEntity.status(409).body(e);
		}
	}
}
