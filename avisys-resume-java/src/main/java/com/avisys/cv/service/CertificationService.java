package com.avisys.cv.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avisys.cv.dto.CertificationDto;
import com.avisys.cv.entity.Certification;
import com.avisys.cv.entity.Person;
import com.avisys.cv.repository.CertificationRepo;
import com.avisys.cv.repository.PersonRepo;

@Service
public class CertificationService {

	@Autowired
	private CertificationRepo CertificationRepository;

	@Autowired
	private PersonRepo personRepository;

	@Autowired
	private ModelMapper modelMapper;

	public List<CertificationDto> getAllCertifications(Long personId) {
		Person person = personRepository.findByPersonIdAndIsDeletedFalse(personId)
				.orElseThrow(() -> new PersistenceException("Person not found"));
		List<Certification> Certifications = CertificationRepository.findByPersonAndIsDeletedFalseOrderByLastUpdateDateAsc(person);
		List<CertificationDto> CertificationDto = Certifications.stream()
				.map((Certification) -> this.modelMapper.map(Certification, CertificationDto.class))
				.collect(Collectors.toList());
		return CertificationDto;
	}

	public CertificationDto update(CertificationDto CertificationDto, Long personId, String uid) {
		Person person = personRepository.findByPersonIdAndIsDeletedFalse(personId)
				.orElseThrow(() -> new PersistenceException("Person not found"));
		Certification existingCertification = CertificationRepository
				.findByCertIdAndIsDeletedFalse(CertificationDto.getCertId())
				.orElseThrow(() -> new PersistenceException("Certificate not found"));
		existingCertification.setCertificationName(CertificationDto.getCertificationName());
		existingCertification.setPerson(person);
		existingCertification.setLastUpdateDate(LocalDateTime.now());
		existingCertification.setLastUpdateBy(uid);
		Certification CertificationObj = CertificationRepository.save(existingCertification);
		return this.modelMapper.map(CertificationObj, CertificationDto.class);
	}

	public CertificationDto saveCertification(CertificationDto CertificationDto, Long personId, String uid) {
		Person person = personRepository.findByPersonIdAndIsDeletedFalse(personId)
				.orElseThrow(() -> new PersistenceException("Person not found"));
		Certification Certification = this.modelMapper.map(CertificationDto, Certification.class);
		Certification.setIsDeleted(false);
		Certification.setPerson(person);
		Certification.setCreatedBy(uid);
		Certification.setLastUpdateBy(uid);
		Certification.setCreationDate(LocalDateTime.now());
		Certification.setLastUpdateDate(LocalDateTime.now());
		Certification CertificationObj = CertificationRepository.save(Certification);
		return this.modelMapper.map(CertificationObj, CertificationDto.class);
	}

	public CertificationDto getByCertificationId(Long CertificationId) {
		Certification Certification = CertificationRepository.findByCertIdAndIsDeletedFalse(CertificationId)
				.orElseThrow(() -> new PersistenceException("Certificate not found"));
		return this.modelMapper.map(Certification, CertificationDto.class);
	}

	public CertificationDto softDelete(Long CertificationId, String uid) {
		Certification certification = CertificationRepository.findByCertIdAndIsDeletedFalse(CertificationId)
				.orElseThrow(() -> new PersistenceException("Certificate not found"));
		certification.setIsDeleted(true);
		certification.setLastUpdateBy(uid);
		certification.setLastUpdateDate(LocalDateTime.now());
		Certification CertificationObj = CertificationRepository.save(certification);
		return this.modelMapper.map(CertificationObj, CertificationDto.class);
	}

}
