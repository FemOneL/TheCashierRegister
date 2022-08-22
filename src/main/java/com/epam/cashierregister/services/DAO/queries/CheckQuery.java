package com.epam.cashierregister.services.DAO.queries;

import com.epam.cashierregister.services.consts.CheckConst;
import com.epam.cashierregister.services.consts.CheckHasGoodsConst;
import com.epam.cashierregister.services.consts.GoodsConst;
import com.epam.cashierregister.services.entities.goods.Goods;

public class CheckQuery {
    //deleteCheck
    public static final String UPDATE_GOODS_NUMBER = "UPDATE " + GoodsConst.TABLE_NAME +
            " SET " + GoodsConst.NUMBERS + " = " + GoodsConst.NUMBERS + " + " +
            "(SELECT " + CheckHasGoodsConst.NUMBER_OF_GOODS + " FROM " + CheckHasGoodsConst.TABLE_NAME +
            " WHERE " + CheckHasGoodsConst.GOODS_ID + " = ? AND " + CheckHasGoodsConst.CHECK_ID + " = ?) WHERE " + CheckHasGoodsConst.GOODS_ID + " = ?";
    public static final String DELETE_FROM_CHECK_HAS_GOODS = "DELETE FROM " + CheckHasGoodsConst.TABLE_NAME +
            " WHERE " + CheckHasGoodsConst.CHECK_ID + " = ?";
    public static final String DELETE_CHECK = "DELETE FROM " + CheckConst.TABLE_NAME +
            " WHERE " + CheckConst.CHECK_ID + " = ?";

    //deleteSpecificCheck
    public static final String UPDATE_TOTAL_COST = "UPDATE " + CheckConst.TABLE_NAME + " SET " + CheckConst.TOTAL_COST + " = " +
            CheckConst.TOTAL_COST + " - ((SELECT " + CheckHasGoodsConst.NUMBER_OF_GOODS + " FROM " + CheckHasGoodsConst.TABLE_NAME +
            " WHERE " + CheckHasGoodsConst.CHECK_ID + " = ? AND " + CheckHasGoodsConst.GOODS_ID + " = ?) *" +
            " (SELECT " + GoodsConst.COST + " FROM " + GoodsConst.TABLE_NAME + " WHERE " + GoodsConst.GOODS_ID + " = ?))" +
            " WHERE " + CheckHasGoodsConst.CHECK_ID + " = ?";
    public static final String UPDATE_CHECK = "UPDATE " + CheckHasGoodsConst.TABLE_NAME +
            " SET " + CheckHasGoodsConst.NUMBER_OF_GOODS + " = " + CheckHasGoodsConst.NUMBER_OF_GOODS + " - ?" +
            " WHERE " + CheckHasGoodsConst.GOODS_ID + " = ? AND " + CheckHasGoodsConst.CHECK_ID + " = ?";
    public static final String UPDATE_TOTAL_COST_AGAIN = "UPDATE " + CheckConst.TABLE_NAME + " SET " + CheckConst.TOTAL_COST + " = " +
            CheckConst.TOTAL_COST + " + ((SELECT " + CheckHasGoodsConst.NUMBER_OF_GOODS + " FROM " + CheckHasGoodsConst.TABLE_NAME +
            " WHERE " + CheckHasGoodsConst.CHECK_ID + " = ? AND " + CheckHasGoodsConst.GOODS_ID + " = ?) *" +
            " (SELECT " + GoodsConst.COST + " FROM " + GoodsConst.TABLE_NAME + " WHERE " + GoodsConst.GOODS_ID + " = ?))" +
            " WHERE " + CheckHasGoodsConst.CHECK_ID + " = ?";
    public static final String RETURN_GOODS = "UPDATE " + GoodsConst.TABLE_NAME + " SET " + GoodsConst.NUMBERS + " = " + GoodsConst.NUMBERS + " + ?" +
            " WHERE " + GoodsConst.GOODS_ID + " = ?";
    public static final String DELETE_GOODS = "DELETE FROM " + CheckHasGoodsConst.TABLE_NAME + " WHERE " + CheckHasGoodsConst.GOODS_ID + " = ? AND " + CheckHasGoodsConst.CHECK_ID + " = ?";
    public static final String RETURN_GOODS_IN_WAREHOUSE = "UPDATE " + GoodsConst.TABLE_NAME + " SET " +
            GoodsConst.NUMBERS + " = " + GoodsConst.NUMBERS + " + (SELECT " + CheckHasGoodsConst.NUMBER_OF_GOODS + " FROM " +
            CheckHasGoodsConst.TABLE_NAME + " WHERE " + CheckHasGoodsConst.GOODS_ID + " = ? AND " + CheckHasGoodsConst.CHECK_ID +
            " = ?) WHERE " + GoodsConst.GOODS_ID + " = ?";

    //getCheckWithGoods
    public static final String SELECT_GOODS = "SELECT * FROM " + CheckHasGoodsConst.TABLE_NAME +
            " WHERE " + CheckHasGoodsConst.CHECK_ID + " = ? ORDER BY " + CheckHasGoodsConst.GOODS_ID;
    public static final String SELECT_CHECK = "SELECT * FROM " + CheckConst.TABLE_NAME + " WHERE " + CheckConst.CHECK_ID + " = ?";

    //getChecks
    public static final String SELECT_FROM_CHECK = "SELECT * FROM " + CheckConst.TABLE_NAME + " ORDER BY " + CheckConst.TIME +
            " DESC LIMIT ?, 21";

    //addGoodsInCheck
    public static final String SET_FOREIGN_KEY_CHECK = "SET FOREIGN_KEY_CHECKS=0";
    public static final String INSERT_GOODS = "INSERT INTO " + CheckHasGoodsConst.TABLE_NAME +
            " VALUES (?, ?, ?)";
    public static final String UPDATE_GOODS = "UPDATE " + GoodsConst.TABLE_NAME + " SET " + GoodsConst.NUMBERS + " = ? " +
            " WHERE " + GoodsConst.GOODS_ID + " = ?";

    //createCheck
    public static final String INSERT_CHECK = "INSERT INTO " + CheckConst.TABLE_NAME +
            " VALUES (default, ?, ?, ?)";
}
