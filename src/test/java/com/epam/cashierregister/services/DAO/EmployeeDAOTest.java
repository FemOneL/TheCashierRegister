package com.epam.cashierregister.services.DAO;

import com.epam.cashierregister.services.DAO.connection.DBHandler;
import com.epam.cashierregister.services.DAO.connection.TestDbHandler;
import com.epam.cashierregister.services.entities.employee.AuthorizeInfo;
import com.epam.cashierregister.services.entities.employee.Employee;
import com.epam.cashierregister.services.entities.employee.Role;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeDAOTest {
    private static DBHandler testDBHandler;
    private static EmployeeDAO employeeDAO;
    private static MockedStatic<DBHandler> mockStat;

    @BeforeAll
    static void beforeAll() {
        mockStat = mockStatic(DBHandler.class);
    }

    @AfterAll
    static void afterAll() {
        mockStat.close();
        testDBHandler = null;
        employeeDAO = null;
    }

    @BeforeEach
    void setUp() throws DatabaseException {
        testDBHandler = new TestDbHandler();
        when(DBHandler.getConBuilder()).thenReturn(testDBHandler);
        employeeDAO = new EmployeeDAO();
        try (Connection connection = testDBHandler.getConnection()) {
            ScriptRunner sr = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new FileReader("src/test/java/com/epam/cashierregister/services/DAO/scriptsForTest/cashregisterScript.sql"));
            sr.runScript(reader);
        } catch (SQLException | FileNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Test
    void testDeleteEmployee() throws DatabaseException {
        AuthorizeInfo authorizeInfo = mock(AuthorizeInfo.class);
        Employee employee = mock(Employee.class);
        when(employee.getId()).thenReturn(1);
        when(employee.getAuthorize()).thenReturn(authorizeInfo);
        when(authorizeInfo.getId()).thenReturn(1);
        assertTrue(employeeDAO.deleteEmployee(employee));
        assertFalse(checkEmployee(1, 1));
    }

    @Test
    void test500() throws DatabaseException {
        Employee employee = mock(Employee.class);
        String password = "fsdfadsadsdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasd"
        + "fsdfadsadsdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasddasdasdasdasdasd";
        AuthorizeInfo authorizeInfo = mock(AuthorizeInfo.class);
        when(employee.getAuthorize()).thenReturn(authorizeInfo);
        when(authorizeInfo.getId()).thenReturn(1);
        DatabaseException e = Assertions.assertThrows(DatabaseException.class, ()-> employeeDAO.updatePassword(password, employee));
        assertEquals(500, e.getErrorCode());
    }

    @Test
    void testAddEmployee() throws DatabaseException {
        Employee employee = new Employee(5, "usersPhotos/nonuser.jpg", "Stepan", "Giga",
                Role.CASHIER, new AuthorizeInfo("sgiga@gmail.com", "stepa123"));
        assertTrue(employeeDAO.addEmployee(employee));
        assertTrue(checkEmployee(employee.getId(), employee.getAuthorize().getId()));
    }

    @Test
    void testUpdatePassword() {
        AuthorizeInfo authorizeInfo = mock(AuthorizeInfo.class);
        Employee employee = mock(Employee.class);
        when(employee.getAuthorize()).thenReturn(authorizeInfo);
        when(authorizeInfo.getId()).thenReturn(1);
        try {
            assertTrue(employeeDAO.updatePassword("testPassword", employee));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetEmployeeByEmail() throws DatabaseException {
        assertEquals(1, employeeDAO.getEmployee("tfemyak@gmail.com").getId());
    }

    @Test
    void getEmployees() throws DatabaseException {
        assertEquals(1, employeeDAO.getEmployees(0, null).size());
    }

    @Test
    void getRoles() throws DatabaseException {
        assertEquals(4, employeeDAO.getRoles().size());
    }

    @Test
    void changeRole() throws DatabaseException {
        assertEquals(Role.CASHIER, employeeDAO.getEmployee(1).getRole());
        employeeDAO.changeRole(Role.ADMIN, 1);
        assertEquals(Role.ADMIN, employeeDAO.getEmployee(1).getRole());
    }

    @Test
    void testGetEmployee() throws DatabaseException {
        assertEquals("tfemyak@gmail.com", employeeDAO.getEmployee(1).getAuthorize().getEmail());
    }

    public boolean checkEmployee(int id, int authorizeId){
        try (Connection connection = testDBHandler.getConnection()) {
            String selectFromEmployee = "SELECT * FROM employee WHERE emp_id = ?";
            String selectFromAuthorize = "SELECT * FROM authorize WHERE authorize_id = ?";
            PreparedStatement statement = connection.prepareStatement(selectFromEmployee);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            statement = connection.prepareStatement(selectFromAuthorize);
            statement.setInt(1, authorizeId);
            ResultSet rs2 = statement.executeQuery();
            return rs.next() && rs2.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}