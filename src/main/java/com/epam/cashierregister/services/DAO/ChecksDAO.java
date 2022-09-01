package com.epam.cashierregister.services.DAO;

import com.epam.cashierregister.services.consts.queries.Query;
import com.epam.cashierregister.services.consts.entityConsts.CheckConst;
import com.epam.cashierregister.services.consts.entityConsts.CheckHasGoodsConst;
import com.epam.cashierregister.services.entities.check.Check;
import com.epam.cashierregister.services.entities.goods.Goods;
import com.epam.cashierregister.services.exeptions.DatabaseException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Data Access Object for checks
 */
public class ChecksDAO extends DAO {

    public ChecksDAO() throws DatabaseException {
    }

    /**
     * delete current check using transactions
     * @param check which you need to delete
     * @return true if check deleted
     * @throws DatabaseException
     */
    public boolean deleteCheck(Check check) throws DatabaseException {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            PreparedStatement updateGoodsStatement = connection.prepareStatement(Query.UPDATE_GOODS_NUMBER);
            PreparedStatement deleteCheckHasGoodsStatement = connection.prepareStatement(Query.DELETE_FROM_CHECK_HAS_GOODS);
            PreparedStatement deleteCheckStatement = connection.prepareStatement(Query.DELETE_CHECK);
            for (Goods goods : check.getGoodsSet()) {
                updateGoodsStatement.setInt(1, goods.getId());
                updateGoodsStatement.setInt(2, check.getId());
                updateGoodsStatement.setInt(3, goods.getId());
                updateGoodsStatement.executeUpdate();
            }
            deleteCheckHasGoodsStatement.setInt(1, check.getId());
            deleteCheckHasGoodsStatement.executeUpdate();
            deleteCheckStatement.setInt(1, check.getId());
            deleteCheckStatement.executeUpdate();

            connection.commit();
            LOG.info("Transaction success!!");
        } catch (SQLException e) {
            try {
                connection.rollback();
                LOG.error("Transaction failed!!");
                throw new DatabaseException(500);
            } catch (SQLException ex) {
                LOG.fatal("Database was thrown SQLException with message: {} {}", ex.getErrorCode() , ex.getMessage());
                throw new DatabaseException(500);
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
                    throw new DatabaseException(500);
                }
            }
        }
        return true;
    }

    /**
     * delete specific goods in check using transactions
     * @param check which contains target goods
     * @param goodsId id of target goods
     * @return true if goods deleted
     * @throws DatabaseException
     */
    public boolean deleteSpecificGoodsInCheck(Check check, int goodsId) throws DatabaseException {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement removeCostStatement = connection.prepareStatement(Query.UPDATE_TOTAL_COST);
            removeCostStatement.setInt(1, check.getId());
            removeCostStatement.setInt(2, goodsId);
            removeCostStatement.setInt(3, goodsId);
            removeCostStatement.setInt(4, check.getId());
            removeCostStatement.executeUpdate();

            PreparedStatement returnGoodsStatement = connection.prepareStatement(Query.RETURN_GOODS_IN_WAREHOUSE);
            returnGoodsStatement.setInt(1, goodsId);
            returnGoodsStatement.setInt(2, check.getId());
            returnGoodsStatement.setInt(3, goodsId);
            returnGoodsStatement.executeUpdate();

            PreparedStatement deleteGoodsFromCheckStatement = connection.prepareStatement(Query.DELETE_GOODS);
            deleteGoodsFromCheckStatement.setInt(1, goodsId);
            deleteGoodsFromCheckStatement.setInt(2, check.getId());
            deleteGoodsFromCheckStatement.executeUpdate();

            connection.commit();
            LOG.info("Transaction success!!");
        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new DatabaseException(500);
            } catch (SQLException ex) {
                LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
                throw new DatabaseException(500);
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
                throw new DatabaseException(500);
            }
        }
        return true;
    }

     /**
     * delete specific goods in check using transactions
     * @param check which contains target goods
     * @param goodsId id of target goods
     * @param diff different which must be deleted
     * @return true if goods deleted
     * @throws DatabaseException
     */
    public boolean deleteSpecificGoodsInCheck(Check check, int goodsId, int diff) throws DatabaseException {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement updateTotalCostStatement = connection.prepareStatement(Query.UPDATE_TOTAL_COST);
            updateTotalCostStatement.setInt(1, check.getId());
            updateTotalCostStatement.setInt(2, goodsId);
            updateTotalCostStatement.setInt(3, goodsId);
            updateTotalCostStatement.setInt(4, check.getId());
            updateTotalCostStatement.executeUpdate();

            PreparedStatement updateCheckStatement = connection.prepareStatement(Query.UPDATE_CHECK);
            updateCheckStatement.setInt(1, diff);
            updateCheckStatement.setInt(2, goodsId);
            updateCheckStatement.setInt(3, check.getId());
            updateCheckStatement.executeUpdate();

            PreparedStatement updateTotalCostAgainStatement = connection.prepareStatement(Query.UPDATE_TOTAL_COST_AGAIN);
            updateTotalCostAgainStatement.setInt(1, check.getId());
            updateTotalCostAgainStatement.setInt(2, goodsId);
            updateTotalCostAgainStatement.setInt(3, goodsId);
            updateTotalCostAgainStatement.setInt(4, check.getId());
            updateTotalCostAgainStatement.executeUpdate();

            PreparedStatement updateGoodsStatement = connection.prepareStatement(Query.RETURN_GOODS);
            updateGoodsStatement.setInt(1, diff);
            updateGoodsStatement.setInt(2, goodsId);
            updateGoodsStatement.executeUpdate();

            connection.commit();
            LOG.info("Transaction success!!");
        } catch (SQLException e) {
            try {
                connection.rollback();
                LOG.error("Transaction failed!!");
                throw new DatabaseException(500);
            } catch (SQLException ex) {
                LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
                throw new DatabaseException(500);
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
                throw new DatabaseException(500);
            }
        }
        return true;
    }

    /**
     * get check and all goods in check
     * @param id of check
     * @return check with goods
     * @throws DatabaseException
     */
    public Check getCheckWithGoods(int id) throws DatabaseException {
        Check check = null;
        Goods goods;
        EmployeeDAO employeeDAO = new EmployeeDAO();
        GoodsDAO goodsDAO = new GoodsDAO();
        try (Connection connection = getConnection()) {
            PreparedStatement checkStatement = connection.prepareStatement(Query.SELECT_CHECK);
            PreparedStatement goodsStatement = connection.prepareStatement(Query.SELECT_GOODS);
            goodsStatement.setInt(1, id);
            checkStatement.setInt(1, id);
            ResultSet goodsSet = goodsStatement.executeQuery();
            ResultSet checkSet = checkStatement.executeQuery();
            if (checkSet.next()) {
                check = new Check(checkSet.getInt(CheckConst.CHECK_ID),
                        employeeDAO.getEmployee(checkSet.getInt(CheckConst.EMP_ID)),
                        checkSet.getTimestamp(CheckConst.TIME), checkSet.getBigDecimal(CheckConst.TOTAL_COST));
            }
            while (goodsSet.next()) {
                goods = goodsDAO.searchGood(goodsSet.getString(CheckHasGoodsConst.GOODS_ID), true);
                goods.setTotalNumber(goods.getNumbers());
                goods.setNumbers(goodsSet.getInt(CheckHasGoodsConst.NUMBER_OF_GOODS));
                goods.setTotalCost(goods.getCost().multiply(new BigDecimal(goods.getNumbers())));
                check.getGoodsSet().add(goods);
            }
        } catch (SQLException e) {
            LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
            throw new DatabaseException(500);
        }
        return check;
    }

    /**
     * get list of checks
     * @param page
     * @param search search string or null
     * @return list of checks
     * @throws DatabaseException
     */
    public List<Check> getChecks(int page, String search) throws DatabaseException {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<Check> checks = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String searchQuery = " WHERE " + CheckConst.TIME + " LIKE '%" + search + "%' ";
            String selectFromCheck = "SELECT * FROM " + CheckConst.TABLE_NAME +
                    (search != null ? searchQuery : " ") +
                    " ORDER BY " + CheckConst.TIME +
                    " DESC LIMIT ?, 21";
            PreparedStatement statement = connection.prepareStatement(selectFromCheck);
            statement.setInt(1, page);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                checks.add(new Check(resultSet.getInt(CheckConst.CHECK_ID),
                        employeeDAO.getEmployee(resultSet.getInt(CheckConst.EMP_ID)),
                        resultSet.getTimestamp(CheckConst.TIME), resultSet.getBigDecimal(CheckConst.TOTAL_COST)));
            }
        } catch (SQLException e) {
            LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
            throw new DatabaseException(500);
        }
        return checks;
    }


    /**
     * added set of goods in check by transaction
     * @param goodsSet
     * @param check
     * @return true if goods added
     * @throws DatabaseException
     */
    public boolean addGoodsInCheck(Set<Goods> goodsSet, Check check) throws DatabaseException {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            CallableStatement statement = connection.prepareCall(Query.FOREIGN_KEY_CHECKS_0);
            statement.executeUpdate();
            PreparedStatement insertStatement = connection.prepareStatement(Query.INSERT_GOODS);
            PreparedStatement updateStatement = connection.prepareStatement(Query.UPDATE_GOODS);
            for (Goods goods : goodsSet) {
                insertStatement.setInt(1, check.getId());
                insertStatement.setInt(2, goods.getId());
                insertStatement.setInt(3, goods.getNumbers());
                updateStatement.setInt(1, (goods.getTotalNumber() - goods.getNumbers()));
                updateStatement.setInt(2, goods.getId());
                insertStatement.executeUpdate();
                updateStatement.executeUpdate();
            }
            statement = connection.prepareCall(Query.FOREIGN_KEY_CHECKS_1);
            statement.executeUpdate();
            connection.commit();
            LOG.info("Transaction success!!");
        } catch (SQLException e) {
            try {
                connection.rollback();
                LOG.error("Transaction failed!!");
                throw new DatabaseException(500);
            } catch (SQLException ex) {
                LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
                throw new DatabaseException(500);
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
                throw new DatabaseException(500);
            }
        }
        return true;
    }

    /**
     * creating new chck
     * @param check
     * @return created check id
     * @throws DatabaseException
     */
    public int createCheck(Check check) throws DatabaseException {
        int id = 0;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(Query.INSERT_CHECK, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, check.getEmployee().getId());
            preparedStatement.setTimestamp(2, check.getTime());
            preparedStatement.setBigDecimal(3, check.getTotalCost());
            preparedStatement.executeUpdate();
            ResultSet key = preparedStatement.getGeneratedKeys();
            if (key.next()) {
                id = key.getInt(1);
            }
        } catch (SQLException e) {
            LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
            throw new DatabaseException(500);
        }
        return id;
    }
}
