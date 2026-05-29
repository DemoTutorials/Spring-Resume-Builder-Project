package com.auth.uam.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.auth.uam.dto.JobPostDTO;
import com.auth.uam.dto.MailDTO;
import com.auth.uam.entity.JobPost;
import com.auth.uam.entity.User;
import com.auth.uam.repository.JobPostRepository;
import com.auth.uam.repository.UserRepository;

@Service
public class JobPostService {

    @Autowired
    private JobPostRepository jobPostRepository;
    
    @Autowired
	UserRepository userRepository;
    
    @Autowired
	MailService mailService;

    public JobPost saveJobPost(JobPost jobPost, String uid) {
    	Optional<JobPost> jobpostCode = jobPostRepository.findByJobPostCodeAndIsDeletedFalse(jobPost.getJobPostCode());
        if(jobpostCode.isPresent()) {
        	 throw new PersistenceException("JobPostCode is already present");
        }
    	jobPost.setCreationDate(LocalDateTime.now());
        jobPost.setLastUpdateDate(LocalDateTime.now());
        jobPost.setCreatedBy(uid);
        jobPost.setLastUpdateBy(uid);
        jobPost.setIsDeleted(false);
        saveMailForHrPostLink(jobPost, uid);
        return jobPostRepository.save(jobPost);
    }

    public JobPost updateJobPost(Long jobId, JobPost jobPostDetails, String uid) {
        Optional<JobPost> jobPostOptional = jobPostRepository.findById(jobId);
        if (jobPostOptional.isPresent()) {
            JobPost jobPost = jobPostOptional.get();
            jobPost.setJobPostCode(jobPostDetails.getJobPostCode());
            jobPost.setCompanyCode(jobPostDetails.getCompanyCode());
            jobPost.setJobProfile(jobPostDetails.getJobProfile());
            jobPost.setDescription(jobPostDetails.getDescription());
            jobPost.setActive(jobPostDetails.isActive());
            jobPost.setIsDeleted(false);
            jobPost.setLastUpdateBy(uid);
            jobPost.setLastUpdateDate(LocalDateTime.now());
            return jobPostRepository.save(jobPost);
        } else {
            throw new PersistenceException("JobPost not found with id " + jobId);
        }
    }

    public List<JobPost> getJobPostsByCompanyCode(String companyCode) {
        return jobPostRepository.findByCompanyCode(companyCode);
    }
    
    public List<JobPost> getAllJobPosts() {
        return jobPostRepository.findAll();
    }
    
    public Page<JobPostDTO> searchJobPost(Pageable pageable, String keyword, String userId) {
    	Optional<User> user = userRepository.findByUserIdAndIsDeleted(userId, false);
        String formattedKeyword = "%" + keyword.trim().toLowerCase() + "%";
        return jobPostRepository.searchJobPostCode(formattedKeyword, user.get().getCompanyCode(), pageable);
    }
    
    public JobPost getJobPostById(Long id) {
        Optional<JobPost> jobPost = jobPostRepository.findById(id);
        if (jobPost.isPresent()) {
            return jobPost.get();
        } else {
            throw new RuntimeException("JobPost not found for id :: " + id);
        }
    }
    
    public JobPost softDelete(Long id, String uid) {
    	JobPost jobpost = jobPostRepository.findByJobIdAndIsDeletedFalse(id)
				.orElseThrow(() -> new PersistenceException("JobId not found"));
    	jobpost.setIsDeleted(true);
    	jobpost.setLastUpdateDate(LocalDateTime.now());
    	jobpost.setLastUpdateBy(uid);
    	return jobPostRepository.save(jobpost);
	}
    
    public void saveMailForHrPostLink(JobPost jobPost, String uid) {
		List<MultipartFile> file = null;
		MailDTO mailDTO = new MailDTO();
		Optional<User> user = userRepository.findByUserIdAndIsDeleted(uid, false);
		mailDTO.setSendTO(user.get().getEmail());
		mailDTO.setEmailBody("https://www.buildresume.co.in/app/auth/login/" + jobPost.getJobPostCode());
		mailDTO.setReferenceId(jobPost.getJobId() + "");
		String senderName = "Build Resume Team";
		mailService.sendMailToHR(mailDTO, file, user.get().getFirstName() + " " + user.get().getLastName(), senderName);
	}
}

