package com.epam.cashierregister.services.dao;

import com.epam.cashierregister.services.consts.queries.Query;
import com.epam.cashierregister.services.consts.entityconsts.ReportConst;
import com.epam.cashierregister.services.consts.entityconsts.ReturnConst;
import com.epam.cashierregister.services.consts.entityconsts.SellingConst;
import com.epam.cashierregister.services.entities.report.Report;
import com.epam.cashierregister.services.entities.report.Return;
import com.epam.cashierregister.services.entities.report.Selling;
import com.epam.cashierregister.services.exeptions.DatabaseException;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Data Access Object for reports
 */
public class ReportDAO extends DAO {
    private static final String DATABASE_ERROR = "Database was thrown SQLException with message:";

    public ReportDAO() throws DatabaseException {
    }

    /**
     * @return selling id
     */
    public int writeSelling(Selling selling) throws DatabaseException {
        int id = 0;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(Query.INSERT_SELLING, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, selling.getNumberOfSellingChecks());
            preparedStatement.setBigDecimal(2, selling.getSellingSum());
            preparedStatement.executeUpdate();
            ResultSet key = preparedStatement.getGeneratedKeys();
            if (key.next()) {
                id = key.getInt(1);
            }
        } catch (SQLException e) {
            LOG.fatal("{} {} {}", DATABASE_ERROR, e.getErrorCode() , e.getMessage());
            throw new DatabaseException(500);
        }
        return id;
    }


    /**
     * @return returned id
     */
    public int writeReturned(Return returned) throws DatabaseException {
        int id = 0;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(Query.INSERT_RETURNED, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, returned.getNumberOfReturningGoods());
            preparedStatement.setBigDecimal(2, returned.getReturnedSum());
            preparedStatement.executeUpdate();
            ResultSet key = preparedStatement.getGeneratedKeys();
            if (key.next()) {
                id = key.getInt(1);
            }
        } catch (SQLException e) {
            LOG.fatal("{} {} {}", DATABASE_ERROR, e.getErrorCode() , e.getMessage());
            throw new DatabaseException(500);
        }
        return id;
    }


    /**
     */
    public void writeReport(Report report) throws DatabaseException {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(Query.INSERT_REPORT);
            preparedStatement.setInt(1, report.getSelling().getId());
            preparedStatement.setInt(2, report.getReturned().getId());
            preparedStatement.setTimestamp(3, report.getCreatedDate());
            preparedStatement.setTimestamp(4, report.getDate());
            preparedStatement.setBigDecimal(5, report.getProfit());
            preparedStatement.setInt(6, report.getSeniorCashier().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.fatal("{} {} {}", DATABASE_ERROR, e.getErrorCode() , e.getMessage());
            throw new DatabaseException(500);
        }
    }

    /**
     * @return set of reports
     */
    public Set<Report> getReports(int page, String search) throws DatabaseException {
        Set<Report> reports = new LinkedHashSet<>();
        try (Connection connection = getConnection()) {
            String searchQuery = " WHERE " + ReportConst.DATE + " LIKE '%" + search + "%' ";
            String getReports = "SELECT " + ReportConst.DATE + ", " + SellingConst.TABLE_NAME + "." + SellingConst.SUM +
                    ", " + ReturnConst.TABLE_NAME + "." + ReturnConst.SUM + ", " + ReportConst.PROFIT +
                    " FROM " + ReportConst.TABLE_NAME +
                    " INNER JOIN " + SellingConst.TABLE_NAME + " ON " + ReportConst.TABLE_NAME + "." + ReportConst.SELLING_ID +
                    " = " + SellingConst.TABLE_NAME + "." + SellingConst.SELLING_ID +
                    " INNER JOIN " + ReturnConst.TABLE_NAME + " ON " + ReportConst.TABLE_NAME + "." + ReportConst.RETURN_ID +
                    " = " + ReturnConst.TABLE_NAME + "." + ReturnConst.RETURN_ID +
                    (search != null ? searchQuery : " ") + " ORDER BY " + ReportConst.DATE + " LIMIT ?, 5";
            PreparedStatement statement = connection.prepareStatement(getReports);
            statement.setInt(1, page);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                reports.add(new Report(resultSet.getTimestamp(ReportConst.DATE),
                        new Selling(0, resultSet.getBigDecimal(SellingConst.SUM)),
                        new Return(0, resultSet.getBigDecimal(ReturnConst.SUM)),
                        resultSet.getBigDecimal(ReportConst.PROFIT)));
            }
        } catch (SQLException e) {
            LOG.fatal("{} {} {}", DATABASE_ERROR, e.getErrorCode() , e.getMessage());
            throw new DatabaseException(500);
        }
        return reports;
    }
}
