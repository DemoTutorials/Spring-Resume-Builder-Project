package com.auth.uam.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth.uam.dto.JobPostDTO;
import com.auth.uam.entity.JobPost;
import com.auth.uam.entity.User;
import com.auth.uam.service.JobPostService;

@RestController
@RequestMapping("/job-posts")
public class JobPostController {

    @Autowired
    private JobPostService jobPostService;

    @PostMapping("/save")
    public ResponseEntity<JobPost> createJobPost(@RequestBody JobPost jobPost, @RequestHeader HttpHeaders headers) {
    	String userId = headers.getFirst("userId");
        JobPost savedJobPost = jobPostService.saveJobPost(jobPost, userId);
        return ResponseEntity.ok(savedJobPost);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<JobPost> updateJobPost(@PathVariable Long id, @RequestBody JobPost jobPostDetails, @RequestHeader HttpHeaders headers) {
    	String userId = headers.getFirst("userId");
        JobPost updatedJobPost = jobPostService.updateJobPost(id, jobPostDetails, userId);
        return ResponseEntity.ok(updatedJobPost);
    }

    @GetMapping("/company/{companyCode}")
    public ResponseEntity<List<JobPost>> getJobPostsByCompanyCode(@PathVariable String companyCode) {
        List<JobPost> jobPosts = jobPostService.getJobPostsByCompanyCode(companyCode);
        return ResponseEntity.ok(jobPosts);
    }
    
    @GetMapping("/get-all")
    public ResponseEntity<List<JobPost>> getAllJobPosts() {
        List<JobPost> jobPosts = jobPostService.getAllJobPosts();
        return ResponseEntity.ok(jobPosts);
    }
    
    @GetMapping("/search")
	public ResponseEntity<Page<JobPostDTO>> searchJobPost(@PageableDefault Pageable pageable,
			@RequestParam(value = "keyword", defaultValue = "", required = false) String keyword, @RequestHeader HttpHeaders headers) {
    	String userId = headers.getFirst("userId");
    	Page<JobPostDTO> result = this.jobPostService.searchJobPost(pageable, keyword, userId);
		return new ResponseEntity<Page<JobPostDTO>>(result, HttpStatus.OK);
	}
    
    @GetMapping("/get/{id}")
    public ResponseEntity<JobPost> getJobPostById(@PathVariable Long id) {
        JobPost jobPost = jobPostService.getJobPostById(id);
        return new ResponseEntity<>(jobPost, HttpStatus.OK);
    }
    
    @DeleteMapping("/soft-delete/{id}")
	public ResponseEntity<JobPost> delete(@PathVariable Long id, @RequestHeader HttpHeaders headers) {
		try {
			String userId = headers.getFirst("userId");
			JobPost job = jobPostService.softDelete(id, userId);
			return new ResponseEntity<>(job, HttpStatus.OK);
		} catch (Exception e) {
			throw new PersistenceException(e.getMessage());
		}
	}
}

