package com.epam.cashierregister.services.DAO;

import com.epam.cashierregister.services.consts.queries.Query;
import com.epam.cashierregister.services.consts.entityConsts.AuthorizeConst;
import com.epam.cashierregister.services.consts.entityConsts.EmployeeConst;
import com.epam.cashierregister.services.consts.entityConsts.RolesConst;
import com.epam.cashierregister.services.entities.employee.AuthorizeInfo;
import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.entities.employee.Role;
import com.epam.cashierregister.services.exeptions.DatabaseException;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Data Access Object for employees
 */
public class EmployeeDAO extends DAO {

    public EmployeeDAO() throws DatabaseException {}

    /**
     * delete current employee using transactions
     * @param employee which you need to delete
     * @return true if employee deleted
     * @throws DatabaseException
     */
    public boolean deleteEmployee(Employee employee) throws DatabaseException {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            CallableStatement callableStatement = connection.prepareCall(Query.FOREIGN_KEY_CHECKS_0);
            callableStatement.executeUpdate();
            PreparedStatement empStatement = connection.prepareStatement(Query.DELETE_EMPLOYEE);
            empStatement.setInt(1, employee.getId());
            PreparedStatement authStatement = connection.prepareStatement(Query.DELETE_AUTHORIZE);
            authStatement.setInt(1, employee.getAuthorize().getId());
            empStatement.executeUpdate();
            authStatement.executeUpdate();
            callableStatement = connection.prepareCall(Query.FOREIGN_KEY_CHECKS_1);
            callableStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                return false;
            } catch (SQLException ex) {
                LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
                throw new DatabaseException(500);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
                throw new DatabaseException(500);
            }
        }
        return true;
    }

    /**
     * adding new employee
     * @param employee
     * @return true if employee added
     * @throws DatabaseException
     */
    public boolean addEmployee(Employee employee) throws DatabaseException {
        int empId = 0;
        int authId = 0;
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            PreparedStatement authorizeStatement = connection.prepareStatement(Query.INSERT_AUTHORIZE, Statement.RETURN_GENERATED_KEYS);
            authorizeStatement.setString(1, employee.getAuthorize().getEmail());
            authorizeStatement.setString(2, employee.getAuthorize().getPassword());
            authorizeStatement.executeUpdate();
            PreparedStatement employeeStatement = connection.prepareStatement(Query.INSERT_EMPLOYEE, Statement.RETURN_GENERATED_KEYS);
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
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
                return false;
            } catch (SQLException ex) {
                LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
                throw new DatabaseException(500);
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
                throw new DatabaseException(500);
            }
        }
        return true;
    }

    public int getAuthorizeID(String email) throws DatabaseException {
        int id = 0;
        try (Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(Query.GET_AUTH_ID);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                id = resultSet.getInt(1);
            }
        }catch (SQLException e){
            LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
            throw new DatabaseException(500);
        }
        return id;
    }

    /**
     * updated user password
     * @param password
     * @param employee
     * @return true if password updated
     * @throws DatabaseException
     */
    public boolean updatePassword(String password, Employee employee) throws DatabaseException {
        try (Connection connection = getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(Query.UPDATE_PASSWORD);
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, employee.getAuthorize().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
            throw new DatabaseException(500);
        }
        return true;
    }

    /**
     * get employee by email
     * @param email
     * @return employee if exist or null
     * @throws DatabaseException
     */
    public Employee getEmployee(String email) throws DatabaseException {
        Employee foundEmployee = null;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(Query.SELECT_USER);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                foundEmployee = new Employee(resultSet.getInt(EmployeeConst.EMP_ID), resultSet.getString(EmployeeConst.PHOTO), resultSet.getString(EmployeeConst.FIRSTNAME),
                        resultSet.getString(EmployeeConst.SECONDNAME), Role.valueOf(resultSet.getString(RolesConst.ROLE)),
                        new AuthorizeInfo(resultSet.getString(AuthorizeConst.EMAIL), resultSet.getString(AuthorizeConst.PASSWORD)));
            }
        } catch (SQLException e) {
            LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
            throw new DatabaseException(500);
        }
        return foundEmployee;
    }

    /**
     * get set of employees on current page
     * @param page
     * @param search search string or null
     * @return list of employees
     * @throws DatabaseException
     */
    public Set<Employee> getEmployees(int page, String search) throws DatabaseException {
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
            LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
            throw new DatabaseException(500);
        }
        return employees;
    }

    /**
     * get set of roles
     * @return set of roles
     * @throws DatabaseException
     */
    public Set<Role> getRoles() throws DatabaseException {
        Set<Role> roles = new LinkedHashSet<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(Query.SELECT_ROLES);
            while (resultSet.next()) {
                roles.add(Role.valueOf(resultSet.getString(RolesConst.ROLE)));
            }
        } catch (SQLException e) {
            LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
            throw new DatabaseException(500);
        }
        return roles;
    }


    /**
     * change employee role
     * @param role new role
     * @param empId user id
     * @return true if role changed
     * @throws DatabaseException
     */
    public boolean changeRole(Role role, int empId) throws DatabaseException {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(Query.CHANGE_ROLE);
            preparedStatement.setString(1, role.name());
            preparedStatement.setInt(2, empId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
            throw new DatabaseException(500);
        }
        return true;
    }

    /**
     * get employee by id
     * @param id
     * @return employee if exist or null
     * @throws DatabaseException
     */
    public Employee getEmployee(int id) throws DatabaseException {
        Employee employee = null;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(Query.SELECT_EMPLOYEE);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee = new Employee(resultSet.getInt(EmployeeConst.EMP_ID), resultSet.getString(EmployeeConst.PHOTO), resultSet.getString(EmployeeConst.FIRSTNAME),
                        resultSet.getString(EmployeeConst.SECONDNAME), Role.valueOf(resultSet.getString(RolesConst.ROLE)),
                        new AuthorizeInfo(resultSet.getInt(AuthorizeConst.AUTHORIZE_ID), resultSet.getString(AuthorizeConst.EMAIL)));
            }
        } catch (SQLException e) {
            LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
            throw new DatabaseException(500);
        }
        return employee;
    }
}