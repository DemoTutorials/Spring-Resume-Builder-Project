package com.avisys.microservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.avisys.microservice.proxy.EmailServiceProxy;

@Service
@Component
public class UploadFileService {

	@Autowired
	EmailServiceProxy fileServerProxy;

	public String uploadFile(MultipartFile file) {
		String uploadFile = fileServerProxy.uploadFile(file);
		return uploadFile.substring(uploadFile.indexOf("http"));
	}
}
