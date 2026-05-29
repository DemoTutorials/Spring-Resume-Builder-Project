package com.auth.uam.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.auth.uam.dto.UserInvitationDatatableDTO;
import com.auth.uam.entity.UserInvitation;

public interface UserInvitationRepository extends JpaRepository<UserInvitation, Long> {

	Page<UserInvitationDatatableDTO> findByIsDeletedFalse(Pageable pageable);

	Page<UserInvitationDatatableDTO> findByIsDeletedAndFirstNameLikeIgnoreCaseOrIsDeletedAndLastNameLikeIgnoreCaseOrIsDeletedAndEmailLikeIgnoreCase(
			boolean b, String string, boolean c, String string2, boolean d, String string3, Pageable pageable);

	Page<UserInvitationDatatableDTO> findByCompanyCodeAndIsDeletedFalse(String companyCode, Pageable pageable);

	Page<UserInvitationDatatableDTO> findByCompanyCodeAndIsDeletedAndFirstNameLikeIgnoreCaseOrCompanyCodeAndIsDeletedAndLastNameLikeIgnoreCaseOrCompanyCodeAndIsDeletedAndEmailLikeIgnoreCase(
			String companyCode, boolean b, String string, String companyCode2, boolean c, String string2,
			String companyCode3, boolean d, String string3, Pageable pageable);

	Optional<UserInvitation> findByInvitationCodeAndIsDeletedFalse(String token);

}
