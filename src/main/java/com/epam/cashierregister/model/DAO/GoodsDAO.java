package com.epam.cashierregister.model.DAO;

import com.epam.cashierregister.model.consts.CategoryConst;
import com.epam.cashierregister.model.consts.GoodsConst;
import com.epam.cashierregister.model.consts.ProducerConst;
import com.epam.cashierregister.model.entities.goods.Category;
import com.epam.cashierregister.model.entities.goods.Goods;
import com.epam.cashierregister.model.entities.goods.Producer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoodsDAO {
    private static GoodsDAO instance;
    private final PoolConnectionBuilder connectionBuilder;

    private GoodsDAO() {
        connectionBuilder = new PoolConnectionBuilder();
    }

    public static GoodsDAO getInstance() {
        if (instance == null) {
            instance = new GoodsDAO();
        }
        return instance;
    }

    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }

    public boolean updateNumber(int id, int newNumber){
        int result = 0;
        try (Connection connection = getConnection()) {
            String update = "UPDATE " + GoodsConst.TABLE_NAME +
                    " SET " + GoodsConst.NUMBERS + " = " + " ? " +
                    " WHERE " + GoodsConst.GOODS_ID + " = " + " ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setInt(1, newNumber);
            preparedStatement.setInt(2, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result > 0;
    }


    public List<Goods> getGoods(int page) {
        List<Goods> goods = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String selectGoods = "SELECT " + GoodsConst.GOODS_ID + ", " + GoodsConst.MODEL + ", " +
                    CategoryConst.CATEGORY + ", " + ProducerConst.NAME + ", " + GoodsConst.NUMBERS + ", " + GoodsConst.COST +
                    " FROM " + GoodsConst.TABLE_NAME +
                    " INNER JOIN " + CategoryConst.TABLE_NAME + " ON " + GoodsConst.TABLE_NAME + "."
                    + GoodsConst.CATEGORY_ID + " = " + CategoryConst.TABLE_NAME + "." + CategoryConst.CATEGORY_ID +
                    " INNER JOIN " + ProducerConst.TABLE_NAME + " ON " + GoodsConst.TABLE_NAME + "."
                    + GoodsConst.PRODUCER_ID + " = " + ProducerConst.TABLE_NAME + "." + ProducerConst.PRODUCER_ID;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectGoods);
            while (resultSet.next()) {
                goods.add(new Goods(resultSet.getInt(GoodsConst.GOODS_ID), resultSet.getString(GoodsConst.MODEL),
                        resultSet.getInt(GoodsConst.NUMBERS), resultSet.getBigDecimal(GoodsConst.COST),
                        new Category(resultSet.getString(CategoryConst.CATEGORY)), new Producer(resultSet.getString(ProducerConst.NAME))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goods;
    }
}
