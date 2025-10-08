package com.jdbc.repository.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

import com.jdbc.dto.OwnerDTO;
import com.jdbc.exception.InternalServiceException;
import com.jdbc.repository.OwnerRepository;
import com.jdbc.util.DBUtil;



public class OwnerRepositoryImpl implements OwnerRepository {

    @Override
    public void saveOwnersAutomatically(List<OwnerDTO> owners) {
        String sql = """
                INSERT INTO owner_table
                (id, first_name, last_name, gender, city, state, mobile_number, email_id,
                pet_id, pet_name, pet_date_of_birth, pet_gender, pet_type)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)""";

        try (Connection connection = DBUtil.getConnection();
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
        } catch (SQLException exception) {
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

        try (Connection connection = DBUtil.getConnection();
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
        } catch (SQLException exception) {
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

        try (Connection connection = DBUtil.getConnection();
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

        } catch (SQLException exception) {
            exception.printStackTrace();
            throw new InternalServiceException(exception.getMessage());
        }
    }

}
