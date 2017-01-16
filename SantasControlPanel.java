import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SantasControlPanel {

	private Connection connection;

	public SantasControlPanel(String user, String url, String pass) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (  ClassNotFoundException e) {
			System.exit(1);
		}

		try {
			connection = DriverManager.getConnection(url, user, pass);
		} catch (  SQLException e) {
			System.exit(1);
		}
	}

	public ChildReport createChildReport(  int cid) throws Exception {
		  ChildReport childReport = new ChildReport();
		  List<Gift> giftList = new ArrayList<Gift>();
		Child child = null;

		  String childQuery = "SELECT name, address FROM Child WHERE cid = ?";
		  String giftsQuery = "SELECT Present.gid, Gift.description " + "FROM Child, Present, Gift "
				+ "WHERE Child.cid = ? " + "AND " + "Child.cid = Present.cid " + "AND "
				+ "Present.gid = Gift.gid;";
		try {
			  PreparedStatement childStatement = connection.prepareStatement(childQuery);
			childStatement.setInt(1, cid);

			  ResultSet childResultSet = childStatement.executeQuery();
			if (childResultSet.next()) {
				child = new Child(Optional.of(cid), childResultSet.getString("name"),
						childResultSet.getString("address"));
			} else {
				throw new Exception("Child is not in the database");
			}

			  PreparedStatement giftsStatement = connection.prepareStatement(giftsQuery);
			giftsStatement.setInt(1, cid);

			  ResultSet giftsResultSet = giftsStatement.executeQuery();
			while (giftsResultSet.next()) {
				  Gift gift = new Gift(Optional.of(giftsResultSet.getInt("gid")),
						giftsResultSet.getString("description"));
				giftList.add(gift);
			}

		} catch (  SQLException e) {
		}

		childReport.setChild(child);
		childReport.setGiftList(giftList);
		return childReport;
	}

	public HelperReport createHelperReport(  int slhid) throws Exception {
		  HelperReport helperReport = new HelperReport();
		  List<ChildReport> childReportList = new ArrayList<ChildReport>();
		Helper helper = null;

		  String helperQuery = "SELECT * FROM SantasLittleHelper WHERE slhid = ?;";
		  String childrenQuery = "SELECT Child.cid FROM Child, SantasLittleHelper, Present "
				+ "WHERE SantasLittleHelper.slhid = 10 "
				+ "AND Present.slhid = SantasLittleHelper.slhid " + "AND Present.cid = Child.cid;";

		try {
			  PreparedStatement helperStatement = connection.prepareStatement(helperQuery);
			helperStatement.setInt(1, slhid);

			  ResultSet helperResultSet = helperStatement.executeQuery();
			if (helperResultSet.next()) {
				helper = new Helper(helperResultSet.getString("name"), Optional.of(slhid));
			} else {
				throw new Exception("Helper is not in the database");
			}

			  PreparedStatement childrenStatement = connection.prepareStatement(childrenQuery);

			  ResultSet childrenResultSet = childrenStatement.executeQuery();
			while (childrenResultSet.next()) {
				  ChildReport childReport = createChildReport(childrenResultSet.getInt("cid"));
				childReportList.add(childReport);
			}

		} catch (  SQLException e) {
		}

		helperReport.setHelper(helper);
		helperReport.setChildReports(childReportList);
		return helperReport;
	}
}
