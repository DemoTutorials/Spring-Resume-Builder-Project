package com.avisys.cv.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.PersistenceException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.avisys.cv.dto.AuthRequest;
import com.avisys.cv.dto.AuthToken;
import com.avisys.cv.dto.DataTableDto;
import com.avisys.cv.dto.PersonDto;
import com.avisys.cv.dto.PersonProjection;
import com.avisys.cv.dto.SkillDto;
import com.avisys.cv.dto.UserDetailsDto;
import com.avisys.cv.dto.UserPersonDto;
import com.avisys.cv.dto.WorkExperienceDto;
import com.avisys.cv.entity.Person;
import com.avisys.cv.entity.Skill;
import com.avisys.cv.entity.User;
import com.avisys.cv.entity.WorkExperience;
import com.avisys.cv.proxy.CompanyConfig;
import com.avisys.cv.proxy.EndpointConfigService;
import com.avisys.cv.repository.PersonRepo;
import com.avisys.cv.repository.SkillRepo;
import com.avisys.cv.repository.WorkExperienceRepo;

@Service
public class PersonService {

	@Autowired
	private PersonRepo personRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private SkillRepo skillRepository;
	
	@Autowired
	private UploadFileService fileServerSvc;
	
	@Autowired
	private WorkExperienceRepo expRepository;
	
	@Autowired
	CompanyConfig companyDetails;
	
	@Autowired
	EndpointConfigService configService;
	
	@Value("${adminPassord}")
	private String password;
 
	@Value("${admin}")
	private String admin;

	public List<PersonDto> getAllPersons() {
		List<Person> personInfo= personRepository.findByIsDeletedFalse();
		List<PersonDto> personDto= personInfo.stream().map((person)-> this.modelMapper.map(person,PersonDto.class)).collect(Collectors.toList());
	    return personDto;
	}

	public PersonDto update(PersonDto personDto, String uid, MultipartFile file, MultipartFile resume) {
		Person existingperson = personRepository.findByPersonIdAndIsDeletedFalse(personDto.getPersonId())
				.orElseThrow(() -> new PersistenceException("Person not found"));
		existingperson.setFirstName(personDto.getFirstName());
		existingperson.setLastName(personDto.getLastName());
		existingperson.setSummary(personDto.getSummary());
		existingperson.setTotalExperience(personDto.getTotalExperience());
		existingperson.setCity(personDto.getCity());
		existingperson.setState(personDto.getState());
		existingperson.setCountry(personDto.getCountry());
		existingperson.setEmail(personDto.getEmail());
		existingperson.setPhone(personDto.getPhone());
		existingperson.setLinkedin(personDto.getLinkedin());
		existingperson.setLastUpdateBy(uid);
		existingperson.setLastUpdateDate(LocalDateTime.now());
		if (file != null) {
			String fileUploadLocation = fileServerSvc.uploadFile(file);
			existingperson.setProfilePic(fileUploadLocation);
		} else {
			existingperson.setProfilePic(null);
		}
		if (resume != null) {
			String fileUpload = fileServerSvc.uploadFile(resume);
			existingperson.setUploadFile(fileUpload);
		}
		this.personRepository.save(existingperson);
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		return this.modelMapper.map(existingperson, PersonDto.class);
	}

	public PersonDto createPerson(PersonDto personDto, String uid, MultipartFile file, MultipartFile resume) {
		Person person = this.modelMapper.map(personDto, Person.class);
		UserDetailsDto userDetails = companyDetails.getCompanyDetails(uid);
		person.setCompanyCode(userDetails.getCompanyCode());
		person.setCompanyName(userDetails.getCompanyName());
		person.setIsDeleted(false);
		person.setCreationDate(LocalDateTime.now());
		person.setLastUpdateDate(LocalDateTime.now());
		person.setCreatedBy(uid);
		person.setLastUpdateBy(uid);
		
		
		UserPersonDto personDetails = new UserPersonDto();
		personDetails.setFirstName(person.getFirstName());
		personDetails.setLastName(person.getLastName());
		personDetails.setCompanyName(person.getCompanyName());
		personDetails.setCompanyCode(person.getCompanyCode());
		personDetails.setEmail(person.getEmail());
		personDetails.setUserName(person.getEmail());
		personDetails.setUserType("USER");
		personDetails.setPassword("Default");
		
		AuthToken token = configService.authonticatAndGetToken(new AuthRequest(admin, password));
		User details = companyDetails.createUserByHR(personDetails,"Bearer "+ token.getAccessToken(),token.getUserId());
		
		person.setUser(details);
		
		if(personDto.getTotalExperience() == null) {
			person.setTotalExperience((float) 0);
		}
		if (file != null) {
			String fileUploadLocation = fileServerSvc.uploadFile(file);
			person.setProfilePic(fileUploadLocation);
		}
		if (resume != null) {
			String fileUpload = fileServerSvc.uploadFile(resume);
			person.setUploadFile(fileUpload);
		}
		Person save = personRepository.save(person);
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		return this.modelMapper.map(save, PersonDto.class);
	}

	public PersonDto getByPersonId(Long personId) {
        Person person = personRepository.findByPersonIdAndIsDeletedFalse(personId)
                .orElseThrow(() -> new PersistenceException("Person not found"));
       modelMapper.getConfiguration().setAmbiguityIgnored(true);
       return this.modelMapper.map(person, PersonDto.class);
    }

	public PersonDto softDelete(Long personId, String uid) {
		Person person = personRepository.findByPersonIdAndIsDeletedFalse(personId)
				.orElseThrow(() -> new PersistenceException("Person not found"));
		person.setIsDeleted(true);
		person.setLastUpdateDate(LocalDateTime.now());
		person.setLastUpdateBy(uid);
		Person personObj = personRepository.save(person);
		return this.modelMapper.map(personObj, PersonDto.class);
	}
	
	public Page<DataTableDto> searchPerson(Pageable pageable, String keyword, String uid) {
	    keyword = keyword.toLowerCase();
	    Page<Person> personPage;
	    UserDetailsDto userDetails = companyDetails.getCompanyDetails(uid);
	    String companyCode = userDetails.getCompanyCode();
	    if (userDetails.getUserType().equals("ADMIN")) {
	        personPage = personRepository.searchPersonByAdmin(keyword, pageable);
	    } else {
	        personPage = personRepository.searchPersonByHRCode(keyword, companyCode, pageable);
	    }
	    Page<DataTableDto> datatableDto = personPage.map(person -> {
	        person.setSkillsList(person.getSkillsList().stream().filter(skill -> !skill.getIsDeleted()).collect(Collectors.toList()));

	        if (person.getOtherSkillsList() != null && person.getOtherSkillsList().getIsDeleted()) {
	            person.setOtherSkillsList(null);
	        }

	        List<Skill> skills = skillRepository.SkillDataByPerson(person);
	        List<SkillDto> skillDto = skills.stream().map(skill -> modelMapper.map(skill, SkillDto.class)).collect(Collectors.toList());

	        List<WorkExperience> experiences = expRepository.ExperiencDataByPerson(person);
	        List<WorkExperienceDto> experienceDto = experiences.stream()
	                .map(experience -> modelMapper.map(experience, WorkExperienceDto.class))
	                .collect(Collectors.toList());

	        DataTableDto dataTableDto = modelMapper.map(person, DataTableDto.class);
	        dataTableDto.setSkillsList(skillDto);
	        dataTableDto.setExperienceList(experienceDto);

	        return dataTableDto;
	    });

	    return datatableDto;
	}

	public PersonDto getPersonByUserId(Long userId, String userRole) {
	    if (userId != null && "USER".equals(userRole)) {
	        Person person = personRepository.findByUserId(userId);
	        if (person != null) {
	        	modelMapper.getConfiguration().setAmbiguityIgnored(true);
	            return this.modelMapper.map(person, PersonDto.class);
	        } else {
	            throw new PersistenceException("Person not found for userId: " + userId);
	        }
	    } else {
	        throw new IllegalArgumentException("Invalid userId or userRole");
	    }
	}

	public Page<DataTableDto> searchPersonOnDashboard(Pageable pageable, Float minExperience, Float maxExperience, String skill, String domain, String companyName, String jobProfile, String userRole, String uid) {
	    UserDetailsDto userDetails = companyDetails.getCompanyDetails(uid);
	    String companyCode = userDetails.getCompanyCode();
	    List<String> skillNames = List.of();
	    if (skill != null) {
	        skillNames = Arrays.asList(skill.split(","))
	                           .stream()
	                           .map(String::toLowerCase)
	                           .collect(Collectors.toList());
	    }
	    
	    String domains = (domain != null && !domain.trim().isEmpty()) ? domain.toLowerCase() : "";
	    String company = (companyName != null && !companyName.trim().isEmpty()) ? companyName.toLowerCase() : "";
	    String jobProfiles = (jobProfile != null && !jobProfile.trim().isEmpty()) ? jobProfile.toLowerCase() : "";
	    
	    Page<PersonProjection> personData;
	    if(userRole.equals("ADMIN")) {
			personData = personRepository.searchByAdmin(pageable, minExperience, maxExperience, skillNames, domains, company);
	    } else {
	        personData = personRepository.searchByHR(pageable, minExperience, maxExperience, skillNames, domains, companyCode, jobProfiles);
	    }

	    Page<Person> personPage = personData.map(personDto -> modelMapper.map(personDto, Person.class));

	    Page<DataTableDto> datatableDto = personPage.map(person -> {
	        if (person.getSkillsList() != null) {
	            person.setSkillsList(person.getSkillsList().stream().filter(skills -> !skills.getIsDeleted()).collect(Collectors.toList()));
	        }

	        if (person.getOtherSkillsList() != null && person.getOtherSkillsList().getIsDeleted()) {
	            person.setOtherSkillsList(null);
	        }

	        List<Skill> skills = skillRepository.SkillDataByPerson(person);
	        List<SkillDto> skillDto = skills.stream().map(skillName -> modelMapper.map(skillName, SkillDto.class)).collect(Collectors.toList());

	        List<WorkExperience> experiences = expRepository.ExperiencDataByPerson(person);
	        List<WorkExperienceDto> experienceDto = experiences.stream()
	                .map(experience -> modelMapper.map(experience, WorkExperienceDto.class))
	                .collect(Collectors.toList());

	        DataTableDto dataTableDto = modelMapper.map(person, DataTableDto.class);
	        dataTableDto.setSkillsList(skillDto);
	        dataTableDto.setExperienceList(experienceDto);

	        return dataTableDto;
	    });
	    return datatableDto;
	}

	
	public PersonDto createPersonByHR(UserDetailsDto userDto) {
		Person person = this.modelMapper.map(userDto, Person.class);

		person.setIsDeleted(false);
		person.setCreationDate(LocalDateTime.now());
		person.setLastUpdateDate(LocalDateTime.now());
		person.setCreatedBy(userDto.getUserId());
		person.setLastUpdateBy(userDto.getUserId());
		person.setTotalExperience((float) 0);
		Person save = personRepository.save(person);
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		return this.modelMapper.map(save, PersonDto.class);
	}

}
