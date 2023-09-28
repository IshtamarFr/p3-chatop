package fr.chatop.api.controller;

import fr.chatop.api.config.util.JwtTokenUtil;
import fr.chatop.api.controller.dto.RentalDto;
import fr.chatop.api.model.Rental;
import fr.chatop.api.service.RentalService;
import fr.chatop.api.service.RentalServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class RentalController {
	@Autowired private RentalService rentalService= new RentalServiceImpl();

	@Autowired private JwtTokenUtil jwtUtil;

	@ApiOperation("Lists all rentals")
	@GetMapping("/rentals")
	public HashMap<String, List<RentalDto>> getAllRentals() {
		HashMap<String, List<RentalDto>> map = new HashMap<>();
		map.put("rentals", rentalService.getRentals());
		return map;
	}

	@ApiOperation("Shows rental with correct Id")
	@GetMapping("/rentals/{id}")
	public RentalDto getRental(@PathVariable("id") final long id) {
		return rentalService.getRental(id);
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
			Long ownerId = jwtUtil.getIdFromValidToken(jwt);
			Rental candidate = new Rental();
			candidate.setName(name);
			candidate.setSurface(surface);
			candidate.setPrice(price);
			candidate.setPicture(rentalService.savePicture(multipartFile));
			candidate.setDescription(description);
			candidate.setOwner_id(ownerId);
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
		//we first try to check get the owner's id from jwt owner (already validated)
		Long ownerId=jwtUtil.getIdFromValidToken(jwt);
		RentalDto candidate = rentalService.getRental(id);
		if (ownerId==candidate.getOwner_id()) {
			//token's owner matches with rental's owner's id
			Rental modification=new Rental();
			modification.setId(id);
			modification.setName(name);
			modification.setSurface(surface);
			modification.setPrice(price);
			modification.setPicture(candidate.getPicture());
			modification.setDescription(description);
			modification.setOwner_id(ownerId);
			modification.setCreated_at(candidate.getCreated_at());
			rentalService.saveRental(modification);
			return ResponseEntity.ok().body(modification);
		} else {
			//token's owner doesnt match with rental's owner id
			return ResponseEntity.notFound().build();
		}
	}
}
