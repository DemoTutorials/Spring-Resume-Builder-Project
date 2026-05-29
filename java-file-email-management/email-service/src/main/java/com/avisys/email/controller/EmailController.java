package com.avisys.email.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avisys.email.dao.EmailDao;
import com.avisys.email.model.EmailDetails;
import com.avisys.email.model.ProductData;
import com.avisys.email.model.TableHeader;

@RestController
public class EmailController {

	@Autowired
	EmailDao emailDao;

	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

	@GetMapping("/emailDetails")
	@CrossOrigin(origins = "*", allowedHeaders = "*")
//	@JsonView({ Views.productLineView.class })
	public ProductData<EmailDetails> getProductline(@RequestParam(required = false, defaultValue = "1") Integer pageNo,
			@RequestParam(required = false, defaultValue = "5") Integer pageSize,
			@RequestParam(required = false) String[] sort, @Nullable String search) {

		ProductData<EmailDetails> productData = new ProductData<EmailDetails>();
		ArrayList<TableHeader> headerlist = new ArrayList<TableHeader>();

		headerlist.add(new TableHeader("", "productLineId", 10, "radio", "product_line_id"));
		headerlist.add(new TableHeader("Name", "productLine", 30, "", "product_line"));
		headerlist.add(new TableHeader("Description", "description", 30, "", "product_line_description"));
		headerlist.add(new TableHeader("Icon", "productLineIcon", 30, "", "product_line_icon"));
		productData.setHeaderlist(headerlist);

		// setting up a default sorting values
		if (sort == null || sort.toString().isEmpty()) {
			String[] ar = { "job_name", "asc" };

			productData.setPage(emailDao.getEmailDetailsPaging(pageNo, pageSize, ar, search));
		} else {

			productData.setPage(emailDao.getEmailDetailsPaging(pageNo, pageSize, sort, search));
		}

		return productData;
	}

}
