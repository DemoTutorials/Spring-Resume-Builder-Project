package com.auth.uam.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.auth.uam.dto.CommonMaster;

@FeignClient(name = "UTILITY")
public interface CommonMasterProxy {

	@GetMapping(value = "/utility/masters/commonMaster/{parent}")
	public List<CommonMaster> getCommonMasterData(@PathVariable String parent);
}
