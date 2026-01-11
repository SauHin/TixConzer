import java.text.NumberFormat;
import java.util.Locale;

public class Customer extends User {
	Locale indonesia = new Locale("id", "ID");
	NumberFormat formatter = NumberFormat.getCurrencyInstance(indonesia);
	
	private double balance; // saldo customer
	
	public Customer(String username, String password, double balance) {
		super(username, password, "CUSTOMER");
		this.balance = balance;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public void topUp(double amount, PaymentMethod method) {
		if(method.processPayment(amount)) {
			this.balance += amount;
			System.out.println("Transaksi sukses!");
			System.out.println("Balance: Rp " + formatter.format(balance));
		}else {
			System.out.println("Transaksi dibatalkan.");
		}
	}
}
