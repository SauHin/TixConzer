
public abstract class Seat {
	private String code;
	private boolean booked; //booked atau belum
	private double basePrice;
	private String type;
	private String owner;
	
	public Seat(String code, boolean booked, String type, String owner) {
		this.code = code;
		this.booked = booked;
		this.basePrice = 500000;
		this.type = type;
		this.owner = owner;
	}

	public String getCode() {
		return code;
	}

	public boolean isBooked() {
		return booked;
	}

	public void setBooked(boolean booked) {
		this.booked = booked;
	}
	
	public String getType() {
		return type;
	}
	
	public double getBasePrice() {
		return basePrice;
	}

	public abstract double calculatePrice();

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}	
}
