package com.epam.cashierregister.services.DAO;

import com.epam.cashierregister.services.consts.ReportConst;
import com.epam.cashierregister.services.consts.ReturnConst;
import com.epam.cashierregister.services.consts.SellingConst;
import com.epam.cashierregister.services.entities.report.Report;
import com.epam.cashierregister.services.entities.report.Return;
import com.epam.cashierregister.services.entities.report.Selling;

import java.sql.*;

public class ReportDAO extends DAO{

    public int writeSelling(Selling selling){
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

    public int writeReturned(Return returned){
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


    public boolean writeReport(Report returned){
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
}
