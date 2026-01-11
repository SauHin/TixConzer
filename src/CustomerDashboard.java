import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

public class CustomerDashboard extends JFrame {
    private Customer customer;
    private Venue venue;
    private JLabel lblBalance;
    private JPanel panelSeats;
    private NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public CustomerDashboard(Customer customer) {
        this.customer = customer;
        this.venue = new Venue();

        setTitle("Customer Dashboard - " + customer.getUsername());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        topPanel.setBackground(new Color(240, 240, 240));

        lblBalance = new JLabel("Saldo: " + formatter.format(customer.getBalance()));
        lblBalance.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Topup
        JButton btnTopUp = new JButton("Top Up (+)");
        btnTopUp.addActionListener(e -> showTopUpDialog());
        
        // Logout
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });
        
        // Tiket saya
        JButton btnMyTickets = new JButton("Tiket Saya");
        btnMyTickets.addActionListener(e -> new MyTicketsFrame(customer).setVisible(true));

        JPanel rightTop = new JPanel(new FlowLayout());
        rightTop.setOpaque(false);
        rightTop.add(btnMyTickets);
        rightTop.add(btnTopUp);
        rightTop.add(btnLogout);

        topPanel.add(lblBalance, BorderLayout.WEST);
        topPanel.add(rightTop, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        panelSeats = new JPanel();
        panelSeats.setLayout(new GridLayout(venue.getRow(), venue.getCol(), 5, 5));
        panelSeats.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        loadSeats(); 
        
        add(new JScrollPane(panelSeats), BorderLayout.CENTER);

        JPanel legendPanel = new JPanel();
        legendPanel.add(createLegendLabel("VIP (Kuning)", Color.ORANGE));
        legendPanel.add(createLegendLabel("Regular (Hijau)", Color.GREEN));
        legendPanel.add(createLegendLabel("Booked (Merah)", Color.RED));
        add(legendPanel, BorderLayout.SOUTH);
    }

    private JLabel createLegendLabel(String text, Color color) {
        JLabel lbl = new JLabel(text);
        lbl.setOpaque(true);
        lbl.setBackground(color);
        lbl.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return lbl;
    }

    private void loadSeats() {
        panelSeats.removeAll();
        Seat[][] seats = venue.getSeats();

        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                Seat seat = seats[i][j];
                JButton btnSeat = new JButton(seat.getCode());
                
                if (seat.isBooked()) {
                    btnSeat.setBackground(Color.RED);
                    btnSeat.setEnabled(false);
                } else if (seat.getType().equals("VIP")) {
                    btnSeat.setBackground(Color.ORANGE);
                } else {
                    btnSeat.setBackground(Color.GREEN);
                }

                btnSeat.addActionListener(e -> processBooking(seat, btnSeat));
                
                panelSeats.add(btnSeat);
            }
        }
        panelSeats.revalidate();
        panelSeats.repaint();
    }

    private void processBooking(Seat seat, JButton btn) {
        double price = seat.calculatePrice();
        int choice = JOptionPane.showConfirmDialog(this, 
                "Booking Kursi " + seat.getCode() + "\nTipe: " + seat.getType() + 
                "\nHarga: " + formatter.format(price) + "\n\nLanjutkan pembayaran?", 
                "Konfirmasi Booking", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            if (customer.getBalance() >= price) {
                customer.setBalance(customer.getBalance() - price);
                seat.setBooked(true);
                seat.setOwner(customer.getUsername());           

                venue.updateStatus();
                Database.saveUsers(Database.loadUsers()); 
                updateUserDatabase();

                lblBalance.setText("Saldo: " + formatter.format(customer.getBalance()));
                btn.setBackground(Color.RED);
                btn.setEnabled(false);
                JOptionPane.showMessageDialog(this, "Booking Berhasil!");
            } else {
                JOptionPane.showMessageDialog(this, "Saldo tidak cukup! Silakan Top Up.", "Gagal", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateUserDatabase() {
        java.util.ArrayList<User> allUsers = Database.loadUsers();
        for (int i=0; i<allUsers.size(); i++) {
            if (allUsers.get(i).getUsername().equals(customer.getUsername())) {
                if (allUsers.get(i) instanceof Customer) {
                    ((Customer)allUsers.get(i)).setBalance(customer.getBalance());
                }
            }
        }
        Database.saveUsers(allUsers);
    }

    private void showTopUpDialog() {
        String inputAmount = JOptionPane.showInputDialog(this, "Masukkan nominal Top Up (Contoh: 100000):");
        
        if (inputAmount != null && !inputAmount.isEmpty()) {
            try {
                double amount = Double.parseDouble(inputAmount);
                if (amount <= 0) {
                    JOptionPane.showMessageDialog(this, "Nominal harus lebih dari 0!");
                    return;
                }

                String[] options = {"Transfer Bank", "Kartu Kredit"};
                int choice = JOptionPane.showOptionDialog(this, 
                        "Pilih Metode Pembayaran:", 
                        "Metode Top Up",
                        JOptionPane.DEFAULT_OPTION, 
                        JOptionPane.INFORMATION_MESSAGE, 
                        null, options, options[0]);

                PaymentMethod method = null;

                if (choice == 0) {
                    method = new BankTransferPayment();
                } else if (choice == 1) {
                    method = new CreditCardPayment();
                } else {
                    return;
                }

                double oldBalance = customer.getBalance();
                
                customer.topUp(amount, method);
                
                if (customer.getBalance() > oldBalance) {
                    updateUserDatabase();
                    lblBalance.setText("Saldo: " + formatter.format(customer.getBalance()));
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Input nominal harus berupa angka!");
            }
        }
    }
}