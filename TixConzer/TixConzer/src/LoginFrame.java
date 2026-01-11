import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnRegister;
    private ArrayList<User> userList;

    public LoginFrame() {
        userList = Database.loadUsers();

        setTitle("TixConzer - Login");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Login TixConzer", SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridLayout(2, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        panelForm.add(new JLabel("Username:"));
        txtUsername = new JTextField();
        panelForm.add(txtUsername);
        
        panelForm.add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        panelForm.add(txtPassword);
        
        add(panelForm, BorderLayout.CENTER);

        JPanel panelButton = new JPanel(new FlowLayout());
        btnLogin = new JButton("Login");
        btnRegister = new JButton("Register");
        
        panelButton.add(btnLogin);
        panelButton.add(btnRegister);
        add(panelButton, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> prosesLogin());
        btnRegister.addActionListener(e -> prosesRegister());
    }

    private void prosesLogin() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        boolean found = false;
        User userLogin = null;

        for (User u : userList) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                found = true;
                userLogin = u;
                break;
            }
        }

        if (found) {
        	JOptionPane.showMessageDialog(this, "Login Berhasil! Role: " + userLogin.getRole());
            this.dispose(); 
            
            if(userLogin instanceof Admin) {
                 new AdminDashboard((Admin)userLogin).setVisible(true);
            } else if (userLogin instanceof Customer) {
                 new CustomerDashboard((Customer) userLogin).setVisible(true);
            }
            
        } else {
            JOptionPane.showMessageDialog(this, "Username/Password Salah!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prosesRegister() {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        if(username.isEmpty() || password.isEmpty()){
            JOptionPane.showMessageDialog(this, "Isi username dan password!");
            return;
        }

        for(User u : userList){
            if(u.getUsername().equals(username)){
                JOptionPane.showMessageDialog(this, "Username sudah ada!");
                return;
            }
        }

        userList.add(new Customer(username, password, 0));
        Database.saveUsers(userList);
        JOptionPane.showMessageDialog(this, "Registrasi Sukses! Silakan Login.");
    }
}