package com.jdbc.service.impl;

import java.util.List;


import com.jdbc.dto.OwnerDTO;
import com.jdbc.repository.OwnerRepository;
import com.jdbc.repository.impl.OwnerRepositoryImpl;
import com.jdbc.service.OwnerService;

public class OwnerServiceImpl implements OwnerService{
	private OwnerRepository ownerRepository;

	public OwnerServiceImpl() {
		this.ownerRepository = new OwnerRepositoryImpl();
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
