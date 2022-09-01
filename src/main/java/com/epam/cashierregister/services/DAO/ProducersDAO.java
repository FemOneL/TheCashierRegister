package com.epam.cashierregister.services.DAO;

import com.epam.cashierregister.services.consts.queries.Query;
import com.epam.cashierregister.services.consts.entityConsts.ProducerConst;
import com.epam.cashierregister.services.entities.goods.Producer;
import com.epam.cashierregister.services.exeptions.DatabaseException;
import com.epam.cashierregister.services.exeptions.InvalidInputException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for producers
 */
public class ProducersDAO extends DAO {

    public ProducersDAO() throws DatabaseException {}

    /**
     * Adding new producer in database
     * @param producer which need to added
     * @throws InvalidInputException if producer are not uniq
     */
    public void addNewProducer(Producer producer) throws InvalidInputException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(Query.INSERT_PRODUCER);
            statement.setString(1, producer.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidInputException("producer must be uniq");
        }
    }

    /**
     * @return List of producers
     * @throws DatabaseException if something go wrong with database
     */
    public List<Producer> getProducerList() throws DatabaseException {
        List<Producer> producers = new ArrayList<>();
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(Query.SELECT_PRODUCER);
            while (rs.next()) {
                producers.add(new Producer(rs.getString(ProducerConst.NAME)));
            }
        } catch (SQLException e) {
            LOG.fatal("Database was thrown SQLException with message: {} {}", e.getErrorCode() , e.getMessage());
            throw new DatabaseException(500);
        }
        return producers;
    }
}
