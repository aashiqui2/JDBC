package com.jdbc.repository.impl;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.jdbc.dto.OwnerDTO;
import com.jdbc.enums.Gender;
import com.jdbc.enums.PetType;
import com.jdbc.repository.OwnerRepository;
import com.jdbc.util.InputUtil;


public class OwnerRepositoryImpl implements OwnerRepository {

	private static List<OwnerDTO> ownerDTOList;

	static {
		ownerDTOList = new ArrayList<>();
		OwnerDTO ownerDTO = new OwnerDTO();
		ownerDTO.setId(1);
		ownerDTO.setFirstName("Aashiqui");
		ownerDTO.setLastName("Bhai");
		ownerDTO.setGender(Gender.M);
		ownerDTO.setCity("Erode");
		ownerDTO.setState("Tamilnadu");
		ownerDTO.setMobileNumber("6384679229");
		ownerDTO.setEmailId("ashikmail2747@gmail.com");
		ownerDTO.setPetId(1);
		ownerDTO.setPetName("Shalu");
		ownerDTO.setPetBirthDate(InputUtil.convertStringToDate("01-01-2001"));
		ownerDTO.setPetGender(Gender.M);
		ownerDTO.setPetType(PetType.BIRD);
		
		ownerDTOList.add(ownerDTO);
	}

	@Override
	public void saveOwner(OwnerDTO ownerDTO) {
		ownerDTOList.add(ownerDTO);
	}

	@Override
	public Optional<OwnerDTO> findOwner(int ownerId) {
		return ownerDTOList.stream()
				.filter(owner -> owner.getId() == ownerId)
				.findFirst();
		// returns Optional<OwnerDTO>
	}

	@Override
	public void updatePetDetails(int ownerId, String petName) {
		ownerDTOList.stream()
			.filter(owner -> owner.getId() == ownerId)
			.findFirst().ifPresent(owner -> owner.setPetName(petName));
	}

	@Override
	public void deleteOwner(int ownerId) {
		ownerDTOList.removeIf(owner -> owner.getId() == ownerId);
	}

	@Override
	public List<OwnerDTO> findAllOwners() {
		return ownerDTOList;
	}

	@Override
	public Optional<OwnerDTO> findOwnerWithEmailIdAndPetBirthDate(String ownerEmailId, LocalDate petBirthDate) {
	    return ownerDTOList.stream()
	            .filter(owner -> owner.getEmailId().equals(ownerEmailId) 
	                          && owner.getPetBirthDate().equals(petBirthDate))
	            .findFirst();
	}


	

}
