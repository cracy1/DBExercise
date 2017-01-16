import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateTabels {

	private String username;
	private String password;
	private String url;
	private Connection connection;

	public CreateTabels(final String url, final String username, final String password) {
		this.url = url;
		this.username = username;
		this.password = password;

		try {
			Class.forName("org.postgresql.Driver");

		} catch (final ClassNotFoundException e) {
			System.exit(1);
		}

		try {
			connection = DriverManager.getConnection(this.url, this.username, this.password);
		} catch (final SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
			System.exit(1);
		}
	}

	public void createTables() {

		final String query = "DROP TABLE IF EXISTS Child CASCADE;" + "CREATE TABLE Child " + "(cid SERIAL PRIMARY KEY,"
				+ "name VARCHAR(30) NOT NULL,"
				+ "address VARCHAR(50) NOT NULL); DROP TABLE IF EXISTS SantasLittleHelper CASCADE;"
				+ "CREATE TABLE SantasLittleHelper " + "(slhid SERIAL PRIMARY KEY,"
				+ "name VARCHAR(30) NOT NULL); DROP TABLE IF EXISTS Gift CASCADE;" + "CREATE TABLE Gift "
				+ "(gid SERIAL PRIMARY KEY,"
				+ "description VARCHAR(50) NOT NULL); DROP TABLE IF EXISTS Present CASCADE; " + "CREATE TABLE Present "
				+ "(gid INTEGER," + "cid INTEGER," + "slhid INTEGER," + "FOREIGN KEY (gid) REFERENCES Gift(gid), "
				+ "FOREIGN KEY (cid) REFERENCES Child(cid)," +

				"FOREIGN KEY (slhid) REFERENCES SantasLittleHelper(slhid))";

		try {
			final PreparedStatement tableCreationStatement = connection.prepareStatement(query);
			tableCreationStatement.executeUpdate();
		} catch (final SQLException e) {
			System.exit(1);
		}
	}

}
