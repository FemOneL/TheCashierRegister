package com.epam.cashierregister.services.dao;

import com.epam.cashierregister.services.consts.queries.Query;
import com.epam.cashierregister.services.consts.entityconsts.CategoryConst;
import com.epam.cashierregister.services.entities.goods.Category;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import com.epam.cashierregister.services.exeptions.InvalidInputException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for categories
 */
public class CategoriesDAO extends DAO {

    public CategoriesDAO() throws DatabaseException {
    }

    /**
     * Adding new category in database
     * @param category category which need to added
     * @throws InvalidInputException if category are not uniq
     */
    public void addNewCategory(Category category) throws InvalidInputException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(Query.INSERT_CATEGORY);
            statement.setString(1, category.getCategory());
            statement.executeUpdate();
            LOG.debug("Added new category to Database");
        } catch (SQLException e) {
            throw new InvalidInputException("category must be uniq");
        }
    }


    /**
     * @return List of categories
     * @throws DatabaseException if something go wrong with database
     */
    public List<Category> getCategoryList() throws DatabaseException {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(Query.SELECT_CATEGORY);
            while (rs.next()) {
                categories.add(new Category(rs.getString(CategoryConst.CATEGORY)));
            }
        } catch (SQLException e) {
            LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
            throw new DatabaseException(500);
        }
        LOG.debug("Returned list of categories, size = {}", categories.size());
        return categories;
    }

}
