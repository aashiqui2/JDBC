package com.jdbc.repository;

import java.util.List;

import com.jdbc.dto.OwnerDTO;

public interface OwnerRepository {
	void saveOwnersAutomatically(List<OwnerDTO> owners);

	void saveOwnersManually(List<OwnerDTO> owners);

	void saveOwnersManuallyWithSavepoint(List<OwnerDTO> owners);

}
