package com.auth.uam.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.auth.uam.dto.AuthRequest;
import com.auth.uam.dto.AuthToken;
import com.auth.uam.dto.PersonDto;

@FeignClient(name = "AUTH-SERVICE")
public interface ProxyService {

	@PostMapping("/auth-service/auth/accessToken")
	public AuthToken authonticatAndGetToken(@RequestBody AuthRequest authRequest);
	
}
