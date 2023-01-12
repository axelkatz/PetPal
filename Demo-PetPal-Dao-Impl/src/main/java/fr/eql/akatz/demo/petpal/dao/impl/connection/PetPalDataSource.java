package fr.eql.akatz.demo.petpal.dao.impl.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;

public class PetPalDataSource implements DataSource {

	private static final Logger logger = LogManager.getLogger();

	private static final String DATABASE_PROPERTY_FILE_PATH = "petpal_db.properties";

	private static String host;
	private static String database;
	private static String username;
	private static String password;

	static {
		Properties props = new Properties();
		InputStream inputStream = Thread.currentThread()
				.getContextClassLoader()
				.getResourceAsStream(DATABASE_PROPERTY_FILE_PATH);
		try {
			props.load(inputStream);
			host = props.getProperty("host");
			database = props.getProperty("database");
			username = props.getProperty("username");
			password = props.getProperty("password");
			try {
				Class.forName(props.getProperty("driver"));
			} catch (ClassNotFoundException e) {
				logger.error("Classe du driver MySql introuvable.", e);
			}
		} catch (IOException e) {
			logger.error("Une erreur s'est produite lors de la lecture " +
					"du fichier properties de la base de données.", e);
		}
	}

	@Override
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(host + database, username, password);
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {

	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {

	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	@Override
	public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}
}
