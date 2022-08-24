package com.epam.cashierregister.services.DAO;

import com.epam.cashierregister.services.consts.ReportConst;
import com.epam.cashierregister.services.consts.ReturnConst;
import com.epam.cashierregister.services.consts.SellingConst;
import com.epam.cashierregister.services.entities.report.Report;
import com.epam.cashierregister.services.entities.report.Return;
import com.epam.cashierregister.services.entities.report.Selling;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class ReportDAO extends DAO {

    public int writeSelling(Selling selling) {
        int id = 0;
        try (Connection connection = getConnection()) {
            String insertSelling = "INSERT INTO " + SellingConst.TABLE_NAME + " VALUES (default, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSelling, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, selling.getNumberOfSellingChecks());
            preparedStatement.setBigDecimal(2, selling.getSellingSum());
            preparedStatement.executeUpdate();
            ResultSet key = preparedStatement.getGeneratedKeys();
            if (key.next()) {
                id = key.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int writeReturned(Return returned) {
        int id = 0;
        try (Connection connection = getConnection()) {
            String insertReturned = "INSERT INTO " + ReturnConst.TABLE_NAME + " VALUES (default, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertReturned, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, returned.getNumberOfReturningGoods());
            preparedStatement.setBigDecimal(2, returned.getReturnedSum());
            preparedStatement.executeUpdate();
            ResultSet key = preparedStatement.getGeneratedKeys();
            if (key.next()) {
                id = key.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


    public boolean writeReport(Report returned) {
        try (Connection connection = getConnection()) {
            String insertReport = "INSERT INTO " + ReportConst.TABLE_NAME + " VALUES (default, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertReport);
            preparedStatement.setInt(1, returned.getSelling().getId());
            preparedStatement.setInt(2, returned.getReturned().getId());
            preparedStatement.setTimestamp(3, returned.getCreatedDate());
            preparedStatement.setTimestamp(4, returned.getDate());
            preparedStatement.setBigDecimal(5, returned.getProfit());
            preparedStatement.setInt(6, returned.getSeniorCashier().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Set<Report> getReports(int page, String search) {
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
            e.printStackTrace();
        }
        return reports;
    }
}
