# TixConzer App

Aplikasi manajemen tiket konser berbasis Java dengan GUI menggunakan Java Swing dan sistem penyimpanan data berbasis file (CSV).

## Key Features
* **Visual Seat Map:** Pemilihan kursi interaktif menggunakan grid. Status kursi ditandai dengan warna:
    * 🟢 **Green**: Regular Seat (Available)
    * 🟡 **Yellow**: VIP Seat (Available)
    * 🔴 **Red**: Occupied/Booked
* **Multi-Role System:**
    * **Admin:** Memantau venue, membatalkan pesanan, dan melakukan *reset* data.
    * **Customer:** Memesan kursi, *top-up* saldo, dan melihat daftar tiket.
* **Simulated Payment:** Mendukung metode *Bank Transfer* dan *Credit Card* (dengan validasi 16 digit).
* **Database:** Penyimpanan otomatis ke `userData.csv` dan `venueData.csv`.

## Cara Menjalankan Aplikasi
  * **Requirement: :**
    * Java Development Kit (JDK) versi 8 atau terbaru.
    * Java IDE.
    
  * **Langkah-langkah:**
    
    **1. Download:** Pastikan seluruh file .java (16 file) berada dalam satu folder yang sama.

    **2. Compile & Run:**
      * Buka folder project di IDE.
      * Cari file App.java.
      * Klik Run.
        
  * **Akun Default (Login)**
    * **Admin:**
      
      Username: admin
  
      Password: admin
      
    * **Customer:**

      Username: user
      
      Password: user
      
    *(Klik tombol Register untuk membuat akun baru)*

## Daftar Class dan Fungsinya

**1. Main Entry & GUI**

  * *App.java:* Main class untuk menjalankan aplikasi.
  * *LoginFrame.java:* Halaman awal untuk login dan registrasi user.
  * *CustomerDashboard.java:* Menu utama untuk Customer (Booking kursi, Top-up, Lihat saldo).
  * *AdminDashboard.java:* Menu utama untuk Admin (Melihat status kursi venue, Reset data, Batalkan booking).
  * *MyTicketsFrame.java:* Menampilkan daftar tiket yang sudah dibeli oleh user yang sedang login.

**2. Object**

  * *User.java (Abstract):* Template dasar untuk user aplikasi.
  * *Admin.java:* Inheritance dari User, khusus untuk role Admin.
  * *Customer.java:* Inheritance dari User, memiliki atribut tambahan balance (saldo) dan fitur topUp.
  * *Seat.java (Abstract):* Template dasar kursi, dengan kode kursi, status booking, dan pemilik (owner).
  * *RegSeat.java:* Kursi tipe Regular dengan harga standar.
  * *VipSeat.java:* Kursi tipe VIP dengan harga 150% dari harga dasar.

**3. Logic & Database**

  * *Venue.java:* Mengelola mapping kursi konser dan interaksi dengan database.
  * *Database.java:* Menangani pembacaan dan penyimpanan data ke file CSV.

**4. Payment System**

  * *PaymentMethod.java (Interface):* Interface untuk metode pembayaran.
  * *BankTransferPayment.java:* Pembayaran via Transfer Bank.
  * *CreditCardPayment.java:* Pembayaran via Kartu Kredit (Validasi 16 digit).

## OOP Implementation

Aplikasi ini dirancang menggunakan 4 konsep utama OOP:

* **Inheritance**
  
  Konsep di mana sebuah class mewarisi properti class lain.
  * Implementasi: *Admin* dan *Customer* inherit dari class *User*. *VipSeat* dan *RegSeat* inherit dari class Seat.
  * Tujuan: Mengurangi duplikasi kode dan mengelompokkan objek dengan sifat yang sejenis.
    
* **Polymorphism**
  
  Kemampuan objek untuk memiliki perilaku berbeda meski namanya sama.
  
  * Implementasi:
    * Method *calculatePrice()* pada VipSeat (basePrice x1.5) berbeda dengan *RegSeat* (basePrice x1).
    * Interface *PaymentMethod* yang diimplementasikan secara berbeda oleh *BankTransferPayment* dan *CreditCardPayment*.
  * Tujuan: Fleksibilitas dalam menentukan harga dan metode pembayaran tanpa mengubah logika utama.

* **Encapsulation**
  
  Menyembunyikan data sensitif dan hanya mengizinkan akses melalui method tertentu.
  
  * Implementasi: Semua atribut (field) pada class *User*, *Seat*, dan *Venue* bersifat private. Akses dilakukan melalui method Getter dan Setter.
  * Tujuan: Melindungi data agar tidak diubah sembarangan dari luar class (Contoh: saldo tidak bisa diedit langsung tanpa top-up).
* **Abstraction**
  
  Menyembunyikan detail implementasi dan hanya menampilkan fungsionalitas utama.

  * Implementasi:
    * Class *User* dan *Seat* dibuat abstract karena program tidak perlu membuat objek "User" atau "Seat" default, harus spesifik *User Admin/Customer* atau *Seat VIP/Reg*.
    * Interface *PaymentMethod* hanya mendefinisikan bahwa pembayaran harus diproses.
