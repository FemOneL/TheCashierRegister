package com.epam.cashierregister.services.DAO;

import com.epam.cashierregister.services.consts.AuthorizeConst;
import com.epam.cashierregister.services.consts.EmployeeConst;
import com.epam.cashierregister.services.consts.RolesConst;
import com.epam.cashierregister.services.entities.employee.AuthorizeInfo;
import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.entities.employee.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class EmployeeDAO extends DAO {

    public Employee getEmployee(String email) {
        Employee foundEmployee = null;
        try (Connection connection = getConnection()) {
            String selectUser = "SELECT " + EmployeeConst.EMP_ID + ", " + EmployeeConst.PHOTO + ", " + EmployeeConst.FIRSTNAME + ", " + EmployeeConst.SECONDNAME
                    + ", " + RolesConst.ROLE + ", " + AuthorizeConst.EMAIL + ", " + AuthorizeConst.PASSWORD
                    + " FROM " + EmployeeConst.TABLE_NAME
                    + " INNER JOIN " + RolesConst.TABLE_NAME
                    + " ON " + EmployeeConst.TABLE_NAME + "." + EmployeeConst.ROLE_ID + " = " + RolesConst.TABLE_NAME + "." + RolesConst.ROLE_ID
                    + " INNER JOIN " + AuthorizeConst.TABLE_NAME
                    + " ON " + EmployeeConst.TABLE_NAME + "." + EmployeeConst.AUTHORIZE_ID + " = " + AuthorizeConst.TABLE_NAME + "." + AuthorizeConst.AUTHORIZE_ID
                    + " WHERE " + AuthorizeConst.TABLE_NAME + "." + AuthorizeConst.EMAIL + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectUser);
            preparedStatement.setString(1, email);
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

    public Set<Employee> getEmployees(int page){
        Set<Employee> employees = new LinkedHashSet<>();
        try (Connection connection = getConnection()){
            String selectAllEmployees = "SELECT " + EmployeeConst.EMP_ID + ", " + EmployeeConst.PHOTO + ", " + EmployeeConst.FIRSTNAME + ", " + EmployeeConst.SECONDNAME
                    + ", " + RolesConst.ROLE + ", " + AuthorizeConst.EMAIL + ", " + AuthorizeConst.PASSWORD
                    + " FROM " + EmployeeConst.TABLE_NAME
                    + " INNER JOIN " + RolesConst.TABLE_NAME
                    + " ON " + EmployeeConst.TABLE_NAME + "." + EmployeeConst.ROLE_ID + " = " + RolesConst.TABLE_NAME + "." + RolesConst.ROLE_ID
                    + " INNER JOIN " + AuthorizeConst.TABLE_NAME
                    + " ON " + EmployeeConst.TABLE_NAME + "." + EmployeeConst.AUTHORIZE_ID + " = " + AuthorizeConst.TABLE_NAME + "." + AuthorizeConst.AUTHORIZE_ID
                    + " ORDER BY " + EmployeeConst.EMP_ID + " LIMIT ?, 1";
            PreparedStatement preparedStatement = connection.prepareStatement(selectAllEmployees);
            preparedStatement.setInt(1, page);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                employees.add(new Employee(resultSet.getInt(EmployeeConst.EMP_ID), resultSet.getString(EmployeeConst.PHOTO), resultSet.getString(EmployeeConst.FIRSTNAME),
                        resultSet.getString(EmployeeConst.SECONDNAME), Role.valueOf(resultSet.getString(RolesConst.ROLE)),
                        new AuthorizeInfo(resultSet.getString(AuthorizeConst.EMAIL), null)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public Set<Role> getRoles(){
        Set<Role> roles = new LinkedHashSet<>();
        try (Connection connection = getConnection()){
            String selectRoles = "SELECT " + RolesConst.ROLE + " FROM " + RolesConst.TABLE_NAME;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectRoles);
            while (resultSet.next()){
                roles.add(Role.valueOf(resultSet.getString(RolesConst.ROLE)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    public boolean changeRole(Role role, int empId){
        try(Connection connection = getConnection()) {
            String change = "UPDATE " + EmployeeConst.TABLE_NAME + " SET " +
                    EmployeeConst.ROLE_ID + " = (SELECT " + RolesConst.ROLE_ID + " FROM " +
                    RolesConst.TABLE_NAME + " WHERE " + RolesConst.ROLE + " = ?) WHERE " + EmployeeConst.EMP_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(change);
            preparedStatement.setString(1, role.name());
            preparedStatement.setInt(2, empId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
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
                    + " WHERE " + EmployeeConst.EMP_ID + " = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                employee = new Employee(resultSet.getInt(EmployeeConst.EMP_ID), resultSet.getString(EmployeeConst.PHOTO), resultSet.getString(EmployeeConst.FIRSTNAME),
                        resultSet.getString(EmployeeConst.SECONDNAME), Role.valueOf(resultSet.getString(RolesConst.ROLE)),
                        new AuthorizeInfo(resultSet.getString(AuthorizeConst.EMAIL), null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }
}