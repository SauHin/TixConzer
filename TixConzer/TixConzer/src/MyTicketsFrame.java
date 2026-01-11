import javax.swing.*;
import java.awt.*;

public class MyTicketsFrame extends JFrame {
    
    public MyTicketsFrame(Customer customer) {
        setTitle("Tiket Saya - " + customer.getUsername());
        setSize(400, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel lblHeader = new JLabel("Daftar E-Ticket Anda", SwingConstants.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 18));
        lblHeader.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(lblHeader, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        Venue venue = new Venue();
        Seat[][] seats = venue.getSeats();
        boolean hasTicket = false;

        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                Seat s = seats[i][j];

                if (s.isBooked() && s.getOwner() != null && s.getOwner().equals(customer.getUsername())) {
                    contentPanel.add(createTicketCard(s));
                    contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                    hasTicket = true;
                }
            }
        }

        if (!hasTicket) {
            JLabel emptyLabel = new JLabel("Anda belum memiliki tiket aktif.", SwingConstants.CENTER);
            JLabel hintLabel = new JLabel("Silakan pesan di menu Dashboard.", SwingConstants.CENTER);
            
            JPanel emptyPanel = new JPanel(new GridLayout(2, 1));
            emptyPanel.add(emptyLabel);
            emptyPanel.add(hintLabel);
            contentPanel.add(emptyPanel);
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        JLabel footer = new JLabel("<html><center>Tunjukkan layar ini kepada petugas<br>saat masuk ke area konser.</center></html>", SwingConstants.CENTER);
        footer.setFont(new Font("SansSerif", Font.ITALIC, 12));
        footer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        footer.setOpaque(true);
        footer.setBackground(new Color(255, 255, 200));
        add(footer, BorderLayout.SOUTH);
    }

    private JPanel createTicketCard(Seat seat) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(350, 80));

        JLabel lblCode = new JLabel(seat.getCode(), SwingConstants.CENTER);
        lblCode.setFont(new Font("Arial", Font.BOLD, 24));
        lblCode.setPreferredSize(new Dimension(80, 80));
        lblCode.setOpaque(true);

        if (seat.getType().equals("VIP")) {
            lblCode.setBackground(Color.ORANGE);
        } else {
            lblCode.setBackground(Color.GREEN);
        }

        JPanel detailPanel = new JPanel(new GridLayout(2, 1));
        detailPanel.setOpaque(false);
        detailPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));
        
        detailPanel.add(new JLabel("Tipe: " + seat.getType()));
        detailPanel.add(new JLabel("Status: LUNAS"));

        card.add(lblCode, BorderLayout.WEST);
        card.add(detailPanel, BorderLayout.CENTER);

        return card;
    }
}