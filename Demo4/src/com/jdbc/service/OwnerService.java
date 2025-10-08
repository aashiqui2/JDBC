package com.jdbc.service;

import java.util.List;

import com.jdbc.dto.OwnerDTO;
import com.jdbc.enums.PetType;

/* Service Layer
 * Contains business logic and validation rules.
 * Calls the Repository to fetch or save data.
 * Converts Entities ↔ DTOs.
 * Applies business rules like checking duplicates, applying discounts, validating data, etc.
 */
public interface OwnerService {

	
	List<OwnerDTO> findOwners(PetType petType);

	List<OwnerDTO> updatePetDetails(PetType petType, boolean useCallable);
}
