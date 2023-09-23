package fr.chatop.api.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import fr.chatop.api.config.FileUploadUtil;
import fr.chatop.api.model.Rental;
import fr.chatop.api.repository.RentalRepository;

@Service
public class RentalService {
	@Autowired
	private RentalRepository rentalRepository;

	public List<Rental> getRentals() {
		return rentalRepository.findAll();
	}

	public Optional<Rental> getRental(final long id) {
		return rentalRepository.findById(id);
	}

	public Rental saveRental(Rental rental) {
		return rentalRepository.save(rental);
	}

	public String savePicture(MultipartFile multipartFile) throws IOException {
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		String filecode = FileUploadUtil.saveFile(fileName, multipartFile);
		return ("/Files-Upload/" + filecode);
	}
}
