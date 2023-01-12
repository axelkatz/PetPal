package fr.eql.akatz.demo.petpal.dao.impl;

import fr.eql.akatz.demo.petpal.dao.OwnerDao;
import fr.eql.akatz.demo.petpal.dao.impl.connection.PetPalDataSource;
import fr.eql.akatz.demo.petpal.entity.Owner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Remote(OwnerDao.class)
@Stateless
public class OwnerDaoImpl implements OwnerDao {

	private static final Logger logger = LogManager.getLogger();

	private static final String REQ_AUTH = "SELECT * FROM owner WHERE login = ? AND password = ?";
	private static final String REQ_FIND_ALL_BUT_SELF = "SELECT * FROM owner WHERE id != ? ORDER BY surname";

	private final DataSource dataSource = new PetPalDataSource();

	@Override
	public Owner authenticate(String login, String password) {
		Owner owner = null;
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(REQ_AUTH);
			statement.setString(1, login);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				owner = new Owner(
						resultSet.getLong("id"),
						resultSet.getString("name"),
						resultSet.getString("surname"),
						resultSet.getString("login"),
						resultSet.getString("password")
				);
			}
			connection.close();
		} catch (SQLException e) {
			logger.error("Une erreur s'est produite " +
					"lors de la consultation du propriétaire en base de données", e);
		}
		return owner;
	}

	@Override
	public List<Owner> findAllButSelf(Owner self) {
		List<Owner> owners = new ArrayList<>();
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(REQ_FIND_ALL_BUT_SELF);
			statement.setLong(1, self.getId());
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				owners.add(new Owner(
						resultSet.getLong("id"),
						resultSet.getString("name"),
						resultSet.getString("surname"),
						resultSet.getString("login"),
						resultSet.getString("password")
						)
				);
			}
			connection.close();
		} catch (SQLException e) {
			logger.error("Une erreur s'est produite " +
					"lors de la consultation des propriétaire en base de données", e);
		}
		return owners;
	}
}
