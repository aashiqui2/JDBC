package com.jdbc.service;

import java.util.List;

import com.jdbc.dto.OwnerDTO;

/* Service Layer
 * Contains business logic and validation rules.
 * Calls the Repository to fetch or save data.
 * Converts Entities ↔ DTOs.
 * Applies business rules like checking duplicates, applying discounts, validating data, etc.
 */
public interface OwnerService {

	void saveOwnersAutomatically(List<OwnerDTO> ownerDTOs);

	void saveOwnersManually(List<OwnerDTO> ownerDTOs);

	void saveOwnersManuallyWithSavepoint(List<OwnerDTO> ownerDTOs);
}
