package fr.eql.akatz.demo.petpal.dao.impl;

import fr.eql.akatz.demo.petpal.dao.GlossaryDao;
import fr.eql.akatz.demo.petpal.dao.impl.connection.PetPalDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Remote(GlossaryDao.class)
@Stateless
public class GlossaryDaoImpl implements GlossaryDao {

    private static final Logger logger = LogManager.getLogger();

    private static final String REQ_FIND_ORDERED = "SELECT expression FROM glossary ORDER BY expression";

    private final DataSource dataSource = new PetPalDataSource();

    @Override
    public List<String> findAll() {
        List<String> expressions = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(REQ_FIND_ORDERED);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                expressions.add(resultSet.getString("expression"));
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("Une erreur s'est produite lors de la consultation du lexique en base de données", e);
        }
        return expressions;
    }
}
