package com.auth.uam.security.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.auth.uam.entity.JobPost;
import com.auth.uam.repository.JobPostRepository;

@Service
public class JobPostService {

    @Autowired
    private JobPostRepository jobPostRepository;

    public JobPost saveJobPost(JobPost jobPost, String uid) {
        jobPost.setCreationDate(LocalDateTime.now());
        jobPost.setLastUpdateDate(LocalDateTime.now());
        jobPost.setCreatedBy(uid);
        jobPost.setLastUpdateBy(uid);
        jobPost.setIsDeleted(false);
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
            jobPost.setIsDeleted(jobPostDetails.getIsDeleted());
            jobPost.setLastUpdateBy(uid);
            jobPost.setLastUpdateDate(LocalDateTime.now());
            return jobPostRepository.save(jobPost);
        } else {
            throw new RuntimeException("JobPost not found with id " + jobId);
        }
    }

    public List<JobPost> getJobPostsByCompanyCode(String companyCode) {
        return jobPostRepository.findByCompanyCode(companyCode);
    }
    
    public List<JobPost> getAllJobPosts() {
        return jobPostRepository.findAll();
    }
    
    public Page<JobPost> searchJobPost(Pageable pageable, String keyword) {
        String formattedKeyword = "%" + keyword.trim().toLowerCase() + "%";
        return jobPostRepository.searchJobPostCode(pageable, formattedKeyword);
    }
}

