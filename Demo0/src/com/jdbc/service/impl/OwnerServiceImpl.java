package com.jdbc.service.impl;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.jdbc.dto.OwnerDTO;
import com.jdbc.exception.DuplicateOwnerException;
import com.jdbc.exception.OwnerNotFoundException;
import com.jdbc.repository.OwnerRepository;
import com.jdbc.repository.impl.OwnerRepositoryImpl;
import com.jdbc.service.OwnerService;

public class OwnerServiceImpl implements OwnerService {
	private OwnerRepository ownerRepository;
	private static final String OWNER_ALREADY_EXISTS = "Owner already exists with ownerId ";
	private static final String OWNER_NOT_FOUND = "Can't find owner with ownerId ";
	private static final String OWNER_NOT_FOUND_WITH_EMAIL_PET_BIRTH_DATE = "Can't find owner with email Id and pet birth date ";

	public OwnerServiceImpl() {
		this.ownerRepository = new OwnerRepositoryImpl();
	}

	@Override
	public void saveOwner(OwnerDTO ownerDTO) throws DuplicateOwnerException {
		Optional<OwnerDTO> existingOwnerDTO = ownerRepository.findOwner(ownerDTO.getId());
		if (existingOwnerDTO.isPresent()) {
			throw new DuplicateOwnerException(OWNER_ALREADY_EXISTS + ownerDTO.getId());
		} else {
			ownerRepository.saveOwner(ownerDTO);
		}
	}

	@Override
	public OwnerDTO findOwner(int ownerId) throws OwnerNotFoundException {
		Optional<OwnerDTO> ownerDTO = ownerRepository.findOwner(ownerId);
		if (ownerDTO.isEmpty()) {
			throw new OwnerNotFoundException(OWNER_NOT_FOUND + ownerId);
		} else {
			return ownerDTO.get();
		}
	}

	@Override
	public void updatePetDetails(int ownerId, String petName) throws OwnerNotFoundException {
		Optional<OwnerDTO> ownerDTO = ownerRepository.findOwner(ownerId);
		if (ownerDTO.isEmpty()) {
			throw new OwnerNotFoundException(OWNER_NOT_FOUND + ownerId);
		} else {
			ownerRepository.updatePetDetails(ownerId, petName);
		}
	}

	@Override
	public void deleteOwner(int ownerId) throws OwnerNotFoundException {
		Optional<OwnerDTO> ownerDTO = ownerRepository.findOwner(ownerId);
		if (ownerDTO.isEmpty()) {
			throw new OwnerNotFoundException(OWNER_NOT_FOUND + ownerId);
		} else {
			ownerRepository.deleteOwner(ownerId);
		}
	}

	@Override
	public List<OwnerDTO> findAllOwners() {
		return ownerRepository.findAllOwners();
	}

	@Override
	public List<OwnerDTO> findOwnerWithEmailIdAndPetBirthDate(String ownerEmailId, LocalDate petBirthDate)
			throws OwnerNotFoundException {
		
		Optional<OwnerDTO> ownerDTO=ownerRepository.findOwnerWithEmailIdAndPetBirthDate(ownerEmailId, petBirthDate);
		if (ownerDTO.isEmpty()) {
			throw new OwnerNotFoundException(OWNER_NOT_FOUND_WITH_EMAIL_PET_BIRTH_DATE+ ownerEmailId +petBirthDate);
		} else {
			return List.of(ownerDTO.get());
		}	
	}

}
