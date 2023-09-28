package fr.chatop.api.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class FileUploadResponse {
	@Schema(example="mylovelyhome")
	private String fileName;
	@Schema(example="/8792314.jpg")
	private String downloadUri;
	@Schema(example="173 kB")
	private long size;

}
