package com.epam.cashierregister.services.DAO;

import com.epam.cashierregister.services.connection.PoolConnectionBuilder;
import com.epam.cashierregister.services.consts.AuthorizeConst;
import com.epam.cashierregister.services.consts.EmployeeConst;
import com.epam.cashierregister.services.consts.RolesConst;
import com.epam.cashierregister.services.entities.employee.AuthorizeInfo;
import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.entities.employee.Role;

import java.sql.*;

public class EmployeeDAO {
    private static EmployeeDAO instance;
    private final PoolConnectionBuilder connectionBuilder;

    private EmployeeDAO() {
        connectionBuilder = new PoolConnectionBuilder();
    }

    public static EmployeeDAO getInstance(){
        if (instance == null){
            instance = new EmployeeDAO();
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }

    public Employee getEmployee(String email, String password){
        Employee foundEmployee = null;
        try (Connection connection = getConnection()){
            String selectUser = "SELECT " + EmployeeConst.EMP_ID +", " + EmployeeConst.FIRSTNAME + ", " + EmployeeConst.SECONDNAME
                    + ", " + RolesConst.ROLE + ", " + AuthorizeConst.EMAIL + ", " + AuthorizeConst.PASSWORD
                    + " FROM " + EmployeeConst.TABLE_NAME
                    + " INNER JOIN " + RolesConst.TABLE_NAME
                    + " ON " + EmployeeConst.TABLE_NAME + "." + EmployeeConst.ROLE_ID + " = " + RolesConst.TABLE_NAME + "." + RolesConst.ROLE_ID
                    + " INNER JOIN " + AuthorizeConst.TABLE_NAME
                    + " ON " + EmployeeConst.TABLE_NAME + "." + EmployeeConst.AUTHORIZE_ID + " = " + AuthorizeConst.TABLE_NAME + "." + AuthorizeConst.AUTHORIZE_ID
                    + " WHERE "+ AuthorizeConst.TABLE_NAME + "." + AuthorizeConst.EMAIL + " = (?) "
                    + " AND " + AuthorizeConst.TABLE_NAME + "." + AuthorizeConst.PASSWORD  + " = (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(selectUser);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                foundEmployee = new Employee(resultSet.getInt(EmployeeConst.EMP_ID), resultSet.getString(EmployeeConst.FIRSTNAME),
                        resultSet.getString(EmployeeConst.SECONDNAME), Role.valueOf(resultSet.getString(RolesConst.ROLE)),
                        new AuthorizeInfo(resultSet.getString(AuthorizeConst.EMAIL), resultSet.getString(AuthorizeConst.PASSWORD)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundEmployee;
    }




}