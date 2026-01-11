import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    private Admin admin;
    private Venue venue;
    private JPanel panelSeats;

    public AdminDashboard(Admin admin) {
        this.admin = admin;
        this.venue = new Venue();

        setTitle("Admin Dashboard - Control Panel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- HEADER ---
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        topPanel.setBackground(new Color(50, 50, 50));

        JLabel lblTitle = new JLabel("ADMIN MODE");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel rightTop = new JPanel(new FlowLayout());
        rightTop.setOpaque(false);
        
        JButton btnReset = new JButton("RESET ALL VENUE");
        btnReset.setBackground(Color.RED);
        btnReset.setForeground(Color.WHITE);
        btnReset.addActionListener(e -> resetVenueAction());

        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            this.dispose();
        });

        rightTop.add(btnReset);
        rightTop.add(btnLogout);

        topPanel.add(lblTitle, BorderLayout.WEST);
        topPanel.add(rightTop, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        panelSeats = new JPanel();
        panelSeats.setLayout(new GridLayout(venue.getRow(), venue.getCol(), 5, 5));
        panelSeats.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        loadSeats();
        
        add(new JScrollPane(panelSeats), BorderLayout.CENTER);

        JLabel infoLabel = new JLabel("Klik kursi merah untuk membatalkan booking secara paksa.", SwingConstants.CENTER);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        add(infoLabel, BorderLayout.SOUTH);
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
                    btnSeat.setForeground(Color.WHITE);
                } else if (seat.getType().equals("VIP")) {
                    btnSeat.setBackground(Color.ORANGE);
                    btnSeat.setForeground(Color.BLACK);
                } else {
                    btnSeat.setBackground(Color.GREEN);
                    btnSeat.setForeground(Color.BLACK);
                }

                btnSeat.addActionListener(e -> {
                    if (seat.isBooked()) {
                        int confirm = JOptionPane.showConfirmDialog(this, 
                            "Batalkan booking untuk kursi " + seat.getCode() + "?", 
                            "Force Cancel", JOptionPane.YES_NO_OPTION);
                        
                        if (confirm == JOptionPane.YES_OPTION) {
                            seat.setBooked(false);
                            venue.updateStatus();
                            loadSeats();
                            JOptionPane.showMessageDialog(this, "Kursi berhasil dibuka kembali.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Kursi ini kosong (Available).");
                    }
                });

                panelSeats.add(btnSeat);
            }
        }
        panelSeats.revalidate();
        panelSeats.repaint();
    }

    private void resetVenueAction() {
        int confirm = JOptionPane.showConfirmDialog(this, 
                "PERINGATAN: Ini akan menghapus SEMUA booking!\nLanjutkan?", 
                "Reset Venue", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            venue.resetVenue();
            loadSeats();
            JOptionPane.showMessageDialog(this, "Seluruh venue berhasil direset!");
        }
    }
}