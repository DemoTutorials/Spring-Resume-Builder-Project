package com.avisys.cv.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.persistence.PersistenceException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.avisys.cv.dto.CertificationDto;
import com.avisys.cv.dto.CompanyDetailsDto;
import com.avisys.cv.dto.EducationDto;
import com.avisys.cv.dto.LanguageDto;
import com.avisys.cv.dto.OtherSkillDto;
import com.avisys.cv.dto.PersonDto;
import com.avisys.cv.dto.ResumeTemplateDto;
import com.avisys.cv.dto.SkillDto;
import com.avisys.cv.dto.WorkExperienceDto;
import com.avisys.cv.entity.Certification;
import com.avisys.cv.entity.CompanyDetails;
import com.avisys.cv.entity.Education;
import com.avisys.cv.entity.Language;
import com.avisys.cv.entity.OtherSkills;
import com.avisys.cv.entity.Person;
import com.avisys.cv.entity.Skill;
import com.avisys.cv.entity.WorkExperience;
import com.avisys.cv.repository.CertificationRepo;
import com.avisys.cv.repository.CompanyDetailsRepo;
import com.avisys.cv.repository.EducationRepo;
import com.avisys.cv.repository.LanguageRepo;
import com.avisys.cv.repository.OtherSkillsRepo;
import com.avisys.cv.repository.PersonRepo;
import com.avisys.cv.repository.SkillRepo;
import com.avisys.cv.repository.WorkExperienceRepo;
import com.avisys.cv.utils.ImageUtil;
import com.lowagie.text.DocumentException;

@Service
public class ResumeService {

	@Autowired
	PersonRepo personRepository;

	@Autowired
	EducationRepo educationRepository;

	@Autowired
	LanguageRepo languageRepository;

	@Autowired
	SkillRepo skillRepository;

	@Autowired
	CertificationRepo certificateRepository;

	@Autowired
	WorkExperienceRepo expRepository;

	@Autowired
	OtherSkillsRepo otherSkillRepository;

	@Autowired
	CompanyDetailsRepo companyRepository;

	@Autowired
	ModelMapper modelMapper;

	public ResumeTemplateDto getResumeByPersonId(Long personId) {
		Person person = personRepository.PersonDataByPersonId(personId)
				.orElseThrow(() -> new PersistenceException("Person not found"));
		updateProfileSummary(person);
		ResumeTemplateDto resumeTemplateDto = new ResumeTemplateDto();
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		resumeTemplateDto.setPerson(modelMapper.map(person, PersonDto.class));

		List<Certification> certificates = certificateRepository.findByPersonAndIsDeletedFalseOrderByLastUpdateDateAsc(person);
		List<CertificationDto> certificationDtos = certificates.stream()
				.map(certification -> modelMapper.map(certification, CertificationDto.class))
				.collect(Collectors.toList());
		resumeTemplateDto.setCertificationList(certificationDtos);

		List<WorkExperience> experiences = expRepository.ExperiencDataByPerson(person);
		updateWorkExperienceDescriptions(experiences);

		List<WorkExperienceDto> experienceDto = experiences.stream()
				.map(experience -> modelMapper.map(experience, WorkExperienceDto.class)).collect(Collectors.toList());
		resumeTemplateDto.setExperienceList(experienceDto);

		List<Language> languages = languageRepository.LanguageDataByPerson(person);
		List<LanguageDto> languageDto = languages.stream().map(language -> modelMapper.map(language, LanguageDto.class))
				.collect(Collectors.toList());
		resumeTemplateDto.setLanguageList(languageDto);

		List<Skill> skills = skillRepository.SkillDataByPerson(person);
		List<SkillDto> skillDto = skills.stream().map(skill -> modelMapper.map(skill, SkillDto.class))
				.collect(Collectors.toList());
		resumeTemplateDto.setSkillsList(skillDto);

		Optional<OtherSkills> otherSkills = otherSkillRepository.findByPersonOrderByLastUpdateDateAsc(person);
		if (otherSkills.isPresent()) {
			resumeTemplateDto.setOtherSkillsList(modelMapper.map(otherSkills.get(), OtherSkillDto.class));
		}

		CompanyDetails company = companyRepository.findByCompanyCodeAndIsDeletedFalse(person.getCompanyCode());
		if (company != null) {
			resumeTemplateDto.setCompany(modelMapper.map(company, CompanyDetailsDto.class));

			if (company.isShowEducation()) {
				List<Education> educations = educationRepository.EducationDataByPerson(person);
				updateEducationDescriptions(educations);
				List<EducationDto> educationDto = educations.stream()
						.map(education -> modelMapper.map(education, EducationDto.class)).collect(Collectors.toList());
				resumeTemplateDto.setEducationList(educationDto);
			}
		} else {
			List<Education> educations = educationRepository.EducationDataByPerson(person);
			updateEducationDescriptions(educations);
			List<EducationDto> educationDto = educations.stream()
					.map(education -> modelMapper.map(education, EducationDto.class)).collect(Collectors.toList());
			resumeTemplateDto.setEducationList(educationDto);
		}

		return resumeTemplateDto;
	}

	public void getCroppedImage(Long id, Boolean showProfile) {
		BufferedImage inputImage = null;
		Person person = personRepository.findByPersonIdAndIsDeletedFalse(id)
				.orElseThrow(() -> new PersistenceException("Person not found"));
		String imageUrl = person.getProfilePic();
		if (imageUrl == null || !showProfile) {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Template/images/avatar.png");
			if (inputStream != null) {
				try {
					byte[] imageBytes = inputStream.readAllBytes();
					inputImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			try {
				inputImage = ImageIO.read(new URL(imageUrl));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (inputImage != null) {
			String outputImagePath = "Template/images/person.png";
			File outputFile = new File(outputImagePath);
			outputFile.getParentFile().mkdirs();

			try {
				BufferedImage roundedImage = ImageUtil.makeRoundedCorner(inputImage);
				ImageIO.write(roundedImage, "png", outputFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateWorkExperienceDescriptions(List<WorkExperience> experiences) {
		for (WorkExperience experience : experiences) {
			String description = experience.getDescription();
			if (description != null) {
				String updatedDescription = description.replace("<strong>", "<b>").replace("</strong>", "</b>");
				experience.setDescription(updatedDescription);
			}
		}
	}

	public void updateEducationDescriptions(List<Education> educations) {
		for (Education education : educations) {
			String description = education.getDescription();
			if (description != null) {
				String updatedDescription = description.replace("<strong>", "<b>").replace("</strong>", "</b>");
				education.setDescription(updatedDescription);
			}
		}
	}

	public void updateProfileSummary(Person person) {
		String summary = person.getSummary();
		if (summary != null) {
			String updatedSummary = summary.replace("<strong>", "<b>").replace("</strong>", "</b>");
			person.setSummary(updatedSummary);
		}
	}

	public ResponseEntity<Resource> convertHtmlToPdf(String htmlFilePath) throws DocumentException {
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();

        try (FileInputStream htmlFileStream = new FileInputStream(htmlFilePath)) {
            byte[] htmlContent = htmlFileStream.readAllBytes();
            String htmlString = new String(htmlContent, StandardCharsets.UTF_8);
            
            // Parse the HTML content into a Jsoup Document and convert it to XHTML
            Document document = Jsoup.parse(htmlString);
            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            document.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
            String xhtmlString = document.outerHtml();

            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(xhtmlString);
            renderer.layout();
            renderer.createPDF(pdfOutputStream);

            ByteArrayResource resource = new ByteArrayResource(pdfOutputStream.toByteArray());
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=example.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(resource.contentLength())
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .body(resource);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	public byte[] convertHtmlFileToPdf(String filePath) throws Exception {
        // Read the content of the HTML file
        String htmlContent = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
 
        // Generate PDF from HTML content
        return generatePdfFromHtml(htmlContent);
    }
	
	public byte[] generatePdfFromHtml(String htmlContent) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(outputStream, true); // The 'true' argument indicates auto-loading of images
        renderer.finishPDF();
        return outputStream.toByteArray();
    }
}

//	public void updateCompanyField(boolean value, Person person) {
//		List<WorkExperience> experiences = expRepository.ExperiencDataByPerson(person);
//        for (WorkExperienceDto experience : experiences) {
//            if (!value) {
//                experience.setCompany(null);
//            }
//        }
//	}
