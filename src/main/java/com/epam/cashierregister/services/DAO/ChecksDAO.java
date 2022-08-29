package com.epam.cashierregister.services.DAO;

import com.epam.cashierregister.services.DAO.queries.CheckQuery;
import com.epam.cashierregister.services.consts.CheckConst;
import com.epam.cashierregister.services.consts.CheckHasGoodsConst;
import com.epam.cashierregister.services.consts.EmployeeConst;
import com.epam.cashierregister.services.entities.check.Check;
import com.epam.cashierregister.services.entities.goods.Goods;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ChecksDAO extends DAO {

    public boolean deleteCheck(Check check) {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            PreparedStatement updateGoodsStatement = connection.prepareStatement(CheckQuery.UPDATE_GOODS_NUMBER);
            PreparedStatement deleteCheckHasGoodsStatement = connection.prepareStatement(CheckQuery.DELETE_FROM_CHECK_HAS_GOODS);
            PreparedStatement deleteCheckStatement = connection.prepareStatement(CheckQuery.DELETE_CHECK);
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
        } catch (SQLException e) {
            try {
                connection.rollback();
                return false;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public boolean deleteSpecificCheck(Check check, int goodsId) {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement removeCostStatement = connection.prepareStatement(CheckQuery.UPDATE_TOTAL_COST);
            removeCostStatement.setInt(1, check.getId());
            removeCostStatement.setInt(2, goodsId);
            removeCostStatement.setInt(3, goodsId);
            removeCostStatement.setInt(4, check.getId());
            removeCostStatement.executeUpdate();

            PreparedStatement returnGoodsStatement = connection.prepareStatement(CheckQuery.RETURN_GOODS_IN_WAREHOUSE);
            returnGoodsStatement.setInt(1, goodsId);
            returnGoodsStatement.setInt(2, check.getId());
            returnGoodsStatement.setInt(3, goodsId);
            returnGoodsStatement.executeUpdate();

            PreparedStatement deleteGoodsFromCheckStatement = connection.prepareStatement(CheckQuery.DELETE_GOODS);
            deleteGoodsFromCheckStatement.setInt(1, goodsId);
            deleteGoodsFromCheckStatement.setInt(2, check.getId());
            deleteGoodsFromCheckStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean deleteSpecificCheck(Check check, int goodsId, int diff) {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            PreparedStatement updateTotalCostStatement = connection.prepareStatement(CheckQuery.UPDATE_TOTAL_COST);
            updateTotalCostStatement.setInt(1, check.getId());
            updateTotalCostStatement.setInt(2, goodsId);
            updateTotalCostStatement.setInt(3, goodsId);
            updateTotalCostStatement.setInt(4, check.getId());
            updateTotalCostStatement.executeUpdate();

            PreparedStatement updateCheckStatement = connection.prepareStatement(CheckQuery.UPDATE_CHECK);
            updateCheckStatement.setInt(1, diff);
            updateCheckStatement.setInt(2, goodsId);
            updateCheckStatement.setInt(3, check.getId());
            updateCheckStatement.executeUpdate();

            PreparedStatement updateTotalCostAgainStatement = connection.prepareStatement(CheckQuery.UPDATE_TOTAL_COST_AGAIN);
            updateTotalCostAgainStatement.setInt(1, check.getId());
            updateTotalCostAgainStatement.setInt(2, goodsId);
            updateTotalCostAgainStatement.setInt(3, goodsId);
            updateTotalCostAgainStatement.setInt(4, check.getId());
            updateTotalCostAgainStatement.executeUpdate();

            PreparedStatement updateGoodsStatement = connection.prepareStatement(CheckQuery.RETURN_GOODS);
            updateGoodsStatement.setInt(1, diff);
            updateGoodsStatement.setInt(2, goodsId);
            updateGoodsStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    public Check getCheckWithGoods(int id) {
        Check check = null;
        Goods goods;
        EmployeeDAO employeeDAO = new EmployeeDAO();
        GoodsDAO goodsDAO = new GoodsDAO();
        try (Connection connection = getConnection()) {
            PreparedStatement checkStatement = connection.prepareStatement(CheckQuery.SELECT_CHECK);
            PreparedStatement goodsStatement = connection.prepareStatement(CheckQuery.SELECT_GOODS);
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
            e.printStackTrace();
        }
        return check;
    }

    public List<Check> getChecks(int page, String search) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        List<Check> checks = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String searchRes = " WHERE " + EmployeeConst.FIRSTNAME + " LIKE '%" + search + "%' OR " +
                    CheckConst.EMP_ID + " LIKE '%" + search + "%' OR " +
                    EmployeeConst.SECONDNAME + " LIKE '%" + search + "%' OR " +
                    CheckConst.TIME + " LIKE '%" + search + "%' OR " + CheckConst.TIME + " LIKE '% + search + %' ";
            String selectFromCheck = "SELECT * FROM " + CheckConst.TABLE_NAME +
                    " INNER JOIN " + EmployeeConst.TABLE_NAME + " ON " + CheckConst.TABLE_NAME + "." + CheckConst.EMP_ID +
                    " = " + EmployeeConst.TABLE_NAME + "." + EmployeeConst.EMP_ID +
                    (search != null ? searchRes : " ") +
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
            e.printStackTrace();
        }
        return checks;
    }


    public boolean addGoodsInCheck(Set<Goods> goodsSet, Check check) {
        Connection connection = null;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            CallableStatement statement = connection.prepareCall(CheckQuery.SET_FOREIGN_KEY_CHECK);
            statement.executeUpdate();
            PreparedStatement insertStatement = connection.prepareStatement(CheckQuery.INSERT_GOODS);
            PreparedStatement updateStatement = connection.prepareStatement(CheckQuery.UPDATE_GOODS);
            for (Goods goods : goodsSet) {
                insertStatement.setInt(1, check.getId());
                insertStatement.setInt(2, goods.getId());
                insertStatement.setInt(3, goods.getNumbers());
                updateStatement.setInt(1, (goods.getTotalNumber() - goods.getNumbers()));
                updateStatement.setInt(2, goods.getId());
                insertStatement.executeUpdate();
                updateStatement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public int createCheck(Check check) {
        int id = 0;
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CheckQuery.INSERT_CHECK, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, check.getEmployee().getId());
            preparedStatement.setTimestamp(2, check.getTime());
            preparedStatement.setBigDecimal(3, check.getTotalCost());
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
}
