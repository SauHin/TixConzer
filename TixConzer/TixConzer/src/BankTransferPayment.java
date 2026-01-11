import javax.swing.JOptionPane;
import java.text.NumberFormat;
import java.util.Locale;

public class BankTransferPayment implements PaymentMethod {
    Locale indonesia = new Locale("id", "ID");
    NumberFormat formatter = NumberFormat.getCurrencyInstance(indonesia);
    
    @Override
    public boolean processPayment(double amount) {
        String message = "Silahkan transfer ke rekening berikut:\n" +
                         "Bank BCA: 8888-9999-0000\n" +
                         "Nominal: " + formatter.format(amount) + "\n\n" +
                         "Apakah anda sudah melakukan transfer?";
                         
        int choice = JOptionPane.showConfirmDialog(null, message, 
                "Konfirmasi Transfer Bank", JOptionPane.YES_NO_OPTION);
        
        if(choice == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Pembayaran via Transfer Bank Berhasil!");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Pembayaran Dibatalkan.");
            return false;
        }
    }
}