package com.epam.cashierregister.services.DAO;

import com.epam.cashierregister.services.consts.CategoryConst;
import com.epam.cashierregister.services.consts.EmployeeConst;
import com.epam.cashierregister.services.consts.GoodsConst;
import com.epam.cashierregister.services.consts.ProducerConst;
import com.epam.cashierregister.services.entities.goods.Category;
import com.epam.cashierregister.services.entities.goods.Goods;
import com.epam.cashierregister.services.entities.goods.Producer;
import com.epam.cashierregister.services.exeptions.GoodsExistInCheckException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoodsDAO extends DAO {

    public GoodsDAO() {
        super();
    }


    public Goods searchGood(String searchBy, boolean getZeroNumber) {
        Goods goods = null;
        try (Connection connection = getConnection()) {
            String search = "SELECT " + GoodsConst.GOODS_ID + ", " + GoodsConst.PHOTO + ", " + GoodsConst.MODEL + ", " + GoodsConst.NUMBERS + ", " + GoodsConst.COST +
                    ", " + CategoryConst.CATEGORY + ", " + ProducerConst.NAME +
                    " FROM " + GoodsConst.TABLE_NAME +
                    " INNER JOIN " + CategoryConst.TABLE_NAME +
                    " ON " + GoodsConst.TABLE_NAME + "." + GoodsConst.CATEGORY_ID + " = " + CategoryConst.TABLE_NAME + "." + CategoryConst.CATEGORY_ID +
                    " INNER JOIN " + ProducerConst.TABLE_NAME +
                    " ON " + GoodsConst.TABLE_NAME + "." + GoodsConst.PRODUCER_ID + " = " + ProducerConst.TABLE_NAME + "." + ProducerConst.PRODUCER_ID +
                    " WHERE (" +
                    GoodsConst.MODEL + " = ? OR " + GoodsConst.GOODS_ID + " = ?)" + (!getZeroNumber ? " AND " + GoodsConst.NUMBERS + " > 0" : "");
            PreparedStatement preparedStatement = connection.prepareStatement(search);
            preparedStatement.setString(1, searchBy);
            preparedStatement.setString(2, searchBy);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                goods = new Goods(resultSet.getInt(GoodsConst.GOODS_ID), resultSet.getString(GoodsConst.MODEL),
                        resultSet.getString(GoodsConst.PHOTO), resultSet.getInt(GoodsConst.NUMBERS), resultSet.getBigDecimal(GoodsConst.COST),
                        new Category(resultSet.getString(CategoryConst.CATEGORY)), new Producer(resultSet.getString(ProducerConst.NAME)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goods;
    }

    public void deleteGoods(int goodsId) throws GoodsExistInCheckException {
        try (Connection connection = getConnection()) {
            String fkc = "SET FOREIGN_KEY_CHECKS=1";
            String delete = "DELETE FROM " + GoodsConst.TABLE_NAME + " WHERE " + GoodsConst.GOODS_ID + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            CallableStatement callableStatement = connection.prepareCall(fkc);
            callableStatement.executeUpdate();
            preparedStatement.setInt(1, goodsId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new GoodsExistInCheckException();
        }
    }

    public boolean checkModel(String model) {
        boolean result = true;
        try (Connection connection = getConnection()) {
            String selectModel = "SELECT " + GoodsConst.MODEL + " FROM " + GoodsConst.TABLE_NAME +
                    " WHERE " + GoodsConst.MODEL + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(selectModel);
            preparedStatement.setString(1, model);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean updateNumber(int id, int newNumber) {
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

    public void addGoods(Goods goods) {
        try (Connection connection = getConnection()) {
            String insertGoods = "INSERT INTO " + GoodsConst.TABLE_NAME +
                    " VALUES (default, ?, ?, ?, ?, " +
                    "(SELECT " + CategoryConst.CATEGORY_ID + " FROM " + CategoryConst.TABLE_NAME
                    + " WHERE " + CategoryConst.CATEGORY + " = ?), " +
                    "(SELECT " + ProducerConst.PRODUCER_ID + " FROM " + ProducerConst.TABLE_NAME
                    + " WHERE " + ProducerConst.NAME + " = ?))";
            PreparedStatement preparedStatement = connection.prepareStatement(insertGoods);
            preparedStatement.setString(1, goods.getModel());
            preparedStatement.setString(2, goods.getPhoto());
            preparedStatement.setInt(3, goods.getNumbers());
            preparedStatement.setBigDecimal(4, goods.getCost());
            preparedStatement.setString(5, goods.getCategory().getCategory());
            preparedStatement.setString(6, goods.getProducer().getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Goods> getGoods(int page, String search) {
        List<Goods> goods = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String searchQuery = " WHERE " + GoodsConst.GOODS_ID + " LIKE '%" + search + "%' OR " +
                    GoodsConst.MODEL + " LIKE '%" + search + "%' OR " +
                    CategoryConst.CATEGORY + " LIKE '%" + search + "%' OR " +
                    ProducerConst.NAME + " LIKE '%" + search + "%' ";
            String selectGoods = "SELECT " + GoodsConst.GOODS_ID + ", " + GoodsConst.PHOTO + ", " + GoodsConst.MODEL + ", " +
                    CategoryConst.CATEGORY + ", " + ProducerConst.NAME + ", " + GoodsConst.NUMBERS + ", " + GoodsConst.COST +
                    " FROM " + GoodsConst.TABLE_NAME +
                    " INNER JOIN " + CategoryConst.TABLE_NAME + " ON " + GoodsConst.TABLE_NAME + "."
                    + GoodsConst.CATEGORY_ID + " = " + CategoryConst.TABLE_NAME + "." + CategoryConst.CATEGORY_ID +
                    " INNER JOIN " + ProducerConst.TABLE_NAME + " ON " + GoodsConst.TABLE_NAME + "."
                    + GoodsConst.PRODUCER_ID + " = " + ProducerConst.TABLE_NAME + "." + ProducerConst.PRODUCER_ID +
                    (search != null ? searchQuery : " ")  + " ORDER BY " + GoodsConst.GOODS_ID + " LIMIT " + page + ", 4";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectGoods);
            while (resultSet.next()) {
                goods.add(new Goods(resultSet.getInt(GoodsConst.GOODS_ID), resultSet.getString(GoodsConst.MODEL),
                        resultSet.getString(GoodsConst.PHOTO), resultSet.getInt(GoodsConst.NUMBERS), resultSet.getBigDecimal(GoodsConst.COST),
                        new Category(resultSet.getString(CategoryConst.CATEGORY)), new Producer(resultSet.getString(ProducerConst.NAME))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goods;
    }
}
