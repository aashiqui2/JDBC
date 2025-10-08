package com.jdbc.repository.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.config.DatabaseConfig;
import com.jdbc.dto.OwnerDTO;
import com.jdbc.exception.InternalServiceException;
import com.jdbc.repository.OwnerRepository;
import com.jdbc.util.MapperUtil;

public class OwnerRepositoryImpl implements OwnerRepository {

	@Override
	public void saveOwner(OwnerDTO owner) {
		String sql = """
				INSERT INTO owner_table
				(id, first_name, last_name, gender, city, state, mobile_number, email_id,
				pet_id, pet_name, pet_date_of_birth, pet_gender, pet_type)
				VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";
		try (Connection connection = DatabaseConfig.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setInt(1, owner.getId());
			preparedStatement.setString(2, owner.getFirstName());
			preparedStatement.setString(3, owner.getLastName());
			preparedStatement.setString(4, owner.getGender().toString());
			preparedStatement.setString(5, owner.getCity());
			preparedStatement.setString(6, owner.getState());
			preparedStatement.setString(7, owner.getMobileNumber());
			preparedStatement.setString(8, owner.getEmailId());
			preparedStatement.setInt(9, owner.getPetId());
			preparedStatement.setString(10, owner.getPetName());
			preparedStatement.setDate(11, Date.valueOf(owner.getPetBirthDate()));
			preparedStatement.setString(12, owner.getPetGender().toString());
			preparedStatement.setString(13, owner.getPetType().toString());
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException exception) {
			throw new InternalServiceException(exception.getMessage());
		}
	}

	@Override
	public OwnerDTO findOwner(int ownerId) {
		String sql = "SELECT * FROM owner_table WHERE id = ?";
		OwnerDTO owner = null;
		try (   Connection connection = DatabaseConfig.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
			) {
			preparedStatement.setInt(1, ownerId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				owner = MapperUtil.convertOwnerResultSetToDto(resultSet);
			}
		} catch (ClassNotFoundException | SQLException exception) {
			throw new InternalServiceException(exception.getMessage());
		}
		return owner;
	}

	@Override
	public void updatePetDetails(int ownerId, String petName) {
		String sql = "UPDATE owner_table SET pet_name = ? WHERE id = ?";
		try (Connection connection = DatabaseConfig.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setString(1, petName);
			preparedStatement.setInt(2, ownerId);
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException exception) {
			throw new InternalServiceException(exception.getMessage());
		}
	}

	@Override
	public void deleteOwner(int ownerId) {
		String sql = "DELETE FROM owner_table WHERE id = ?";
		try (Connection connection = DatabaseConfig.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
			preparedStatement.setInt(1, ownerId);
			preparedStatement.executeUpdate();
		} catch (ClassNotFoundException | SQLException exception) {
			throw new InternalServiceException(exception.getMessage());
		}
	}

	@Override
	public List<OwnerDTO> findAllOwners() {
		String sql = "SELECT * FROM owner_table";
		List<OwnerDTO> ownerList = new ArrayList<>();
		try (Connection connection = DatabaseConfig.getConnection();
				Statement statement = connection.createStatement();) {
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				OwnerDTO owner = MapperUtil.convertOwnerResultSetToDto(resultSet);
				ownerList.add(owner);
			}
		} catch (ClassNotFoundException | SQLException exception) {
			throw new InternalServiceException(exception.getMessage());
		}
		return ownerList;
	}
	
	@Override
	public List<OwnerDTO> findOwners(String petType) {
		String sql = "SELECT * FROM owner_table WHERE pet_type = ?";
		List<OwnerDTO> ownerList = new ArrayList<>();

		try (Connection connection = DatabaseConfig.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			preparedStatement.setString(1, petType);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					OwnerDTO owner = MapperUtil.convertOwnerResultSetToDto(resultSet);
					ownerList.add(owner);
				}
			}

		} catch (ClassNotFoundException |SQLException exception) {
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

		try (Connection connection =DatabaseConfig.getConnection();
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

		} catch (ClassNotFoundException |SQLException exception) {
			throw new InternalServiceException("Error while updating pets: " + exception.getMessage());
		}

		return ownerList;
	}

	@Override
	public List<OwnerDTO> updatePetDetailsWithCallable(String petType) {
		String sql = "CALL add_prefix_to_pet_name(?)"; // Stored Procedure call
		List<OwnerDTO> ownerList = new ArrayList<>();

		try (Connection connection = DatabaseConfig.getConnection();
				CallableStatement callableStatement = connection.prepareCall(sql)) {

			callableStatement.setString(1, petType);

			try (ResultSet resultSet = callableStatement.executeQuery()) {
				while (resultSet.next()) {
					OwnerDTO owner = MapperUtil.convertOwnerResultSetToDto(resultSet);
					ownerList.add(owner);
				}
			}

		} catch (ClassNotFoundException |SQLException exception) {
			throw new InternalServiceException("Error while updating pets with callable: " + exception.getMessage());
		}

		return ownerList;
	}
	 @Override
	    public void saveOwnersAutomatically(List<OwnerDTO> owners) {
	        String sql = """
	                INSERT INTO owner_table
	                (id, first_name, last_name, gender, city, state, mobile_number, email_id,
	                pet_id, pet_name, pet_date_of_birth, pet_gender, pet_type)
	                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";

	        try (Connection connection = DatabaseConfig.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

	            for (OwnerDTO owner : owners) {
	                preparedStatement.setInt(1, owner.getId());
	                preparedStatement.setString(2, owner.getFirstName());
	                preparedStatement.setString(3, owner.getLastName());
	                preparedStatement.setString(4, owner.getGender().toString());
	                preparedStatement.setString(5, owner.getCity());
	                preparedStatement.setString(6, owner.getState());
	                preparedStatement.setString(7, owner.getMobileNumber());
	                preparedStatement.setString(8, owner.getEmailId());
	                preparedStatement.setInt(9, owner.getPetId());
	                preparedStatement.setString(10, owner.getPetName());
	                preparedStatement.setDate(11, Date.valueOf(owner.getPetBirthDate()));
	                preparedStatement.setString(12, owner.getPetGender().toString());
	                preparedStatement.setString(13, owner.getPetType().toString());
	                preparedStatement.executeUpdate();
	            }
	        } catch (SQLException | ClassNotFoundException exception) {
	            exception.printStackTrace();
	            throw new InternalServiceException(exception.getMessage());
	        }
	    }

	    @Override
	    public void saveOwnersManually(List<OwnerDTO> owners) {
	        String sql = """
	                INSERT INTO owner_table
	                (id, first_name, last_name, gender, city, state, mobile_number, email_id,
	                pet_id, pet_name, pet_date_of_birth, pet_gender, pet_type)
	                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";

	        try (Connection connection = DatabaseConfig.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

	            connection.setAutoCommit(false);

	            for (OwnerDTO owner : owners) {
	                preparedStatement.setInt(1, owner.getId());
	                preparedStatement.setString(2, owner.getFirstName());
	                preparedStatement.setString(3, owner.getLastName());
	                preparedStatement.setString(4, owner.getGender().toString());
	                preparedStatement.setString(5, owner.getCity());
	                preparedStatement.setString(6, owner.getState());
	                preparedStatement.setString(7, owner.getMobileNumber());
	                preparedStatement.setString(8, owner.getEmailId());
	                preparedStatement.setInt(9, owner.getPetId());
	                preparedStatement.setString(10, owner.getPetName());
	                preparedStatement.setDate(11, Date.valueOf(owner.getPetBirthDate()));
	                preparedStatement.setString(12, owner.getPetGender().toString());
	                preparedStatement.setString(13, owner.getPetType().toString());
	                preparedStatement.executeUpdate();
	            }
	            connection.commit();
	        } catch (SQLException | ClassNotFoundException exception) {
	            exception.printStackTrace();
	            throw new InternalServiceException(exception.getMessage());
	        }
	    }

	    @Override
	    public void saveOwnersManuallyWithSavepoint(List<OwnerDTO> owners) {
	        String sql = """
	                INSERT INTO owner_table
	                (id, first_name, last_name, gender, city, state, mobile_number, email_id,
	                pet_id, pet_name, pet_date_of_birth, pet_gender, pet_type)
	                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";

	        try (Connection connection = DatabaseConfig.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

	            connection.setAutoCommit(false);
	            
	            Savepoint savepoint = null;

	            for (OwnerDTO owner : owners) {
	                try {
	                    preparedStatement.setInt(1, owner.getId());
	                    preparedStatement.setString(2, owner.getFirstName());
	                    preparedStatement.setString(3, owner.getLastName());
	                    preparedStatement.setString(4, owner.getGender().toString());
	                    preparedStatement.setString(5, owner.getCity());
	                    preparedStatement.setString(6, owner.getState());
	                    preparedStatement.setString(7, owner.getMobileNumber());
	                    preparedStatement.setString(8, owner.getEmailId());
	                    preparedStatement.setInt(9, owner.getPetId());
	                    preparedStatement.setString(10, owner.getPetName());
	                    preparedStatement.setDate(11, Date.valueOf(owner.getPetBirthDate()));
	                    preparedStatement.setString(12, owner.getPetGender().toString());
	                    preparedStatement.setString(13, owner.getPetType().toString());
	                    preparedStatement.executeUpdate();

	                    // Set a savepoint after every odd id
	                    if (owner.getId() % 2 != 0) {
	                        savepoint = connection.setSavepoint("Savepoint_after_owner_" + owner.getId());
	                        System.out.println("✅ Savepoint created after ownerId " + owner.getId());
	                    }

	                } catch (SQLException ex) {
	                    System.err.println("⚠️ Error inserting ownerId " + owner.getId() + ": " + ex.getMessage());

	                    if (savepoint != null) {
	                        System.out.println("↩️ Rolling back to " + savepoint.getSavepointName());
	                        connection.rollback(savepoint);  // rollback only to last savepoint
	                    } else {
	                        System.out.println("↩️ Rolling back entire transaction");
	                        connection.rollback(); // rollback everything
	                    }
	                }
	            }

	            connection.commit();
	            System.out.println("✅ Transaction committed successfully");

	        } catch (SQLException | ClassNotFoundException exception) {
	            exception.printStackTrace();
	            throw new InternalServiceException(exception.getMessage());
	        }
	    }

}
