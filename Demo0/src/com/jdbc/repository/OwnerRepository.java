package com.jdbc.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.jdbc.dto.OwnerDTO;

public interface OwnerRepository {
	void saveOwner(OwnerDTO owner);

	Optional<OwnerDTO> findOwner(int ownerId);

	void updatePetDetails(int ownerId, String petName);

	void deleteOwner(int ownerId);

	List<OwnerDTO> findAllOwners();
	
	Optional<OwnerDTO> findOwnerWithEmailIdAndPetBirthDate(String ownerEmailId,LocalDate petBirthDate);

}
