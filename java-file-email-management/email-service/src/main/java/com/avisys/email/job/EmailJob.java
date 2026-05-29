package com.avisys.email.job;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class EmailJob extends QuartzJobBean {
	private static final Logger logger = LoggerFactory.getLogger(EmailJob.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MailProperties mailProperties;

	// this method gets called when the job is scheduled for the specific timing for
	// that job instance is set to.

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		logger.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());

		JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
		String subject = jobDataMap.getString("subject");
		String body = jobDataMap.getString("body");
		String recipientEmail = jobDataMap.getString("email");
		String fileName = jobDataMap.getString("fileName");
		byte[] byteArr = (byte[]) jobDataMap.get("attachment");

		String[] recipientEmailArr = null;
		recipientEmailArr = recipientEmail.split(";");

		if(fileName !=null) {
			File outputFile = new File(fileName);
			try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
				outputStream.write(byteArr);
			} catch (Exception e) {
				logger.error(e.toString());
			}
			sendMail(mailProperties.getUsername(), recipientEmailArr, subject, body, outputFile, fileName);			
		}
		else {
			sendMail(mailProperties.getUsername(), recipientEmailArr, subject, body, null, fileName);

		}
	}

	// this method uses the MimeMessage class to send the email to the recipients
	// email address with body and subject
	// (MIME) is an Internet standard that extends the format of email messages

	private void sendMail(String fromEmail, String toEmail[], String subject, String body, File outputFile,
			String fileName) {
		try {
			for (String str : toEmail) {
				logger.info("Sending Email to {}", str);
			}
			MimeMessage message = mailSender.createMimeMessage();

			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.toString());
			messageHelper.setSubject(subject);
			messageHelper.setText(body, true);
			messageHelper.setFrom(fromEmail);
			messageHelper.setTo(toEmail);
			if(outputFile!=null && fileName!=null) {
				messageHelper.addAttachment(fileName, outputFile);				
			}

			mailSender.send(message);
		} catch (MessagingException ex) {
			logger.error("Failed to send email to {}", toEmail);
			logger.error("" + ex);
		}
	}
}
