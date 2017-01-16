import java.util.List;

public class HelperReport {

	private Helper helper;
	private List<ChildReport> childReports;

	public List<ChildReport> getChildReports() {
		return childReports;
	}

	public void setChildReports(List<ChildReport> childReports) {
		this.childReports = childReports;
	}

	public Helper getHelper() {
		return helper;
	}

	public void setHelper(Helper helper) {
		this.helper = helper;
	}

	@Override
	public String toString() {
		String childrenReports = childReports.stream().map(childReport -> childReport.toString() + "\n")
				.reduce("", (s1, s2) -> s1 + s2);
		return helper.toString() + "\n" + "Children: " + childrenReports;
	}

}
