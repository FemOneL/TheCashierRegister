package com.epam.cashierregister.services.DAO;

import com.epam.cashierregister.services.DAO.queries.Query;
import com.epam.cashierregister.services.consts.CategoryConst;
import com.epam.cashierregister.services.consts.GoodsConst;
import com.epam.cashierregister.services.consts.ProducerConst;
import com.epam.cashierregister.services.entities.goods.Category;
import com.epam.cashierregister.services.entities.goods.Goods;
import com.epam.cashierregister.services.entities.goods.Producer;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import com.epam.cashierregister.services.exeptions.GoodsExistInCheckException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for goods
 */
public class GoodsDAO extends DAO {

    public GoodsDAO() throws DatabaseException {}

    /**
     * search goods by id or model
     * @param searchBy id/model
     * @param getZeroNumber if needed goods with zero numbers
     * @return searched goods
     * @throws DatabaseException
     */
    public Goods searchGood(String searchBy, boolean getZeroNumber) throws DatabaseException {
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
            LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
            throw new DatabaseException(500);
        }
        return goods;
    }

    /**
     * delete current goods by id
     * @param goodsId
     * @throws GoodsExistInCheckException if current goods exist in check
     */
    public void deleteGoods(int goodsId) throws GoodsExistInCheckException {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(Query.DELETE);
            CallableStatement callableStatement = connection.prepareCall(Query.FOREIGN_KEY_CHECKS_1);
            callableStatement.executeUpdate();
            preparedStatement.setInt(1, goodsId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
            throw new GoodsExistInCheckException();
        }
    }

    /**
     * check if model exist in database
     * @param model
     * @return false if model exist
     * @throws DatabaseException
     */
    public boolean checkModel(String model) throws DatabaseException {
        boolean result = true;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(Query.SELECT_MODEL);
            preparedStatement.setString(1, model);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = false;
            }
        } catch (SQLException e) {
            LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
            throw new DatabaseException(500);
        }
        return result;
    }

    /**
     * update number in goods
     * @param id of target goods
     * @param newNumber new number of goods
     * @return true if number updated
     * @throws DatabaseException
     */
    public boolean updateNumber(int id, int newNumber) throws DatabaseException {
        int result;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(Query.UPDATE);
            preparedStatement.setInt(1, newNumber);
            preparedStatement.setInt(2, id);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
            throw new DatabaseException(500);
        }
        return result > 0;
    }

    /**
     * added new goods
     * @param goods
     * @throws DatabaseException
     */
    public void addGoods(Goods goods) throws DatabaseException {
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(Query.ADD_GOODS);
            preparedStatement.setString(1, goods.getModel());
            preparedStatement.setString(2, goods.getPhoto());
            preparedStatement.setInt(3, goods.getNumbers());
            preparedStatement.setBigDecimal(4, goods.getCost());
            preparedStatement.setString(5, goods.getCategory().getCategory());
            preparedStatement.setString(6, goods.getProducer().getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
            throw new DatabaseException(500);
        }
    }


    /**
     * get list of goods in current page
     * @param page
     * @param search or null
     * @return list of goods
     * @throws DatabaseException
     */
    public List<Goods> getGoods(int page, String search) throws DatabaseException {
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
            LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
            throw new DatabaseException(500);
        }
        return goods;
    }
}
