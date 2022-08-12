package com.epam.cashierregister.services.DAO;

import com.epam.cashierregister.services.consts.ProducerConst;
import com.epam.cashierregister.services.entities.goods.Producer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProducersDAO extends DAO {

    public void addNewProducer(Producer producer) {
        try (Connection connection = getConnection()) {
            String insertCategory = "INSERT INTO " + ProducerConst.TABLE_NAME + " VALUES (default, ?)";
            PreparedStatement statement = connection.prepareStatement(insertCategory);
            statement.setString(1, producer.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Producer> getProducerList() {
        List<Producer> producers = new ArrayList<>();
        try (Connection connection = getConnection()) {
            String selectCategory = "SELECT " + ProducerConst.NAME + " FROM " + ProducerConst.TABLE_NAME;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectCategory);
            while (rs.next()) {
                producers.add(new Producer(rs.getString(ProducerConst.NAME)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producers;
    }
}
