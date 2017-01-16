import java.util.List;
import java.util.Scanner;

public class Main {
	private SantasControlPanel cpanel;

	public Main(final SantasControlPanel cpanel) {
		this.cpanel = cpanel;
	}

	private void listen() {
		boolean valid = true;
		final Scanner scanner = new Scanner(System.in);

		while (valid) {
			System.out.println("Enter command: ");
			final String[] input = scanner.nextLine().split(" ");
			final String command = input[0];

			if (command.equals("quit")) {
				valid = false;
			} else if (command.equals("childreport")) {
				final int id = Integer.parseInt(input[2]);
				showChildReport(id);
			} else if (command.equals("slhreport")) {
				final int id = Integer.parseInt(input[2]);
				showHelperReport(id);
			} else {
				System.out.println("Invalid command");
			}
		}

		scanner.close();
	}

	private void showHelperReport(final int slhid) {
		try {
			final HelperReport report = cpanel.createHelperReport(slhid);
			System.out.println("Helper report: ");
			System.out.println("Helper: " + report.getHelper().getSlhid().get() + ", " + report.getHelper().getName());
			System.out.println("Children: (reports)");
			for (final ChildReport child : report.getChildReports()) {
				System.out.println(child.getChild().getCid() + ", " + child.getChild().getName() + ", "
						+ child.getChild().getAddress());
				final List<Gift> gifts = child.getGiftList();
				for (final Gift gift : gifts) {
					System.out.println(gift.getGid() + ", " + gift.getDescription());
				}

			}
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void showChildReport(final int cid) {
		try {
			final ChildReport child = cpanel.createChildReport(cid);
			System.out.println(child.getChild().getCid() + ", " + child.getChild().getName() + ", "
					+ child.getChild().getAddress());
			final List<Gift> gifts = child.getGiftList();
			for (final Gift gift : gifts) {
				System.out.println(gift.getGid() + ", " + gift.getDescription());

			}
		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(final String[] args) {
		final String url = "jdbc:postgresql://mod-intro-databases.cs.bham.ac.uk/anc513";
		final String user = "anc513";
		final String pass = "do1kxorb0w";

		final CreateTabels tableCreator = new CreateTabels(url, user, pass);
		tableCreator.createTables();

		final PopulateTabels populator = new PopulateTabels(url, user, pass);

		populator.addChildren();
		populator.addGifts();
		populator.addHelpers();
		populator.addPresents();

		final SantasControlPanel cPanel = new SantasControlPanel(user, url, pass);
		new Main(cPanel).listen();
	}

}
