package com.avisys.cv.service;

import java.time.LocalDateTime;

import javax.persistence.PersistenceException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.avisys.cv.dto.CompanyDetailsDto;
import com.avisys.cv.dto.UserDetailsDto;
import com.avisys.cv.entity.CompanyDetails;
import com.avisys.cv.proxy.CompanyConfig;
import com.avisys.cv.repository.CompanyDetailsRepo;

@Service
public class CompanyDetailService {
	
	@Autowired
	CompanyDetailsRepo companyRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UploadFileService fileServerSvc;
	
	@Autowired
	CompanyConfig companyDetails;
	
	public CompanyDetailsDto updateCompanyDetails(CompanyDetailsDto details, String uid, MultipartFile logo, MultipartFile watermark) {
		CompanyDetails company = companyRepo.findByCompanyIdAndIsDeletedFalse(details.getCompanyId())
				.orElseThrow(() -> new PersistenceException("Company Details not found"));
		company.setShowContact(details.isShowContact());
		company.setPartialName(details.isPartialName());
		company.setWaterMark(details.getWaterMark());
		company.setCompanyLogo(details.getCompanyLogo());
		company.setFooter(details.getFooter());
		company.setShowEducation(details.isShowEducation());
		company.setShowProfilePicture(details.isShowProfilePicture());
		company.setShowCompanyName(details.isShowCompanyName());
		company.setLastUpdateBy(uid);
		company.setLastUpdateDate(LocalDateTime.now());
		if (logo != null) {
			String logoUploaded = fileServerSvc.uploadFile(logo);
			company.setCompanyLogo(logoUploaded);
		}
		if (watermark != null) {
			String watermarkUploaded = fileServerSvc.uploadFile(watermark);
			company.setWaterMark(watermarkUploaded);
		}
		CompanyDetails detailObj = companyRepo.save(company);
		return this.modelMapper.map(detailObj, CompanyDetailsDto.class);
	}

	public CompanyDetailsDto createCompanyDetails(CompanyDetailsDto detailsDto, String uid, MultipartFile logo, MultipartFile watermark) {
		CompanyDetails details = this.modelMapper.map(detailsDto, CompanyDetails.class);
		UserDetailsDto userDetails = companyDetails.getCompanyDetails(uid);
		CompanyDetails comapnyDetails = companyRepo.findByCompanyCodeAndIsDeletedFalse(userDetails.getCompanyCode());
		if(comapnyDetails == null) {
		details.setCompanyName(userDetails.getCompanyName());
		details.setCompanyCode(userDetails.getCompanyCode());
		details.setIsDeleted(false);
		details.setCreationDate(LocalDateTime.now());
		details.setLastUpdateDate(LocalDateTime.now());
		details.setCreatedBy(uid);
		details.setLastUpdateBy(uid);
		
		if (logo != null) {
			String logoUploaded = fileServerSvc.uploadFile(logo);
			details.setCompanyLogo(logoUploaded);
		}
		if (watermark != null) {
			String watermarkUploaded = fileServerSvc.uploadFile(watermark);
			details.setWaterMark(watermarkUploaded);
		}
		CompanyDetails detailsObj = companyRepo.save(details);
		return this.modelMapper.map(detailsObj, CompanyDetailsDto.class);
		} else {
			throw new PersistenceException("Company details already exist");
		}
	}

	public CompanyDetailsDto getByCompanyCode(String companyCode) {
		CompanyDetails companyDetails = companyRepo.findByCompanyCodeAndIsDeletedFalse(companyCode);
		if(companyDetails != null) {
			return this.modelMapper.map(companyDetails, CompanyDetailsDto.class);
		} else {
			throw new PersistenceException("Company details not found for given CompanyCode");
		}
		
	}
}
