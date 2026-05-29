package com.auth.uam.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.auth.uam.dto.MailNotification;

@FeignClient(name = "EMAIL-SERVICE" )
public interface EmailProxyService {

	@PostMapping(value = "/email/mail/create/{sendMail}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public MailNotification create(@RequestParam String maildto, @Nullable @RequestPart List<MultipartFile> file,
			@PathVariable("sendMail") String sendMail);
}
