package fr.chatop.api.controller;

import fr.chatop.api.config.util.JwtTokenUtil;
import fr.chatop.api.controller.dto.RentalDto;
import fr.chatop.api.controller.exceptionhandler.OwnerMismatchException;
import fr.chatop.api.model.Rental;
import fr.chatop.api.service.RentalService;
import fr.chatop.api.service.RentalServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
	@Operation(responses={
			@ApiResponse(responseCode = "200", description="Retrieved all rentals' data"),
			@ApiResponse(responseCode = "401", description="Bad credentials")
	})
	@GetMapping("/rentals")
	public ResponseEntity<HashMap<String, List<RentalDto>>> getAllRentals() {
		HashMap<String, List<RentalDto>> map = new HashMap<>();
		map.put("rentals", rentalService.getRentals());
		return ResponseEntity.ok().body(map);
	}

	@ApiOperation("Shows rental with correct Id")
	@Operation(responses={
			@ApiResponse(responseCode = "200", description="Retrieved specific rental's data"),
			@ApiResponse(responseCode = "401", description="Bad credentials")
	})
	@GetMapping("/rentals/{id}")
	public ResponseEntity<RentalDto> getRental(@PathVariable("id") final long id) {
		return ResponseEntity.ok().body(rentalService.getRental(id));
	}

	@ApiOperation("Create a new rental for user - Picture being uploaded and kinda obfuscated")
	@Operation(responses={
			@ApiResponse(responseCode = "200", description="Successfully created new rental"),
			@ApiResponse(responseCode = "401", description="Bad credentials")
	})
	@PostMapping("/rentals")
	public ResponseEntity<RentalDto> createRental(
	//@formatter:off
		@RequestParam("picture") MultipartFile multipartFile,
		@RequestParam("name") String name,
		@RequestParam("surface") float surface,
		@RequestParam("price") float price,
		@RequestParam("description") String description,
		@RequestHeader("Authorization") String jwt
	//@formatter:on
	) throws Exception {
		Long ownerId = jwtUtil.getIdFromValidToken(jwt);
		Rental candidate = new Rental();
		candidate.setName(name);
		candidate.setSurface(surface);
		candidate.setPrice(price);
		candidate.setDescription(description);
		candidate.setOwner_id(ownerId);
		candidate.setPicture(rentalService.savePicture(multipartFile));
		return ResponseEntity.status(200).body(rentalService.saveRental(candidate));
	}

	@ApiOperation("Changes rental's details - picture cannot be changed")
	@Operation(responses={
			@ApiResponse(responseCode = "200", description="Successfully modified rental"),
			@ApiResponse(responseCode = "401", description="Bad credentials"),
	})
	@PutMapping("/rentals/{id}")
	//@formatter:off
	/*
	 * IMPORTANT :
	 * 1- this PutMapping gets PathVariable for Rental's id
	 * 2- Owner's id must be gotten from Jwt and checked against expected owner's id
	 * 3- This method doesn't allow to change picture (not a requested feature)
	 */
	//@formatter: on
	public ResponseEntity<RentalDto> modifyRental(
	//@formatter: off
		@RequestParam("name") String name,
		@RequestParam("surface") float surface,
		@RequestParam("price") float price,
		@RequestParam("description") String description,
		@PathVariable("id") long id,
		@RequestHeader("Authorization") String jwt
	//@formatter: on
	) throws Exception {
		//we first try to check get the owner's id from jwt owner (already validated)
		long ownerId=jwtUtil.getIdFromValidToken(jwt);
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
			return ResponseEntity.ok().body(rentalService.saveRental(modification));
		} else {
			throw new OwnerMismatchException();
		}
	}
}
