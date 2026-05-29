package com.auth.uam.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.auth.uam.dto.PersonDto;

@FeignClient(name = "JAVA-CV")
public interface ResumeService {

	@PostMapping(path = "/cv/person/resume-triggere")
	public PersonDto save(@RequestBody PersonDto person);

//	@PostMapping(value = "/cv/person/save", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
//	public PersonDto create(@RequestPart PersonDto person, @Nullable @RequestPart MultipartFile file,@RequestHeader("Authorization") String token);

}