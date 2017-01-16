import java.util.Optional;

public class Child {

	public Optional<Integer> cid;
	public String name;
	public String address;

	public Child(Optional<Integer> cid, String name, String address) {
		this.cid = cid;
		this.name = name;
		this.address = address;
	}

	public Optional<Integer> getCid() {
		return cid;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

}
