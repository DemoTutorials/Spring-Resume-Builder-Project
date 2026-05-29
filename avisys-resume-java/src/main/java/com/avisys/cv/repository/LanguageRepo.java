package com.avisys.cv.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.avisys.cv.entity.Person;
import com.avisys.cv.entity.Language;

@Repository
public interface LanguageRepo extends JpaRepository<Language, Long> {
	
	List<Language> findByPersonAndIsDeletedFalseOrderByLastUpdateDateAsc(Person person);

	List<Language> findByPersonAndIsDeletedFalse(Person person);

	Optional<Language> findByLangIdAndIsDeletedFalse(Long languageId);


	@Query("SELECT new com.avisys.cv.entity.Language(c.langId, c.person, c.language, cm1.value as languageValue) " +
	"FROM Language c " +
	"LEFT JOIN CommonMaster cm1 ON cm1.code = c.language " +
	"WHERE c.person = :person AND c.isDeleted = false " +
	"ORDER BY c.lastUpdateDate ASC")
	List<Language> LanguageDataByPerson(Person person);

	Language findByCreatedBy(String userId);

}
