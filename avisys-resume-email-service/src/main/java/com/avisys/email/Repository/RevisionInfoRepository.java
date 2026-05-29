package com.avisys.email.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.avisys.email.dto.TempActivityDTO;
import com.avisys.email.entity.RevisionInfo;

public interface RevisionInfoRepository extends JpaRepository<RevisionInfo, Long> {

	@Query(value = " SELECT revisionin0_.rev_id       AS revId,\r\n"
			+ "       revisionin0_.rev_tstmp    AS revTstmp,\r\n" + "       ui.first_name\r\n" + "       || ' '\r\n"
			+ "       || ui.last_name           AS actionBy,\r\n"
			+ "       revisionin0_.reference_id AS referenceId,\r\n"
			+ "       revisionin0_.entity_tag   AS entityTag,\r\n" + "       revisionin0_.rev_type     AS revType,\r\n"
			+ "       ui1.first_name\r\n" + "       || ' '\r\n" + "       || ui1.last_name          AS actionFor,\r\n"
			+ "       ai.field_name             AS variableName,\r\n" + "       CASE\r\n"
			+ "         WHEN cm.value IS NULL THEN ai.old_value\r\n" + "         ELSE cm1.value\r\n"
			+ "       END                       AS oldValue,\r\n" + "       CASE\r\n"
			+ "         WHEN cm.value IS NULL THEN ai.new_value\r\n" + "         ELSE cm.value\r\n"
			+ "       END                       AS newValue\r\n" + "FROM   revision_info revisionin0_\r\n"
			+ "       LEFT JOIN crm.user_info ui\r\n" + "              ON ui.user_id = revisionin0_.action_by\r\n"
			+ "       LEFT JOIN crm.user_info ui1\r\n" + "              ON ui1.user_id = revisionin0_.action_for\r\n"
			+ "       LEFT JOIN auditing_info ai\r\n" + "              ON ai.rev_id = revisionin0_.rev_id\r\n"
			+ "                 AND ( ai.field_name IS NULL\r\n"
			+ "                        OR ai.field_name <> 'updatedDate' )\r\n"
			+ "       LEFT JOIN crm.common_mst cm\r\n" + "              ON cm.code = ai.new_value\r\n"
			+ "       LEFT JOIN crm.common_mst cm1\r\n" + "              ON cm1.code = ai.old_value\r\n"
			+ "WHERE  revisionin0_.entity_tag =?1 \r\n" + "       AND revisionin0_.reference_id =?2 \r\n"
			+ "ORDER  BY revisionin0_.rev_tstmp DESC  ", nativeQuery = true)
	List<TempActivityDTO> getList(String entityTag, Long referenceId);

}
