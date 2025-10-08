package com.jdbc.service.impl;


import java.util.List;
import java.util.Objects;

import com.jdbc.config.PropertiesConfig;
import com.jdbc.dto.OwnerDTO;
import com.jdbc.enums.PetType;
import com.jdbc.exception.DuplicateOwnerException;
import com.jdbc.exception.OwnerNotFoundException;
import com.jdbc.repository.OwnerRepository;
import com.jdbc.repository.impl.OwnerRepositoryImpl;
import com.jdbc.service.OwnerService;

public class OwnerServiceImpl implements OwnerService{
	private OwnerRepository ownerRepository;
	private static final String OWNER_ALREADY_EXISTS = "owner.already.exists";
	private static final String OWNER_NOT_FOUND = "owner.not.found";
	private static final PropertiesConfig PROPERTIES_CONFIG = PropertiesConfig.getInstance();

	public OwnerServiceImpl() {
		this.ownerRepository = new OwnerRepositoryImpl();
	}

	@Override
	public void saveOwner(OwnerDTO ownerDTO) throws DuplicateOwnerException {
		OwnerDTO existingOwner = ownerRepository.findOwner(ownerDTO.getId());
		if (Objects.nonNull(existingOwner)) {
			throw new DuplicateOwnerException(String.format(PROPERTIES_CONFIG.getProperty(OWNER_ALREADY_EXISTS), ownerDTO.getId()));
		} else {
			ownerRepository.saveOwner(ownerDTO);
		}
	}

	@Override
	public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException {
		OwnerDTO owner = ownerRepository.findOwner(ownerId);
		if (Objects.isNull(owner)) {
			throw new OwnerNotFoundException(String.format(PROPERTIES_CONFIG.getProperty(OWNER_NOT_FOUND), ownerId));
		} else {
			return owner;
		}
	}

	@Override
	public void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException {
		OwnerDTO owner = ownerRepository.findOwner(ownerId);
		if (Objects.isNull(owner)) {
			throw new OwnerNotFoundException(String.format(PROPERTIES_CONFIG.getProperty(OWNER_NOT_FOUND), ownerId));
		} else {
			ownerRepository.updatePetDetails(ownerId, petName);
		}
	}

	@Override
	public void deleteOwner(int ownerId) throws OwnerNotFoundException {
		OwnerDTO owner = ownerRepository.findOwner(ownerId);
		if (Objects.isNull(owner)) {
			throw new OwnerNotFoundException(String.format(PROPERTIES_CONFIG.getProperty(OWNER_NOT_FOUND), ownerId));
		} else {
			ownerRepository.deleteOwner(ownerId);
		}
	}

	@Override
	public List<OwnerDTO> findAllOwners() {
		return ownerRepository.findAllOwners();
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
	
	@Override
	public void saveOwnersAutomatically(List<OwnerDTO> ownerDTOs) {
		ownerRepository.saveOwnersAutomatically(ownerDTOs);
	}

	@Override
	public void saveOwnersManually(List<OwnerDTO> ownerDTOs) {
		ownerRepository.saveOwnersManually(ownerDTOs);
	}

	@Override
	public void saveOwnersManuallyWithSavepoint(List<OwnerDTO> ownerDTOs) {
		ownerRepository.saveOwnersManuallyWithSavepoint(ownerDTOs);
	}


}
