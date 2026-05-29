package com.avisys.email.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avisys.email.entity.AuditingInfo;

public interface AuditingInfoRepository extends JpaRepository<AuditingInfo, Long> {

}

