package com.jdbc.repository;

import java.util.List;

import com.jdbc.dto.OwnerDTO;

public interface OwnerRepository {
	List<OwnerDTO> findOwners(String petType);

	List<OwnerDTO> updatePetDetailsWithoutCallable(String petType);
	
	List<OwnerDTO> updatePetDetailsWithCallable(String petType);

}
