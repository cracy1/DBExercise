import java.util.Optional;

public class Gift {

	public Optional<Integer> gid;
	public String description;

	public Gift(Optional<Integer> gid, String description) {
		this.description = description;
		this.gid = gid;
	}

	public Optional<Integer> getGid() {
		return gid;
	}

	public String getDescription() {
		return description;
	}

}
