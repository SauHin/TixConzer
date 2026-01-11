import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {
	private static final String usersFile = "userData.csv";
    private static final String venueFile = "venueData.csv";
    
    public static ArrayList<User> loadUsers() {
        ArrayList<User> users = new ArrayList<>();
        File file = new File(usersFile);
        
        //dummy
        if (!file.exists()) {
            users.add(new Admin("admin", "admin"));
            users.add(new Customer("user", "user", 0));
            saveUsers(users);
            return users;
        }

        try (Scanner scanFile = new Scanner(file)) {
            while (scanFile.hasNextLine()) {
                String line = scanFile.nextLine();
                String[] parts = line.split(",");
                if (parts[0].equals("ADMIN")) {
                    users.add(new Admin(parts[1], parts[2]));
                } else if (parts[0].equals("CUSTOMER")) {
                    double balance = Double.parseDouble(parts[3]);
                    users.add(new Customer(parts[1], parts[2], balance));
                }
            }
        }catch (Exception e) {
            System.out.println("Error load users: " + e.getMessage());
        }
        return users;
    }
    
    public static void saveUsers(ArrayList<User> users) {
        try (PrintWriter writer = new PrintWriter(new File(usersFile))) { 	
        	for(int i = 0; i<users.size(); i++) {
        		User u = users.get(i);
        		if (u instanceof Admin) {
                    writer.println("ADMIN," + u.getUsername() + "," + u.getPassword());
                } else if (u instanceof Customer) {
                    writer.println("CUSTOMER," + u.getUsername() + "," + u.getPassword() + "," + ((Customer)u).getBalance());
                }
        	}
        } catch (FileNotFoundException e) {
            System.out.println("Error saving users data: " + e.getMessage());
        }
    }
    
    public static Seat[][] loadVenue(int row, int col) {
        Seat[][] venue = new Seat[row][col];
        File file = new File(venueFile);

        if (!file.exists()) {
        	Seat[][] newVenue = generateNewVenue(row, col);
            saveSeats(newVenue);     
            return newVenue;
        }

        try (Scanner scanFile = new Scanner(file)) {
            while (scanFile.hasNextLine()) {
                String line = scanFile.nextLine();
                String[] parts = line.split(",");

                int r = Integer.parseInt(parts[0]);
                int c = Integer.parseInt(parts[1]);
                String type = parts[2];
                boolean isBooked = Boolean.parseBoolean(parts[3]);

                String owner = null;
                if (parts.length > 4 && !parts[4].equals("null")) {
                    owner = parts[4];
                }

                String seatCode = "" + (char)('A' + r) + c;

                if (type.equals("VIP")) {
                    venue[r][c] = new VipSeat(seatCode, isBooked, owner);
                } else {
                    venue[r][c] = new RegSeat(seatCode, isBooked, owner);
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading venue data, generating new: " + e.getMessage());
            return generateNewVenue(row, col);
        }
        return venue;
    }
    
    private static Seat[][] generateNewVenue(int row, int col) {
        Seat[][] venue = new Seat[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                String seatCode = "" + (char)('A' + i) + j;
                if (i < 2) {
                	venue[i][j] = new VipSeat(seatCode, false, null);
                }else {
                	venue[i][j] = new RegSeat(seatCode, false, null);
                }
            }
        }
        return venue;
    }

    public static void saveSeats(Seat[][] venue) {
        try (PrintWriter writer = new PrintWriter(new File(venueFile))) {
            for (int i = 0; i < venue.length; i++) {
                for (int j = 0; j < venue[i].length; j++) {
                    Seat s = venue[i][j];

                    String ownerData = (s.getOwner() == null) ? "null" : s.getOwner();

                    writer.println(i + "," + j + "," + s.getType() + "," + s.isBooked() + "," + ownerData);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error saving seats.");
        }
    }
}
