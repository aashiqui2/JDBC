package com.jdbc.service.impl;

import java.util.List;


import com.jdbc.dto.OwnerDTO;
import com.jdbc.enums.PetType;
import com.jdbc.repository.OwnerRepository;
import com.jdbc.repository.impl.OwnerRepositoryImpl;
import com.jdbc.service.OwnerService;

public class OwnerServiceImpl implements OwnerService{
	private OwnerRepository ownerRepository;

	public OwnerServiceImpl() {
		this.ownerRepository = new OwnerRepositoryImpl();
	}

	@Override
	public List<OwnerDTO> findOwners(PetType petType) {
		return ownerRepository.findOwners(petType.toString());
	}

	@Override
	public List<OwnerDTO> updatePetDetails(PetType petType, boolean useCallable) {
		if (useCallable) {
			return ownerRepository.updatePetDetailsWithCallable(petType.toString());
		} else {
			return ownerRepository.updatePetDetailsWithoutCallable(petType.toString());
		}
	}

}
