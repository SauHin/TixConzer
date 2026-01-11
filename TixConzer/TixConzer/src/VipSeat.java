
public class VipSeat extends Seat {

	public VipSeat(String code, boolean booked, String owner) {
		super(code, booked, "VIP", owner);
	}


	@Override
	public double calculatePrice() {
		// TODO Auto-generated method stub
		return this.getBasePrice() * 1.5; // 150% base price
	}

}
