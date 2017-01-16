import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public class PopulateTabels {

	private Connection connection;

	public PopulateTabels(String url, String username, String pass) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.exit(1);
		}

		try {
			connection = DriverManager.getConnection(url, username, pass);
		} catch (SQLException e) {
			System.exit(1);
		}

	}

	public void addChildren() {
		for (int i = 0; i < 1000; i++) {
			String query = "INSERT INTO Child (name, address) VALUES (?, ?)";
			Child child = child(i);
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, child.getName());
				preparedStatement.setString(2, child.getAddress());
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				System.exit(1);
			}
		}

	}

	public void addHelpers() {

		for (int i = 0; i < 10; i++) {
			String query = "INSERT INTO SantasLittleHelper (name) VALUES (?) ";
			Helper helper = helper(i);
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, helper.getName());
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				System.exit(1);
			}
		}
	}

	public void addGifts() {

		for (int i = 0; i < 10; i++) {
			String query = "INSERT INTO Gift (description) VALUES (?) ";
			Gift gift = gift(i);
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, gift.getDescription());
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				System.exit(1);
			}
		}
	}

	public void addPresents() {
		for (int i = 0; i < 100; i++) {
			String query = "INSERT INTO Present (gid, cid, slhid) VALUES (?, ?, ?)";
			Present present = present(1000, 10, 10);
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setInt(1, present.getGid());
				preparedStatement.setInt(2, present.getCid());
				preparedStatement.setInt(3, present.getSlhid());
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				System.exit(1);
			}
		}
	}

	private Child child(int index) {

		return new Child(Optional.empty(), "Child" + index, "Address" + index);
	}

	private Helper helper(int index) {

		return new Helper("Helper" + index, Optional.empty());
	}

	private Gift gift(int index) {

		return new Gift(Optional.empty(), "Description" + index);
	}

	private Present present(int children, int gifts, int helpers) {

		return new Present(randomId(children), randomId(gifts), randomId(helpers));
	}

	private int randomId(int max) {
		return 1 + (int)(Math.random() * ((max - 1) + 1));
	}
}
