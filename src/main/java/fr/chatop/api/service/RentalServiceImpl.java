package fr.chatop.api.service;

import fr.chatop.api.config.AppConfig;
import fr.chatop.api.config.util.FileUploadUtil;
import fr.chatop.api.config.util.ObjectMapperUtils;
import fr.chatop.api.controller.dto.RentalDto;
import fr.chatop.api.model.Rental;
import fr.chatop.api.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class RentalServiceImpl implements RentalService {
	@Autowired private RentalRepository rentalRepository;
	@Autowired private AppConfig appConfig;

	@Override
	public List<RentalDto> getRentals() {
		List<Rental> listOfRentals=rentalRepository.findAll();
		return ObjectMapperUtils.mapAll(listOfRentals,RentalDto.class);
	}

	@Override
	public Optional<RentalDto> getRental(final Long id) {
		Optional<Rental> rental=rentalRepository.findById(id);
		if (rental.isPresent()) {
			RentalDto rentalDto=appConfig.modelMapper().map(rental.get(),RentalDto.class);
			return Optional.of(rentalDto);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Rental saveRental(Rental rental) {
		return rentalRepository.save(rental);
	}

	@Override
	public String savePicture(MultipartFile multipartFile) throws IOException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		String filecode = FileUploadUtil.saveFile(fileName, multipartFile);
		return ("/Files-Upload/" + filecode);
	}
}
