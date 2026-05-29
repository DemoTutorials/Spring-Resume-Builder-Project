package com.avisys.cv.service;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import com.avisys.cv.proxy.FileServerProxy;
import com.avisys.cv.web.MultipartFileCustom;

@Service
public class UploadFileService {

	private static final Logger logger = LoggerFactory.getLogger(UploadFileService.class);

	@Value("${cv.file-server.url}")
	private String fileServerUrl;

	@Autowired
	private FileServerProxy fileServerProxy;
	/** max size of 2Mb */
	private static final long MAX_FILE_SIZE_IN_BYTES = 2000000L;

	/**
	 * Uploads the provided MultipartFile on the file server.
	 * 
	 * @param file file data which we want to save
	 * @return location of saved file on file server
	 */
	public String uploadFile(MultipartFile file) {
		if (file.getSize() > MAX_FILE_SIZE_IN_BYTES) {
			throw new MaxUploadSizeExceededException(MAX_FILE_SIZE_IN_BYTES);
		}
		HttpHeaders httpHeader = new HttpHeaders();
		httpHeader.setContentType(MediaType.MULTIPART_FORM_DATA);

		MultipartFile multipartFileToSave;
		try {
			multipartFileToSave = new MultipartFileCustom(getUniqueFileName(file.getOriginalFilename()),
					file.getInputStream());
		} catch (IOException e) {
			// If there is an exception then we should not stop but upload the file with
			// original name and log it so that it can be debugged later.
			logger.warn("There was an error creating file with unique name so using original name", e);
			multipartFileToSave = file;
		}
		MultiValueMap<String, Object> multiValMap = new LinkedMultiValueMap<>();
		multiValMap.add("file", multipartFileToSave.getResource());

		String responseBody = fileServerProxy.uploadFile(multipartFileToSave);

		return responseBody.substring(responseBody.indexOf("http"), responseBody.length());
	}

	/**
	 * Two uploaded file can have same file name and extension. This will make sure
	 * even if the file name and extension is same we get a unique file name. The
	 * logic user here is first 5 character or number of character we have before
	 * extension of the original filename is appended by UUID and then the
	 * extension.
	 * 
	 * This logic is added so that if the user upload two different files with same
	 * name and extension then on the file server does not overwrite the previous
	 * file.
	 * 
	 * @param originalFileName the original file name
	 * @return unique file name
	 */
	private String getUniqueFileName(String originalFileName) {
		if (originalFileName == null) {
			// For some reason if don't have a file name then use this dummy name
			originalFileName = "sysCreated";
		}
		int startIndexFileExtension = originalFileName.lastIndexOf(".");
		String nameFirst5CharOrLess = originalFileName.substring(0,
				startIndexFileExtension < 5 ? startIndexFileExtension : 5);
		StringBuilder fileNameBuilder = new StringBuilder(60);
		fileNameBuilder.append(nameFirst5CharOrLess).append(UUID.randomUUID().toString().replace("-", ""))
				.append(originalFileName.substring(startIndexFileExtension));
		return fileNameBuilder.toString();
	}
}
