package hudson.plugins.ec2;

public enum VMWareCpuType {

	OnevCpu("1"), TwovCpu("2"), ThreevCpu("3");

	private final String typeId;

	VMWareCpuType(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeId() {
		return typeId;
	}

	public static VMWareCpuType getTypeFromString(String val) {
		for (VMWareCpuType t : VMWareCpuType.values()) {
			if (t.getTypeId().equals(val)) {
				return t;
			}
		}
		return null;
	}

}