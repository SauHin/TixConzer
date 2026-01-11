
public class RegSeat extends Seat {
	
	public RegSeat(String code, boolean booked, String owner) {
		super(code, booked, "REGULAR", owner);
	}

	@Override
	public double calculatePrice() {
		// TODO Auto-generated method stub
		return this.getBasePrice();
	}

}
