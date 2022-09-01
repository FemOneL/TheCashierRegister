package com.epam.cashierregister.services.consts.queries;

import com.epam.cashierregister.services.consts.entityConsts.*;

public class Query {

    //CATEGORIES
    //addNewCategory
    public static final String INSERT_CATEGORY = "INSERT INTO " + CategoryConst.TABLE_NAME + " VALUES (default, ?)";
    //getCategoryList
    public static final String SELECT_CATEGORY = "SELECT " + CategoryConst.CATEGORY + " FROM " + CategoryConst.TABLE_NAME;

    //PRODUCERS
    //getProducerList()
    public static final String SELECT_PRODUCER = "SELECT " + ProducerConst.NAME + " FROM " + ProducerConst.TABLE_NAME;
    //addNewProducer
    public static final String INSERT_PRODUCER = "INSERT INTO " + ProducerConst.TABLE_NAME + " VALUES (default, ?)";

    //REPORTS
    //writeSelling
    public static final String INSERT_SELLING = "INSERT INTO " + SellingConst.TABLE_NAME + " VALUES (default, ?, ?)";
    //writeReturned
    public static final String INSERT_RETURNED = "INSERT INTO " + ReturnConst.TABLE_NAME + " VALUES (default, ?, ?)";
    //writeReport
    public static final String INSERT_REPORT = "INSERT INTO " + ReportConst.TABLE_NAME + " VALUES (default, ?, ?, ?, ?, ?, ?)";

    //GOODS
    //deleteGoods
    public static final String DELETE = "DELETE FROM " + GoodsConst.TABLE_NAME + " WHERE " + GoodsConst.GOODS_ID + " = ?";
    //checkModel
    public static final String SELECT_MODEL = "SELECT " + GoodsConst.MODEL + " FROM " + GoodsConst.TABLE_NAME +
            " WHERE " + GoodsConst.MODEL + " = ?";
    //updateNumber
    public static final String UPDATE = "UPDATE " + GoodsConst.TABLE_NAME +
            " SET " + GoodsConst.NUMBERS + " = " + " ? " +
            " WHERE " + GoodsConst.GOODS_ID + " = " + " ? ";
    //addGoods
    public static final String ADD_GOODS = "INSERT INTO " + GoodsConst.TABLE_NAME +
            " VALUES (default, ?, ?, ?, ?, " +
            "(SELECT " + CategoryConst.CATEGORY_ID + " FROM " + CategoryConst.TABLE_NAME
            + " WHERE " + CategoryConst.CATEGORY + " = ?), " +
            "(SELECT " + ProducerConst.PRODUCER_ID + " FROM " + ProducerConst.TABLE_NAME
            + " WHERE " + ProducerConst.NAME + " = ?))";

    //EMPLOYEES
    //getAuthorizeID
    public static final String GET_AUTH_ID = "SELECT " + AuthorizeConst.AUTHORIZE_ID + " FROM "+ AuthorizeConst.TABLE_NAME +
            " WHERE " + AuthorizeConst.EMAIL + " = ?";
    //deleteEmployee
    public static final String DELETE_EMPLOYEE = "DELETE FROM " + EmployeeConst.TABLE_NAME + " WHERE " + EmployeeConst.EMP_ID + " = ?";
    public static final String DELETE_AUTHORIZE = "DELETE FROM " + AuthorizeConst.TABLE_NAME + " WHERE " + AuthorizeConst.AUTHORIZE_ID + " = ?";
    //addEmployee
    public static final String INSERT_AUTHORIZE = "INSERT INTO " + AuthorizeConst.TABLE_NAME + " VALUES (default, ?, ?)";
    public static final String INSERT_EMPLOYEE = "INSERT INTO " + EmployeeConst.TABLE_NAME +
            " VALUES (default, ?, ?, ?, (SELECT " + RolesConst.ROLE_ID + " FROM " + RolesConst.TABLE_NAME +
            " WHERE " + RolesConst.ROLE + " = ?), " + "(SELECT " + AuthorizeConst.AUTHORIZE_ID +
            " FROM " + AuthorizeConst.TABLE_NAME + " WHERE " + AuthorizeConst.EMAIL + " = ?))";
    //updatePassword
    public static final String UPDATE_PASSWORD = "UPDATE " + AuthorizeConst.TABLE_NAME + " SET "+ AuthorizeConst.PASSWORD + " = ?" +
            " WHERE " + AuthorizeConst.AUTHORIZE_ID + " = ?";
    //getEmployee
    public static final String SELECT_USER = "SELECT " + EmployeeConst.EMP_ID + ", " + EmployeeConst.PHOTO + ", " + EmployeeConst.FIRSTNAME + ", " + EmployeeConst.SECONDNAME
            + ", " + RolesConst.ROLE + ", " + AuthorizeConst.EMAIL + ", " + AuthorizeConst.PASSWORD
            + " FROM " + EmployeeConst.TABLE_NAME
            + " INNER JOIN " + RolesConst.TABLE_NAME
            + " ON " + EmployeeConst.TABLE_NAME + "." + EmployeeConst.ROLE_ID + " = " + RolesConst.TABLE_NAME + "." + RolesConst.ROLE_ID
            + " INNER JOIN " + AuthorizeConst.TABLE_NAME
            + " ON " + EmployeeConst.TABLE_NAME + "." + EmployeeConst.AUTHORIZE_ID + " = " + AuthorizeConst.TABLE_NAME + "." + AuthorizeConst.AUTHORIZE_ID
            + " WHERE " + AuthorizeConst.TABLE_NAME + "." + AuthorizeConst.EMAIL + " = ?";
    //getRoles
    public static final String SELECT_ROLES = "SELECT " + RolesConst.ROLE + " FROM " + RolesConst.TABLE_NAME;
    //changeRole
    public static final String CHANGE_ROLE = "UPDATE " + EmployeeConst.TABLE_NAME + " SET " +
            EmployeeConst.ROLE_ID + " = (SELECT " + RolesConst.ROLE_ID + " FROM " +
            RolesConst.TABLE_NAME + " WHERE " + RolesConst.ROLE + " = ?) WHERE " + EmployeeConst.EMP_ID + " = ?";
    //getEmployee2
    public static final String SELECT_EMPLOYEE = "SELECT " + EmployeeConst.EMP_ID + ", " + EmployeeConst.PHOTO + ", " + EmployeeConst.FIRSTNAME + ", " + EmployeeConst.SECONDNAME
            + ", " + RolesConst.ROLE + ", " + AuthorizeConst.EMAIL + ", " + AuthorizeConst.AUTHORIZE_ID
            + " FROM " + EmployeeConst.TABLE_NAME
            + " INNER JOIN " + RolesConst.TABLE_NAME
            + " ON " + EmployeeConst.TABLE_NAME + "." + EmployeeConst.ROLE_ID + " = " + RolesConst.TABLE_NAME + "." + RolesConst.ROLE_ID
            + " INNER JOIN " + AuthorizeConst.TABLE_NAME
            + " ON " + EmployeeConst.TABLE_NAME + "." + EmployeeConst.AUTHORIZE_ID + " = " + AuthorizeConst.TABLE_NAME + "." + AuthorizeConst.AUTHORIZE_ID
            + " WHERE " + EmployeeConst.EMP_ID + " = ? ";

    //CHECKS
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
    public static final String INSERT_GOODS = "INSERT INTO " + CheckHasGoodsConst.TABLE_NAME +
            " VALUES (?, ?, ?)";
    public static final String UPDATE_GOODS = "UPDATE " + GoodsConst.TABLE_NAME + " SET " + GoodsConst.NUMBERS + " = ? " +
            " WHERE " + GoodsConst.GOODS_ID + " = ?";
    //createCheck
    public static final String INSERT_CHECK = "INSERT INTO " + CheckConst.TABLE_NAME +
            " VALUES (default, ?, ?, ?)";

    //FOREIGN CHECK
    public static final String FOREIGN_KEY_CHECKS_0 = "SET FOREIGN_KEY_CHECKS=0";
    public static final String FOREIGN_KEY_CHECKS_1 = "SET FOREIGN_KEY_CHECKS=1";
}

