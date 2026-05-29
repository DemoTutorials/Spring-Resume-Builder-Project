package com.avisys.microservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avisys.microservice.model.TemplateVariables;
import com.avisys.microservice.util.Constants;

@RestController
@RequestMapping("/masters")
public class EmailTemplateVariableController {

	@Autowired
	Constants templateWrapper;

	@GetMapping("/templateVariables")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	public ResponseEntity<List<TemplateVariables>> fetchAllEmailTemplate() {

		List<TemplateVariables> tVariables = new ArrayList<>();
		JSONArray json = new JSONArray(templateWrapper.getEmailTemplateVariables());

		for (int i = 0; i < json.length(); i++) {
			JSONObject jobject = new JSONObject(json.get(i).toString());
			String next = jobject.keys().next();
			tVariables.add(new TemplateVariables(next, jobject.getString(next)));
		}

		return new ResponseEntity<>(tVariables, HttpStatus.OK);
	}

}
