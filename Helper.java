import java.util.Optional;

public class Helper {

	private Optional<Integer> slhid;
	private String name;

	Helper(String name, Optional<Integer> slhid) {
		this.name = name;
		this.slhid = slhid;
	}

	public String getName() {
		return name;
	}

	public Optional<Integer> getSlhid() {
		return slhid;
	}

}
