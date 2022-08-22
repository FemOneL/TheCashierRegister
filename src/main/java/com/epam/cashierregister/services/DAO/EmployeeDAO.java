package com.epam.cashierregister.services.DAO;

import com.epam.cashierregister.services.consts.AuthorizeConst;
import com.epam.cashierregister.services.consts.EmployeeConst;
import com.epam.cashierregister.services.consts.RolesConst;
import com.epam.cashierregister.services.entities.employee.AuthorizeInfo;
import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.entities.employee.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAO extends DAO {

    public Employee getEmployee(String email, String password) {
        Employee foundEmployee = null;
        try (Connection connection = getConnection()) {
            String selectUser = "SELECT " + EmployeeConst.EMP_ID + ", " + EmployeeConst.PHOTO + ", " + EmployeeConst.FIRSTNAME + ", " + EmployeeConst.SECONDNAME
                    + ", " + RolesConst.ROLE + ", " + AuthorizeConst.EMAIL + ", " + AuthorizeConst.PASSWORD
                    + " FROM " + EmployeeConst.TABLE_NAME
                    + " INNER JOIN " + RolesConst.TABLE_NAME
                    + " ON " + EmployeeConst.TABLE_NAME + "." + EmployeeConst.ROLE_ID + " = " + RolesConst.TABLE_NAME + "." + RolesConst.ROLE_ID
                    + " INNER JOIN " + AuthorizeConst.TABLE_NAME
                    + " ON " + EmployeeConst.TABLE_NAME + "." + EmployeeConst.AUTHORIZE_ID + " = " + AuthorizeConst.TABLE_NAME + "." + AuthorizeConst.AUTHORIZE_ID
                    + " WHERE " + AuthorizeConst.TABLE_NAME + "." + AuthorizeConst.EMAIL + " = (?) "
                    + " AND " + AuthorizeConst.TABLE_NAME + "." + AuthorizeConst.PASSWORD + " = (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(selectUser);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                foundEmployee = new Employee(resultSet.getInt(EmployeeConst.EMP_ID), resultSet.getString(EmployeeConst.PHOTO), resultSet.getString(EmployeeConst.FIRSTNAME),
                        resultSet.getString(EmployeeConst.SECONDNAME), Role.valueOf(resultSet.getString(RolesConst.ROLE)),
                        new AuthorizeInfo(resultSet.getString(AuthorizeConst.EMAIL), resultSet.getString(AuthorizeConst.PASSWORD)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundEmployee;
    }

    public Employee getEmployee(int id){
        Employee employee = null;
        try (Connection connection = getConnection()){
            String select = "SELECT " + EmployeeConst.EMP_ID + ", " + EmployeeConst.PHOTO + ", " + EmployeeConst.FIRSTNAME + ", " + EmployeeConst.SECONDNAME
                    + ", " + RolesConst.ROLE + ", " + AuthorizeConst.EMAIL + ", " + AuthorizeConst.PASSWORD
                    + " FROM " + EmployeeConst.TABLE_NAME
                    + " INNER JOIN " + RolesConst.TABLE_NAME
                    + " ON " + EmployeeConst.TABLE_NAME + "." + EmployeeConst.ROLE_ID + " = " + RolesConst.TABLE_NAME + "." + RolesConst.ROLE_ID
                    + " INNER JOIN " + AuthorizeConst.TABLE_NAME
                    + " ON " + EmployeeConst.TABLE_NAME + "." + EmployeeConst.AUTHORIZE_ID + " = " + AuthorizeConst.TABLE_NAME + "." + AuthorizeConst.AUTHORIZE_ID
                    + " WHERE " + EmployeeConst.EMP_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                employee = new Employee(resultSet.getInt(EmployeeConst.EMP_ID), resultSet.getString(EmployeeConst.PHOTO), resultSet.getString(EmployeeConst.FIRSTNAME),
                        resultSet.getString(EmployeeConst.SECONDNAME), Role.valueOf(resultSet.getString(RolesConst.ROLE)),
                        new AuthorizeInfo(resultSet.getString(AuthorizeConst.EMAIL), resultSet.getString(AuthorizeConst.PASSWORD)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }


}