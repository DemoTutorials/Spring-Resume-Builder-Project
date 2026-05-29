package com.avisys.cv.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.avisys.cv.dto.ResumeTemplateDto;
import com.avisys.cv.dto.UserDetailsDto;
import com.avisys.cv.dto.WorkExperienceDto;
import com.avisys.cv.proxy.CompanyConfig;
import com.avisys.cv.repository.WorkExperienceRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;

@Service
public class TemplateService {

	@Autowired
	ResumeService resumeService;

	@Autowired
	WorkExperienceRepo expRepository;

	@Autowired
	CompanyConfig companyDetails;

	public ResponseEntity<Resource> exportReport(Long personId, ActionType actionType, PreviewSample previewSample,
			String userId) throws Exception {
		ResumeTemplateDto data = resumeService.getResumeByPersonId(personId);

		if (!data.getPerson().getCompanyCode().equals("CV-001") && data.getCompany() != null) {
			data.getPerson().setName(data.getCompany().isPartialName());
			data.getPerson().setContact(data.getCompany().isShowContact());
			if (!data.getCompany().isShowCompanyName()) {
				for (WorkExperienceDto experience : data.getExperienceList()) {
					experience.setCompany(null);
				}
			}
		} else {
			data.getPerson().setName(false);
			data.getPerson().setContact(true);
		}
		UserDetailsDto userDetails = companyDetails.getCompanyDetails(userId);
		String companyCode = userDetails.getCompanyCode();

		switch (previewSample) {
		case sample_1:
			JasperReport mainReport;
			if (companyCode.equals("CV-001")) {
				ClassPathResource mainReportResource = new ClassPathResource("Template/sample-1/Main.jrxml");
				mainReport = JasperCompileManager.compileReport(mainReportResource.getInputStream());
			} else {
				ClassPathResource mainReportResource = new ClassPathResource("Template/sample-1/MainC.jrxml");
				mainReport = JasperCompileManager.compileReport(mainReportResource.getInputStream());
			}
			ClassPathResource subReport1Resource = new ClassPathResource("Template/sample-1/Person.jrxml");
			JasperReport subReport1 = JasperCompileManager.compileReport(subReport1Resource.getInputStream());

			ClassPathResource subReport2Resource = new ClassPathResource("Template/sample-1/Education.jrxml");
			JasperReport subReport2 = JasperCompileManager.compileReport(subReport2Resource.getInputStream());

			ClassPathResource subReport3Resource = new ClassPathResource("Template/sample-1/Experience.jrxml");
			JasperReport subReport3 = JasperCompileManager.compileReport(subReport3Resource.getInputStream());

			ClassPathResource subReport4Resource = new ClassPathResource("Template/sample-1/Skills.jrxml");
			JasperReport subReport4 = JasperCompileManager.compileReport(subReport4Resource.getInputStream());

			ClassPathResource subReport5Resource = new ClassPathResource("Template/sample-1/Certificate.jrxml");
			JasperReport subReport5 = JasperCompileManager.compileReport(subReport5Resource.getInputStream());

			ClassPathResource subReport6Resource = new ClassPathResource("Template/sample-1/Language.jrxml");
			JasperReport subReport6 = JasperCompileManager.compileReport(subReport6Resource.getInputStream());

			ClassPathResource subReport7Resource = new ClassPathResource("Template/sample-1/OtherSkills.jrxml");
			JasperReport subReport7 = JasperCompileManager.compileReport(subReport7Resource.getInputStream());

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			String jsonData = objectMapper.writeValueAsString(data);
			// Create JSON data source
			JsonDataSource dataSource = new JsonDataSource(new ByteArrayInputStream(jsonData.getBytes()));

			// Set parameters for main report
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("jsonData", dataSource); // Pass JSON data source to main report
			parameters.put("REPORT_DATA_SOURCE", subReport1); // Pass sub-report 1 as parameter
			parameters.put("REPORT_DATA_SOURCE", subReport2);
			parameters.put("REPORT_DATA_SOURCE", subReport3);
			parameters.put("REPORT_DATA_SOURCE", subReport4);
			parameters.put("REPORT_DATA_SOURCE", subReport5);
			parameters.put("REPORT_DATA_SOURCE", subReport6);
			parameters.put("REPORT_DATA_SOURCE", subReport7);
			// Fill the JasperPrint object with data
			JasperPrint print = JasperFillManager.fillReport(mainReport, parameters, dataSource);
			
			Path tempDir = Files.createTempDirectory("report-");
	        File htmlFile = File.createTempFile("htmlExample1", ".html", tempDir.toFile());
	        JasperExportManager.exportReportToHtmlFile(print, htmlFile.getAbsolutePath());

	        File pdfFile = File.createTempFile("pdfExample1", ".pdf", tempDir.toFile());

	        try (ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
	             FileOutputStream fileOutputStream = new FileOutputStream(pdfFile)) {

	            ITextRenderer renderer = new ITextRenderer();
	            renderer.setDocument(htmlFile.toURI().toURL().toString());
	            renderer.layout();
	            renderer.createPDF(pdfOutputStream);
	            renderer.finishPDF();

	            pdfOutputStream.writeTo(fileOutputStream);
	        }

	        byte[] pdfBytes = Files.readAllBytes(pdfFile.toPath());
	        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

	        return ResponseEntity.ok()
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(resource);
	    
//			String htmlFilePath = "src/main/resources/templates/htmlExample1.html";
//			JasperExportManager.exportReportToHtmlFile(print, htmlFilePath);
//
//			try (ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
//			         FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/templates/pdfExample1.pdf")) {
//			        
//			        ITextRenderer renderer = new ITextRenderer();
//			        renderer.setDocument(new File(htmlFilePath).toURI().toURL().toString());
//			        renderer.layout();
//			        renderer.createPDF(pdfOutputStream);
//			        renderer.finishPDF();
//
//			        // Save PDF to file
//			        pdfOutputStream.writeTo(fileOutputStream);
//			    }
//
//		// Read the PDF file content
//	    Path path = Paths.get("src/main/resources/templates/pdfExample1.pdf");
//	    byte[] pdfBytes = Files.readAllBytes(path);
//
//	    // Create ByteArrayResource from PDF content
//	    ByteArrayResource resource = new ByteArrayResource(pdfBytes);
//
//	    // Return the PDF content as a ResponseEntity
//	    return ResponseEntity.ok()
//	            .contentType(MediaType.APPLICATION_PDF)
//	            .body(resource);


		case sample_2:

			JasperReport mainReport2;
			if (companyCode.equals("CV-001") || data.getCompany() == null) {
				resumeService.getCroppedImage(personId, true);
				ClassPathResource mainReport2Resource = new ClassPathResource("Template/CV-Sample-2/Main.jrxml");
				mainReport2 = JasperCompileManager.compileReport(mainReport2Resource.getInputStream());
			} else {
				resumeService.getCroppedImage(personId, data.getCompany().isShowProfilePicture());
				ClassPathResource mainReport2Resource = new ClassPathResource("Template/CV-Sample-2/MainC.jrxml");
				mainReport2 = JasperCompileManager.compileReport(mainReport2Resource.getInputStream());
			}

			// Load sub-report templates
			ClassPathResource sub1Report2Resource = new ClassPathResource("Template/CV-Sample-2/Person.jrxml");
			JasperReport sub1Report2 = JasperCompileManager.compileReport(sub1Report2Resource.getInputStream());

			ClassPathResource sub9Report2Resource = new ClassPathResource("Template/CV-Sample-2/Profile_Img.jrxml");
			JasperReport sub9Report2 = JasperCompileManager.compileReport(sub9Report2Resource.getInputStream());

			ClassPathResource sub8Report2Resource = new ClassPathResource("Template/CV-Sample-2/Contact.jrxml");
			JasperReport sub8Report2 = JasperCompileManager.compileReport(sub8Report2Resource.getInputStream());

			ClassPathResource sub2Report2Resource = new ClassPathResource("Template/CV-Sample-2/Education.jrxml");
			JasperReport sub2Report2 = JasperCompileManager.compileReport(sub2Report2Resource.getInputStream());

			ClassPathResource sub3Report2Resource = new ClassPathResource("Template/CV-Sample-2/Experience.jrxml");
			JasperReport sub3Report2 = JasperCompileManager.compileReport(sub3Report2Resource.getInputStream());

			ClassPathResource sub4Report2Resource = new ClassPathResource("Template/CV-Sample-2/Skills.jrxml");
			JasperReport sub4Report2 = JasperCompileManager.compileReport(sub4Report2Resource.getInputStream());

			ClassPathResource sub5Report2Resource = new ClassPathResource("Template/CV-Sample-2/Certificate.jrxml");
			JasperReport sub5Report2 = JasperCompileManager.compileReport(sub5Report2Resource.getInputStream());

			ClassPathResource sub6Report2Resource = new ClassPathResource("Template/CV-Sample-2/Language.jrxml");
			JasperReport sub6Report2 = JasperCompileManager.compileReport(sub6Report2Resource.getInputStream());

			ClassPathResource sub7Report2Resource = new ClassPathResource("Template/CV-Sample-2/OtherSkills.jrxml");
			JasperReport sub7Report2 = JasperCompileManager.compileReport(sub7Report2Resource.getInputStream());

			ObjectMapper objectMapper2 = new ObjectMapper();
			objectMapper2.registerModule(new JavaTimeModule());
			String jsonData2 = objectMapper2.writeValueAsString(data);
			// Create JSON data source
			JsonDataSource dataSource2 = new JsonDataSource(new ByteArrayInputStream(jsonData2.getBytes()));

			// Set parameters for main report
			Map<String, Object> parameters2 = new HashMap<>();
			parameters2.put("jsonData", dataSource2); // Pass JSON data source to main report
			parameters2.put("REPORT_DATA_SOURCE", sub1Report2); // Pass sub-report 1 as parameter
			parameters2.put("REPORT_DATA_SOURCE", sub2Report2);
			parameters2.put("REPORT_DATA_SOURCE", sub3Report2);
			parameters2.put("REPORT_DATA_SOURCE", sub4Report2);
			parameters2.put("REPORT_DATA_SOURCE", sub5Report2);
			parameters2.put("REPORT_DATA_SOURCE", sub6Report2);
			parameters2.put("REPORT_DATA_SOURCE", sub7Report2);
			parameters2.put("REPORT_DATA_SOURCE", sub8Report2);
			parameters2.put("REPORT_DATA_SOURCE", sub9Report2);
			// Fill main report
			JasperPrint print2 = JasperFillManager.fillReport(mainReport2, parameters2, dataSource2);
			Path tempDir2 = Files.createTempDirectory("report-");
	        File htmlFile2 = File.createTempFile("htmlExample2", ".html", tempDir2.toFile());
	        JasperExportManager.exportReportToHtmlFile(print2, htmlFile2.getAbsolutePath());

	        File pdfFile2 = File.createTempFile("pdfExample2", ".pdf", tempDir2.toFile());

	        try (ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
	             FileOutputStream fileOutputStream = new FileOutputStream(pdfFile2)) {

	            ITextRenderer renderer = new ITextRenderer();
	            renderer.setDocument(htmlFile2.toURI().toURL().toString());
	            renderer.layout();
	            renderer.createPDF(pdfOutputStream);
	            renderer.finishPDF();

	            pdfOutputStream.writeTo(fileOutputStream);
	        }

	        byte[] pdfBytes2 = Files.readAllBytes(pdfFile2.toPath());
	        ByteArrayResource resource2 = new ByteArrayResource(pdfBytes2);

	        return ResponseEntity.ok()
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(resource2);
			

		default:
			throw new IllegalArgumentException("Invalid sample provided!");
		}
	}
}
