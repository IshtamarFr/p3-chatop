package fr.chatop.api.service.impl;

import fr.chatop.api.config.AppConfig;
import fr.chatop.api.service.RentalService;
import fr.chatop.api.util.FileUploadUtil;
import fr.chatop.api.util.ObjectMapperUtils;
import fr.chatop.api.dto.RentalDto;
import fr.chatop.api.exceptionhandler.EntityNotFoundException;
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

	@Override
	public List<RentalDto> getRentals() {
		List<Rental> listOfRentals=rentalRepository.findAll();
		return ObjectMapperUtils.mapAll(listOfRentals,RentalDto.class);
	}

	@Override
	public RentalDto getRental(final Long id) {
		Optional<Rental> rental=rentalRepository.findById(id);
		if (rental.isPresent()) {
			return AppConfig.modelMapper().map(rental.get(),RentalDto.class);
		} else {
			throw new EntityNotFoundException(Rental.class,"id",id.toString());
		}
	}

	@Override
	public RentalDto saveRental(Rental rental) {
		return appConfig.modelMapper().map(rentalRepository.save(rental),RentalDto.class);
	}

	@Override
	public String savePicture(MultipartFile multipartFile) throws IOException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		String filecode = FileUploadUtil.saveFile(fileName, multipartFile);
		return ("/Files-Upload/" + filecode);
	}
}
