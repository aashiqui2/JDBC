package com.jdbc.repository.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.dto.OwnerDTO;
import com.jdbc.exception.InternalServiceException;
import com.jdbc.repository.OwnerRepository;
import com.jdbc.util.DBUtil;
import com.jdbc.util.MapperUtil;

public class OwnerRepositoryImpl implements OwnerRepository {

	@Override
	public List<OwnerDTO> findOwners(String petType) {
		String sql = "SELECT * FROM owner_table WHERE pet_type = ?";
		List<OwnerDTO> ownerList = new ArrayList<>();

		try (Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(1, petType);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					OwnerDTO owner = MapperUtil.convertOwnerResultSetToDto(resultSet);
					ownerList.add(owner);
				}
			}

		} catch (SQLException exception) {
			throw new InternalServiceException("Error while finding owners: " + exception.getMessage());
		}

		return ownerList;
	}

	@Override
	public List<OwnerDTO> updatePetDetailsWithoutCallable(String petType) {
		String updateSql = """
				UPDATE owner_table SET pet_name =
				CASE pet_gender
				     WHEN 'M' THEN CONCAT('Mr. ', pet_name)
				     WHEN 'F' THEN CONCAT('Miss ', pet_name)
				     ELSE pet_name
				END
				WHERE pet_type = ?""";

		String readSql = "SELECT * FROM owner_table WHERE pet_type = ?";
		List<OwnerDTO> ownerList = new ArrayList<>();

		try (Connection connection = DBUtil.getConnection();
				PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {

			updateStatement.setString(1, petType);
			updateStatement.executeUpdate();

			try (PreparedStatement readStatement = connection.prepareStatement(readSql)) {
				readStatement.setString(1, petType);

				try (ResultSet resultSet = readStatement.executeQuery()) {
					while (resultSet.next()) {
						OwnerDTO owner = MapperUtil.convertOwnerResultSetToDto(resultSet);
						ownerList.add(owner);
					}
				}
			}

		} catch (SQLException exception) {
			throw new InternalServiceException("Error while updating pets: " + exception.getMessage());
		}

		return ownerList;
	}

	@Override
	public List<OwnerDTO> updatePetDetailsWithCallable(String petType) {
		String sql = "CALL add_prefix_to_pet_name(?)"; // Stored Procedure call
		List<OwnerDTO> ownerList = new ArrayList<>();

		try (Connection connection = DBUtil.getConnection();
				CallableStatement callableStatement = connection.prepareCall(sql)) {

			callableStatement.setString(1, petType);

			try (ResultSet resultSet = callableStatement.executeQuery()) {
				while (resultSet.next()) {
					OwnerDTO owner = MapperUtil.convertOwnerResultSetToDto(resultSet);
					ownerList.add(owner);
				}
			}

		} catch (SQLException exception) {
			throw new InternalServiceException("Error while updating pets with callable: " + exception.getMessage());
		}

		return ownerList;
	}
}
