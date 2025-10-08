package com.jdbc.repository.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import com.jdbc.dto.OwnerDTO;
import com.jdbc.exception.InternalServiceException;
import com.jdbc.repository.OwnerRepository;
import com.jdbc.util.DBUtil;
import com.jdbc.util.MapperUtil;

public class OwnerRepositoryImpl implements OwnerRepository {

    @Override
    public void saveOwner(OwnerDTO owner) {
        String sql = """
            INSERT INTO owner_table
            (id, first_name, last_name, gender, city, state, mobile_number, email_id,
            pet_id, pet_name, pet_date_of_birth, pet_gender, pet_type)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

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
        } catch (SQLException e) {
            throw new InternalServiceException(e.getMessage());
        }
    }

    @Override
    public OwnerDTO findOwner(int ownerId) {
        String sql = "SELECT * FROM owner_table WHERE id = ?";
        OwnerDTO owner = null;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, ownerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    owner = MapperUtil.convertOwnerResultSetToDto(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new InternalServiceException(e.getMessage());
        }
        return owner;
    }

    @Override
    public void updatePetDetails(int ownerId, String petName) {
        String sql = "UPDATE owner_table SET pet_name = ? WHERE id = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, petName);
            preparedStatement.setInt(2, ownerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new InternalServiceException(e.getMessage());
        }
    }

    @Override
    public void deleteOwner(int ownerId) {
        String sql = "DELETE FROM owner_table WHERE id = ?";
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, ownerId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new InternalServiceException(e.getMessage());
        }
    }

    @Override
    public List<OwnerDTO> findAllOwners() {
        String sql = "SELECT * FROM owner_table";
        List<OwnerDTO> ownerList = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                ownerList.add(MapperUtil.convertOwnerResultSetToDto(resultSet));
            }
        } catch (SQLException e) {
            throw new InternalServiceException(e.getMessage());
        }
        return ownerList;
    }

    @Override
    public List<OwnerDTO> findOwner(String ownerEmailId, LocalDate petBirthDate) {
        String sql = "SELECT * FROM owner_table WHERE email_id = ? AND pet_date_of_birth = ?";
        List<OwnerDTO> ownerList = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, ownerEmailId);
            preparedStatement.setDate(2, Date.valueOf(petBirthDate));
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ownerList.add(MapperUtil.convertOwnerResultSetToDto(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new InternalServiceException(e.getMessage());
        }
        return ownerList;
    }
}
