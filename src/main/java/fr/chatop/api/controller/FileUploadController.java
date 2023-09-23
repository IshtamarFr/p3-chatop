package fr.chatop.api.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fr.chatop.api.config.FileUploadUtil;
import fr.chatop.api.model.FileUploadResponse;
import io.swagger.annotations.ApiOperation;

@RestController
public class FileUploadController {

	@ApiOperation("[Test-Only] Saves a file to server and kinda obfuscates it")
	@PostMapping("/uploadFile")
	public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile multipartFile)
			throws IOException {

		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		long size = multipartFile.getSize();

		String filecode = FileUploadUtil.saveFile(fileName, multipartFile);

		FileUploadResponse response = new FileUploadResponse();
		response.setFileName(fileName);
		response.setSize(size);
		response.setDownloadUri("/Files-Upload/" + filecode);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
