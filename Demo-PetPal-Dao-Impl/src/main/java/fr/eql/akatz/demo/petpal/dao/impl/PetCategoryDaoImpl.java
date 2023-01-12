package fr.eql.akatz.demo.petpal.dao.impl;

import fr.eql.akatz.demo.petpal.dao.PetCategoryDao;
import fr.eql.akatz.demo.petpal.dao.impl.connection.PetPalDataSource;
import fr.eql.akatz.demo.petpal.entity.Owner;
import fr.eql.akatz.demo.petpal.entity.PetCategory;
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

@Remote(PetCategoryDao.class)
@Stateless
public class PetCategoryDaoImpl implements PetCategoryDao {

    private static final Logger logger = LogManager.getLogger();

    private static final String REQ_FIND_BY_OWNER =
            "SELECT pc.category FROM pet_category pc " +
            "JOIN owner_pet_category opc ON pc.id = opc.pet_category_id " +
            "WHERE opc.owner_id = ? " +
            "ORDER BY pc.category";

    private final DataSource dataSource = new PetPalDataSource();

    @Override
    public List<PetCategory> findByOwner(Owner owner) {
        List<PetCategory> favorites = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(REQ_FIND_BY_OWNER);
            statement.setLong(1, owner.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                favorites.add(PetCategory.valueOf(resultSet.getString("category")));
            }
            connection.close();
        } catch (SQLException e) {
            logger.error("Une erreur s'est produite lors de la consultation des catégories d'animaux en base de données", e);
        }
        return favorites;
    }
}
