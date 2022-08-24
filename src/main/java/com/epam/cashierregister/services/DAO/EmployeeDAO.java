package com.epam.cashierregister.services.DAO;

import com.epam.cashierregister.services.consts.AuthorizeConst;
import com.epam.cashierregister.services.consts.EmployeeConst;
import com.epam.cashierregister.services.consts.RolesConst;
import com.epam.cashierregister.services.entities.employee.AuthorizeInfo;
import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.entities.employee.Role;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class EmployeeDAO extends DAO {


    public boolean deleteEmployee(Employee employee){
        try (Connection connection = getConnection()) {
            String fkc0 = "SET FOREIGN_KEY_CHECKS=0";
            String fkc1 = "SET FOREIGN_KEY_CHECKS=1";
            String deleteEmp = "DELETE FROM " + EmployeeConst.TABLE_NAME + " WHERE " + EmployeeConst.EMP_ID + " = ?";
            String deleteAuth = "DELETE FROM " + AuthorizeConst.TABLE_NAME + " WHERE " + AuthorizeConst.AUTHORIZE_ID + " = ?";
            CallableStatement callableStatement = connection.prepareCall(fkc0);
            callableStatement.executeUpdate();
            PreparedStatement empStatement = connection.prepareStatement(deleteEmp);
            empStatement.setInt(1, employee.getId());
            PreparedStatement authStatement = connection.prepareStatement(deleteAuth);
            authStatement.setInt(1, employee.getAuthorize().getId());
            empStatement.executeUpdate();
            authStatement.executeUpdate();
            callableStatement = connection.prepareCall(fkc1);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean addEmployee(Employee employee) {
        int empId = 0;
        int authId = 0;
        try (Connection connection = getConnection()) {
            String insertAuthorize = "INSERT INTO " + AuthorizeConst.TABLE_NAME + " VALUES (default, ?, ?)";
            String insertEmployee = "INSERT INTO " + EmployeeConst.TABLE_NAME +
                    " VALUES (default, ?, ?, ?, (SELECT " + RolesConst.ROLE_ID + " FROM " + RolesConst.TABLE_NAME +
                    " WHERE " + RolesConst.ROLE + " = ?), " + "(SELECT " + AuthorizeConst.AUTHORIZE_ID +
                    " FROM " + AuthorizeConst.TABLE_NAME + " WHERE " + AuthorizeConst.EMAIL + " = ?))";
            PreparedStatement authorizeStatement = connection.prepareStatement(insertAuthorize, Statement.RETURN_GENERATED_KEYS);
            authorizeStatement.setString(1, employee.getAuthorize().getEmail());
            authorizeStatement.setString(2, employee.getAuthorize().getPassword());
            authorizeStatement.executeUpdate();
            PreparedStatement employeeStatement = connection.prepareStatement(insertEmployee, Statement.RETURN_GENERATED_KEYS);
            employeeStatement.setString(1, employee.getPhoto());
            employeeStatement.setString(2, employee.getFirstname());
            employeeStatement.setString(3, employee.getSecondname());
            employeeStatement.setString(4, employee.getRole().name());
            employeeStatement.setString(5, employee.getAuthorize().getEmail());
            employeeStatement.executeUpdate();
            ResultSet resultEmpSet = employeeStatement.getGeneratedKeys();
            if (resultEmpSet.next()){
                empId = resultEmpSet.getInt(1);
            }
            ResultSet resultAuthSet = authorizeStatement.getGeneratedKeys();
            if (resultAuthSet.next()){
                authId = resultAuthSet.getInt(1);
            }
            employee.setId(empId);
            employee.getAuthorize().setId(authId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean updatePassword(String password, Employee employee){
        try (Connection connection = getConnection()){
            String updatePassword = "UPDATE " + AuthorizeConst.TABLE_NAME + " SET "+ AuthorizeConst.PASSWORD + " = ?" +
                    " WHERE " + AuthorizeConst.AUTHORIZE_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updatePassword);
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, employee.getAuthorize().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

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

    public Set<Employee> getEmployees(int page, String search) {
        Set<Employee> employees = new LinkedHashSet<>();
        try (Connection connection = getConnection()) {
            String searchQuery = " WHERE " + EmployeeConst.FIRSTNAME + " LIKE '%" + search + "%' OR " +
                    EmployeeConst.SECONDNAME + " LIKE '%" + search + "%' OR " +
                    EmployeeConst.EMP_ID + " LIKE '%" + search + "%' OR " +
                    RolesConst.ROLE + " LIKE '%" + search + "%' OR " +
                    AuthorizeConst.EMAIL + " LIKE '%" + search + "%' ";
            String selectAllEmployees = "SELECT " + EmployeeConst.EMP_ID + ", " + EmployeeConst.PHOTO + ", " + EmployeeConst.FIRSTNAME + ", " + EmployeeConst.SECONDNAME
                    + ", " + RolesConst.ROLE + ", " + AuthorizeConst.EMAIL + ", " + AuthorizeConst.PASSWORD
                    + " FROM " + EmployeeConst.TABLE_NAME
                    + " INNER JOIN " + RolesConst.TABLE_NAME
                    + " ON " + EmployeeConst.TABLE_NAME + "." + EmployeeConst.ROLE_ID + " = " + RolesConst.TABLE_NAME + "." + RolesConst.ROLE_ID
                    + " INNER JOIN " + AuthorizeConst.TABLE_NAME
                    + " ON " + EmployeeConst.TABLE_NAME + "." + EmployeeConst.AUTHORIZE_ID + " = " + AuthorizeConst.TABLE_NAME + "." + AuthorizeConst.AUTHORIZE_ID
                    + (search != null ? searchQuery : " ") + " ORDER BY " + EmployeeConst.EMP_ID + " LIMIT ?, 1";
            PreparedStatement preparedStatement = connection.prepareStatement(selectAllEmployees);
            preparedStatement.setInt(1, page);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                employees.add(new Employee(resultSet.getInt(EmployeeConst.EMP_ID), resultSet.getString(EmployeeConst.PHOTO), resultSet.getString(EmployeeConst.FIRSTNAME),
                        resultSet.getString(EmployeeConst.SECONDNAME), Role.valueOf(resultSet.getString(RolesConst.ROLE)),
                        new AuthorizeInfo(resultSet.getString(AuthorizeConst.EMAIL), null)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public Set<Role> getRoles() {
        Set<Role> roles = new LinkedHashSet<>();
        try (Connection connection = getConnection()) {
            String selectRoles = "SELECT " + RolesConst.ROLE + " FROM " + RolesConst.TABLE_NAME;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectRoles);
            while (resultSet.next()) {
                roles.add(Role.valueOf(resultSet.getString(RolesConst.ROLE)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    public boolean changeRole(Role role, int empId) {
        try (Connection connection = getConnection()) {
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

    public Employee getEmployee(int id) {
        Employee employee = null;
        try (Connection connection = getConnection()) {
            String select = "SELECT " + EmployeeConst.EMP_ID + ", " + EmployeeConst.PHOTO + ", " + EmployeeConst.FIRSTNAME + ", " + EmployeeConst.SECONDNAME
                    + ", " + RolesConst.ROLE + ", " + AuthorizeConst.EMAIL + ", " + AuthorizeConst.AUTHORIZE_ID
                    + " FROM " + EmployeeConst.TABLE_NAME
                    + " INNER JOIN " + RolesConst.TABLE_NAME
                    + " ON " + EmployeeConst.TABLE_NAME + "." + EmployeeConst.ROLE_ID + " = " + RolesConst.TABLE_NAME + "." + RolesConst.ROLE_ID
                    + " INNER JOIN " + AuthorizeConst.TABLE_NAME
                    + " ON " + EmployeeConst.TABLE_NAME + "." + EmployeeConst.AUTHORIZE_ID + " = " + AuthorizeConst.TABLE_NAME + "." + AuthorizeConst.AUTHORIZE_ID
                    + " WHERE " + EmployeeConst.EMP_ID + " = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee = new Employee(resultSet.getInt(EmployeeConst.EMP_ID), resultSet.getString(EmployeeConst.PHOTO), resultSet.getString(EmployeeConst.FIRSTNAME),
                        resultSet.getString(EmployeeConst.SECONDNAME), Role.valueOf(resultSet.getString(RolesConst.ROLE)),
                        new AuthorizeInfo(resultSet.getInt(AuthorizeConst.AUTHORIZE_ID), resultSet.getString(AuthorizeConst.EMAIL)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }
}