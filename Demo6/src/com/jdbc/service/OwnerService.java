package com.jdbc.service;

import java.util.List;

import com.jdbc.dto.OwnerDTO;
import com.jdbc.enums.PetType;
import com.jdbc.exception.DuplicateOwnerException;
import com.jdbc.exception.OwnerNotFoundException;

/* Service Layer
 * Contains business logic and validation rules.
 * Calls the Repository to fetch or save data.
 * Converts Entities ↔ DTOs.
 * Applies business rules like checking duplicates, applying discounts, validating data, etc.
 */
public interface OwnerService {

	void saveOwner(OwnerDTO ownerDTO) throws DuplicateOwnerException;

	OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException;

	void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException;

	void deleteOwner(int ownerId) throws OwnerNotFoundException;

	List<OwnerDTO> findAllOwners();
	
	List<OwnerDTO> findOwners(PetType petType);

	List<OwnerDTO> updatePetDetails(PetType petType, boolean useCallable);
	
	void saveOwnersAutomatically(List<OwnerDTO> ownerDTOs);

	void saveOwnersManually(List<OwnerDTO> ownerDTOs);

	void saveOwnersManuallyWithSavepoint(List<OwnerDTO> ownerDTOs);
}
