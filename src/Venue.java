
public class Venue {
	private Seat[][] seats;
	private int row = 10;
	private int col = 10;
	
	public Seat getSeat(String seatCode) {
		for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (seats[i][j].getCode().equalsIgnoreCase(seatCode)) {
                    return seats[i][j];
                }
            }
        }
        return null;
	}
	
	public Venue() {
        this.seats = Database.loadVenue(row, col);
    }
	
	public void updateStatus() {
        Database.saveSeats(seats);
    }
	
	public void resetVenue() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                seats[i][j].setBooked(false);
            }
        }
        updateStatus();
    }
	
	public Seat[][] getSeats() {
	    return this.seats;
	}

	public int getRow() {
	    return this.row;
	}

	public int getCol() {
	    return this.col;
	}
}
