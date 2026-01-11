import javax.swing.JOptionPane;
import java.text.NumberFormat;
import java.util.Locale;

public class CreditCardPayment implements PaymentMethod {
    Locale indonesia = new Locale("id", "ID");
    NumberFormat formatter = NumberFormat.getCurrencyInstance(indonesia);
    
    @Override
    public boolean processPayment(double amount) {
        String ccNum = JOptionPane.showInputDialog(null, 
                "Tagihan: " + formatter.format(amount) + "\nMasukkan 16 digit Nomor Kartu Kredit:",
                "Pembayaran Kartu Kredit", JOptionPane.QUESTION_MESSAGE);
        
        if (ccNum == null) {
            return false;
        }

        if(ccNum.length() == 16 && ccNum.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(null, "Kartu Valid! Pembayaran Berhasil.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Nomor Kartu Tidak Valid (Harus 16 angka)!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }            
}