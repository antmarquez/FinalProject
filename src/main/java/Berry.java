

public class Berry {

	String berryName;
	int berrySize;
	int berryGrowthTime;

	public Berry(String berryName, int berrySize, int berryGrowthTime) {
		super();
		this.berryName = berryName;
		this.berrySize = berrySize;
		this.berryGrowthTime = berryGrowthTime;
	}

	public String getBerryName() {
		return berryName;
	}

	public int getBerrySize() {
		return berrySize;
	}

	public int getBerryGrowthTime() {
		return berryGrowthTime;
	}

	
	@Override
	public String toString() {
		return "Berry " + berryName +" (size: " + berrySize + ", growth time: "
				+ berryGrowthTime + " hours).";
	}

}