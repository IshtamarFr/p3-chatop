package fr.chatop.api.service;

import fr.chatop.api.model.Rental;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface RentalService {
    List<Rental> getRentals();
    Optional<Rental> getRental(final long id);
    Rental saveRental(Rental rental);
    String savePicture(MultipartFile multipartFile) throws IOException;
}
