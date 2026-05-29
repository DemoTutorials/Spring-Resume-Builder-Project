package com.avisys.gateway.apigateway.utils;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avisys.gateway.apigateway.entity.APIEndpoints;

public interface EndpointsRepository extends JpaRepository<APIEndpoints, Long> {

}
