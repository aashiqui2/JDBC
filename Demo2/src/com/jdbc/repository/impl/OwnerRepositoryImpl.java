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
        String sql = "INSERT INTO owner_table (id, first_name, last_name, gender, city, state, mobile_number, email_id, "
                + "pet_id, pet_name, pet_date_of_birth, pet_gender, pet_type) VALUES (" + owner.getId() + " , '"
                + owner.getFirstName() + "' , '" + owner.getLastName() + "' , '" + owner.getGender().toString()
                + "' , '" + owner.getCity() + "' , '" + owner.getState() + "' , '" + owner.getMobileNumber() + "' , '"
                + owner.getEmailId() + "' , " + owner.getPetId() + " , '" + owner.getPetName() + "' , '"
                + Date.valueOf(owner.getPetBirthDate()) + "' , '" + owner.getPetGender().toString() + "' , '"
                + owner.getPetType().toString() + "')";

        Connection connection = null;
        Statement statement = null;

        try {
            connection = DBUtil.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new InternalServiceException(e.getMessage());
        } finally {
            DBUtil.close(statement);
            DBUtil.close(connection);
        }
    }

    @Override
    public OwnerDTO findOwner(int ownerId) {
        String sql = "SELECT * FROM owner_table WHERE id = " + ownerId;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        OwnerDTO owner = null;

        try {
            connection = DBUtil.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                owner = MapperUtil.convertOwnerResultSetToDto(resultSet);
            }
        } catch (SQLException e) {
            throw new InternalServiceException(e.getMessage());
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(statement);
            DBUtil.close(connection);
        }
        return owner;
    }

    @Override
    public void updatePetDetails(int ownerId, String petName) {
        String sql = "UPDATE owner_table SET pet_name = '" + petName + "' WHERE id = " + ownerId;

        Connection connection = null;
        Statement statement = null;

        try {
            connection = DBUtil.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new InternalServiceException(e.getMessage());
        } finally {
            DBUtil.close(statement);
            DBUtil.close(connection);
        }
    }

    @Override
    public void deleteOwner(int ownerId) {
        String sql = "DELETE FROM owner_table WHERE id = " + ownerId;

        Connection connection = null;
        Statement statement = null;

        try {
            connection = DBUtil.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new InternalServiceException(e.getMessage());
        } finally {
            DBUtil.close(statement);
            DBUtil.close(connection);
        }
    }

    @Override
    public List<OwnerDTO> findAllOwners() {
        String sql = "SELECT * FROM owner_table";
        List<OwnerDTO> ownerList = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                ownerList.add(MapperUtil.convertOwnerResultSetToDto(resultSet));
            }
        } catch (SQLException e) {
            throw new InternalServiceException(e.getMessage());
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(statement);
            DBUtil.close(connection);
        }
        return ownerList;
    }

    @Override
    public List<OwnerDTO> findOwner(String ownerEmailId, LocalDate petBirthDate) {
        String sql = "SELECT * FROM owner_table WHERE email_id = '" + ownerEmailId
                + "' AND pet_date_of_birth = '" + petBirthDate + "'";
        List<OwnerDTO> ownerList = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBUtil.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                ownerList.add(MapperUtil.convertOwnerResultSetToDto(resultSet));
            }
            
        } catch (SQLException e) {
            throw new InternalServiceException(e.getMessage());
        } finally {
            DBUtil.close(resultSet);
            DBUtil.close(statement);
            DBUtil.close(connection);
        }
        return ownerList;
    }
}
