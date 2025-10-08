package com.jdbc.repository;


import java.util.List;

import com.jdbc.dto.OwnerDTO;

public interface OwnerRepository {
	void saveOwner(OwnerDTO owner);

	OwnerDTO findOwner(int ownerId);

	void updatePetDetails(int ownerId, String petName);

	void deleteOwner(int ownerId);

	List<OwnerDTO> findAllOwners();
	
	
	List<OwnerDTO> findOwners(String petType);

	List<OwnerDTO> updatePetDetailsWithoutCallable(String petType);
	
	List<OwnerDTO> updatePetDetailsWithCallable(String petType);
	
	void saveOwnersAutomatically(List<OwnerDTO> owners);

	void saveOwnersManually(List<OwnerDTO> owners);

	void saveOwnersManuallyWithSavepoint(List<OwnerDTO> owners);

}
