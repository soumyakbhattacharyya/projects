package hudson.plugins.ec2;

public enum VMWareRamType {

	OneGBRam("1024"), TwoGBRam("2048"), ThreeGBRam("3072"), FourGBRam("4096");

	private final String typeId;

	VMWareRamType(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeId() {
		return typeId;
	}

	public static VMWareRamType getTypeFromString(String val) {
		for (VMWareRamType t : VMWareRamType.values()) {
			if (t.getTypeId().equals(val)) {
				return t;
			}
		}
		return null;
	}

}