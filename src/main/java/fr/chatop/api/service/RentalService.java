package fr.chatop.api.service;

import fr.chatop.api.controller.dto.RentalDto;
import fr.chatop.api.model.Rental;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RentalService {
    List<RentalDto> getRentals();
    RentalDto getRental(final Long id);
    RentalDto saveRental(Rental rental);
    String savePicture(MultipartFile multipartFile) throws IOException;
}
