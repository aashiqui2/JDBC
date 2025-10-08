package com.jdbc.repository;

import java.time.LocalDate;
import java.util.List;

import com.jdbc.dto.OwnerDTO;

public interface OwnerRepository {
	void saveOwner(OwnerDTO owner);

	OwnerDTO findOwner(int ownerId);

	void updatePetDetails(int ownerId, String petName);

	void deleteOwner(int ownerId);

	List<OwnerDTO> findAllOwners();
	
	List<OwnerDTO> findOwner(String ownerEmailId, LocalDate petBirthDate);

}
