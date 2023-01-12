package fr.eql.akatz.demo.petpal.dao.impl;

import fr.eql.akatz.demo.petpal.dao.CatDao;
import fr.eql.akatz.demo.petpal.dao.impl.connection.PetPalDataSource;
import fr.eql.akatz.demo.petpal.entity.Cat;
import fr.eql.akatz.demo.petpal.entity.CatBreed;
import fr.eql.akatz.demo.petpal.entity.Owner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Remote(CatDao.class)
@Stateless
public class CatDaoImpl implements CatDao {

	private static final Logger logger = LogManager.getLogger();

	private static final String REQ_INSERT_PET = "INSERT INTO pet (name, birth_date, picture, owner_id) VALUES (?,?,?,?)";
	private static final String REQ_INSERT_CAT = "INSERT INTO cat (pet_id, cat_breed_id) VALUES (?,?)";
	private static final String REQ_EXISTS =
			"SELECT * FROM cat " +
			"JOIN pet ON cat.pet_id = pet.id " +
			"WHERE name = ?";
	private static final String REQ_COUNT = "SELECT COUNT(*) AS count FROM pet WHERE pet.owner_id = ?";
	private static final String REQ_FIND_BY_OWNER =
			"SELECT c.id, p.name, cb.breed, p.birth_date, p.picture FROM cat c " +
			"JOIN pet p ON c.pet_id = p.id " +
			"JOIN cat_breed cb ON c.cat_breed_id = cb.id " +
			"WHERE p.owner_id = ? " +
			"ORDER BY p.name";

	private final DataSource dataSource = new PetPalDataSource();

	@Override
	public void insertCat(Cat cat, Owner owner) {
		try (Connection connection = dataSource.getConnection()) {
			connection.setAutoCommit(false);
			long id = petStatementExecution(cat, owner, connection);
			if (id > 0) {
				catStatementExecution(cat, connection);
			} else {
				connection.rollback();
			}
			connection.commit();
		} catch (SQLException e) {
			logger.error("Une erreur s'est produite lors de l'insertion de " + cat.getName() , e);
		}
	}

	private long petStatementExecution(Cat cat, Owner owner, Connection connection) throws SQLException {
		long id = 0;
		PreparedStatement statement = connection.prepareStatement(REQ_INSERT_PET, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, cat.getName());
		statement.setDate(2, Date.valueOf(cat.getBirthDate()));
		statement.setString(3, cat.getPicture());
		statement.setLong(4, owner.getId());
		int affectedRows = statement.executeUpdate();
		if (affectedRows > 0) {
			try (ResultSet resultSet = statement.getGeneratedKeys()) {
				if (resultSet.next()) {
					id = resultSet.getLong(1);
					cat.setPetId(id);
				}
			} catch (SQLException e) {
				connection.rollback();
				logger.error("Une erreur s'est produite lors de la récupération de l'id de l'animal inséré.", e);
			}
		}
		return id;
	}

	private void catStatementExecution(Cat cat, Connection connection) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(REQ_INSERT_CAT, Statement.RETURN_GENERATED_KEYS);
		statement.setLong(1, cat.getPetId());
		statement.setLong(2, cat.getBreed().ordinal() + 1);
		int affectedRows = statement.executeUpdate();
		if (affectedRows > 0) {
			try (ResultSet resultSet = statement.getGeneratedKeys()) {
				if (resultSet.next()) {
					long id = resultSet.getLong(1);
					cat.setId(id);
					logger.info(cat.getName() + " inséré en base de données avec id " + id);
				}
			} catch (SQLException e) {
				connection.rollback();
				logger.error("Une erreur s'est produite lors de la récupération de l'id du chat inséré.", e);
			}
		}
	}

	@Override
	public boolean exists(Cat cat) {
		boolean exists = false;
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(REQ_EXISTS);
			statement.setString(1, cat.getName());
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				exists = true;
			}
			connection.close();
		} catch (SQLException e) {
			logger.error("Une erreur s'est produite " +
					"lors de la consultation du chat en base de données", e);
		}
		return exists;
	}

	@Override
	public int countByOwner(Owner owner) {
		int count = 0;
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(REQ_COUNT);
			statement.setLong(1, owner.getId());
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				count = resultSet.getInt("count");
			}
			connection.close();
		} catch (SQLException e) {
			logger.error("Une erreur s'est produite " +
					"lors de la consultation des animaux en base de données", e);
		}
		return count;
	}

	@Override
	public List<Cat> findByOwner(Owner owner) {
		List<Cat> cats = new ArrayList<>();
		try {
			Connection connection = dataSource.getConnection();
			PreparedStatement statement = connection.prepareStatement(REQ_FIND_BY_OWNER);
			statement.setLong(1, owner.getId());
			ResultSet resultSet = statement.executeQuery();
//			ResultSetMetaData rsMetaData = resultSet.getMetaData();
//			System.out.println("List of column names in the current table: ");
//			int count = rsMetaData.getColumnCount();
//			for(int i = 1; i<=count; i++) {
//				System.out.println(rsMetaData.getColumnName(i));
//			}
			while(resultSet.next()) {
				cats.add(new Cat(
						resultSet.getLong("id"),
						resultSet.getString("name"),
						CatBreed.valueOf(resultSet.getString("breed")),
						resultSet.getDate("birth_date").toLocalDate(),
						resultSet.getString("picture")
						)
				);
			}
			connection.close();
		} catch (SQLException e) {
			logger.error("Une erreur s'est produite lors de la consultation des chats en base de données", e);
		}
		return cats;
	}
}
