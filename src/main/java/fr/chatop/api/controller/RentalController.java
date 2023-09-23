package fr.chatop.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.chatop.api.config.JwtTokenUtil;
import fr.chatop.api.model.Rental;
import fr.chatop.api.service.RentalService;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "*")
public class RentalController {
	@Autowired
	private RentalService rentalService;

	@Autowired
	private JwtTokenUtil jwtUtil;

	@ApiOperation("Lists all rentals")
	@GetMapping("/rentals")
	public ResponseEntity<?> getAllRentals() {
		HashMap<String, List<Rental>> map = new HashMap<>();
		map.put("rentals", rentalService.getRentals());
		return ResponseEntity.ok().body(map);
	}

	@ApiOperation("Shows rental with correct Id")
	@GetMapping("/rentals/{id}")
	public ResponseEntity<?> getRental(@PathVariable("id") final long id) {
		Optional<Rental> candidate = rentalService.getRental(id);
		if (candidate.isPresent()) {
			return ResponseEntity.ok().body(candidate);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation("Create a new rental for user - Picture being uploaded and kinda obfuscated")
	@PostMapping("/rentals/{owner_id}")
	public ResponseEntity<?> createRental(
	//@formatter:off
		@RequestParam("picture") MultipartFile multipartFile,
		@RequestParam("name") String name,
		@RequestParam("surface") float surface,
		@RequestParam("price") float price,
		@RequestParam("description") String description,
		@PathVariable("owner_id") long ownerId
	//@formatter:on
	) {
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

	@ApiOperation("Changes rental's details - picture cannot be changed")
	@PutMapping("/rentals/{id}")
	//@formatter:off
	/*
	 * IMPORTANT :
	 * 1- this PutMapping gets PathVariable for Rental's id
	 * 2- Owner's id must be gotten from Jwt and checked against expected owner's id
	 * 3- This method doesn't allow to change picture (not a requested feature)
	 */
	//@formatter: on
	public ResponseEntity<?> modifyRental(
	//@formatter: off
		@RequestParam("name") String name,
		@RequestParam("surface") float surface,
		@RequestParam("price") float price,
		@RequestParam("description") String description,
		@PathVariable("id") long id,
		@RequestHeader("Authorization") String jwt
	//@formatter: on
	) {
		try {
			//we first try to check get the owner's id from jwt owner (already validated)
			long ownerId=jwtUtil.getIdFromValidToken(jwt);
			Optional<Rental> candidate = rentalService.getRental(id);
			if (candidate.isPresent()) {
				//we now check if owner's id matches with rental's owner's id
				//plus we need data from old rental to make new rental
				Rental realCandidate=candidate.get();
				if (ownerId==realCandidate.getOwnerId()) {
					//token's owner matches with rental's owner's id
					Rental modification=new Rental();
					modification.setId(id);
					modification.setName(name);
					modification.setSurface(surface);
					modification.setPrice(price);
					modification.setPicture(realCandidate.getPicture());
					modification.setDescription(description);
					modification.setOwnerId(ownerId);
					modification.setCreated_at(realCandidate.getCreated_at());
					rentalService.saveRental(modification);
					return ResponseEntity.ok().body(modification);
				} else {
					return ResponseEntity.notFound().build();
				}
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return ResponseEntity.status(409).body(e);
		}
	}
}
