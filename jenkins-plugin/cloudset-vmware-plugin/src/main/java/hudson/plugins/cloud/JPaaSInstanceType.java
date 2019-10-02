package hudson.plugins.cloud;

public enum JPaaSInstanceType {

	DEFAULT("m1.small"), LARGE("m1.large"), XLARGE("m1.xlarge"), MICRO("t1.micro"), HIGHCPUMEDIUM("c1.medium"), HIGHCPULARGE("c1.xlarge");

	private final String typeId;

	JPaaSInstanceType(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeId() {
		return typeId;
	}

	public static JPaaSInstanceType getTypeFromString(String val) {
		for (JPaaSInstanceType t : JPaaSInstanceType.values()) {
			if (t.getTypeId().equals(val)) {
				return t;
			}
		}
		return null;
	}

}