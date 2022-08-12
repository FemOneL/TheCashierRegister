package com.epam.cashierregister.services.DAO;

import com.epam.cashierregister.services.consts.CategoryConst;
import com.epam.cashierregister.services.entities.goods.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriesDAO extends DAO {

    public void addNewCategory(Category category) {
        try (Connection connection = getConnection()) {
            String insertCategory = "INSERT INTO " + CategoryConst.TABLE_NAME + " VALUES (default, ?)";
            PreparedStatement statement = connection.prepareStatement(insertCategory);
            statement.setString(1, category.getCategory());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Category> getCategoryList() {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String selectCategory = "SELECT " + CategoryConst.CATEGORY + " FROM " + CategoryConst.TABLE_NAME;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectCategory);
            while (rs.next()) {
                categories.add(new Category(rs.getString(CategoryConst.CATEGORY)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

}