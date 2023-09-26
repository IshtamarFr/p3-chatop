package fr.chatop.api.controller;

import fr.chatop.api.config.util.JwtTokenUtil;
import fr.chatop.api.controller.dto.RentalDto;
import fr.chatop.api.model.Rental;
import fr.chatop.api.service.RentalServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class RentalController {
	@Autowired private RentalServiceImpl rentalServiceImpl;

	@Autowired private JwtTokenUtil jwtUtil;

	@ApiOperation("Lists all rentals")
	@GetMapping("/rentals")
	public ResponseEntity<?> getAllRentals() {
		HashMap<String, List<RentalDto>> map = new HashMap<>();
		map.put("rentals", rentalServiceImpl.getRentals());
		return ResponseEntity.ok().body(map);
	}

	@ApiOperation("Shows rental with correct Id")
	@GetMapping("/rentals/{id}")
	public ResponseEntity<?> getRental(@PathVariable("id") final long id) {
		Optional<RentalDto> candidate = rentalServiceImpl.getRental(id);
		if (candidate.isPresent()) {
			return ResponseEntity.ok().body(candidate);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@ApiOperation("Create a new rental for user - Picture being uploaded and kinda obfuscated")
	@PostMapping("/rentals")
	public ResponseEntity<?> createRental(
	//@formatter:off
		@RequestParam("picture") MultipartFile multipartFile,
		@RequestParam("name") String name,
		@RequestParam("surface") float surface,
		@RequestParam("price") float price,
		@RequestParam("description") String description,
		@RequestHeader("Authorization") String jwt
	//@formatter:on
	) {
		try {
			long ownerId = jwtUtil.getIdFromValidToken(jwt);
			Rental candidate = new Rental();
			candidate.setName(name);
			candidate.setSurface(surface);
			candidate.setPrice(price);
			candidate.setPicture(rentalServiceImpl.savePicture(multipartFile));
			candidate.setDescription(description);
			candidate.setOwner_id(ownerId);
			rentalServiceImpl.saveRental(candidate);
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
			Optional<RentalDto> candidate = rentalServiceImpl.getRental(id);
			if (candidate.isPresent()) {
				//we now check if owner's id matches with rental's owner's id
				//plus we need data from old rental to make new rental
				RentalDto realCandidate=candidate.get();
				if (ownerId==realCandidate.getOwner_id()) {
					//token's owner matches with rental's owner's id
					Rental modification=new Rental();
					modification.setId(id);
					modification.setName(name);
					modification.setSurface(surface);
					modification.setPrice(price);
					modification.setPicture(realCandidate.getPicture());
					modification.setDescription(description);
					modification.setOwner_id(ownerId);
					modification.setCreated_at(realCandidate.getCreated_at());
					rentalServiceImpl.saveRental(modification);
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
