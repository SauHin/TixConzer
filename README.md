**TixConzer App**
Aplikasi pemesanan tiket konser sederhana berbasis desktop yang dibuat menggunakan Java (OOP) dan Java Swing (GUI). Aplikasi ini memungkinkan user untuk memesan kursi konser, mengelola saldo (Top-up), dan melihat tiket yang sudah dibeli.

Aplikasi ini memiliki fitur:
  - **Visual Seat Map:** Pemilihan kursi menggunakan grid tombol interaktif (Hijau = Kosong, Merah = Terisi, Kuning = VIP).
  - **Multi-Role:** Sistem login untuk **Admin** (pengelola venue) dan **Customer** (pembeli).
  - **Sistem Pembayaran:** Simulasi Top-up saldo menggunakan metode Transfer Bank atau Kartu Kredit.
  - **Database:** Penyimpanan data user dan status kursi menggunakan file CSV (userData.csv dan venueData.csv), sehingga data tidak hilang saat aplikasi ditutup.

**Cara Menjalankan Aplikasi**
**Requirement**
  - Java Development Kit (JDK) versi 8 atau lebih baru.
  - Java IDE.

**Langkah-langkah**
1.  **Download:** Pastikan seluruh file .java (16 file) berada dalam satu folder yang sama.
2.  **Compile & Run:**
      - Buka folder project di IDE.
      - Cari file **App.java**.
      - Klik **Run**.

**Akun Default (Login)**
Saat pertama kali dijalankan, aplikasi akan membuat akun bawaan:
  - **Admin:**
      - Username: admin
      - Password: admin
  - **Customer:**
      - Username: user
      - Password: user
      - *(Klik tombol Register jika inin membuat akun baru)*

**Daftar Class dan Fungsinya**
Berikut adalah class-class yang ada pada code TixConzer:
**1. Main Entry & GUI**
  - **App.java**: Main class untuk menjalankan aplikasi.
  - **LoginFrame.java**: Halaman awal untuk login dan registrasi user.
  - **CustomerDashboard.java**: Menu utama untuk Customer (Booking kursi, Top-up, Lihat saldo).
  - **AdminDashboard.java**: Menu utama untuk Admin (Melihat status kursi venue, Reset data, Batalkan booking).
  - **MyTicketsFrame.java**: Menampilkan daftar tiket yang sudah dibeli oleh user yang sedang login.

**2. Object**
  - **User.java** *(Abstract)*: Template dasar untuk user aplikasi.
  - **Admin.java**: Inheritance dari User, khusus untuk role Admin.
  - **Customer.java**: Inheritance dari User, memiliki atribut tambahan balance (saldo) dan fitur topUp.
  - **Seat.java** *(Abstract)*: Template dasar kursi, dengan kode kursi, status booking, dan pemilik (owner).
  - **RegSeat.java**: Kursi tipe Regular dengan harga standar.
  - **VipSeat.java**: Kursi tipe VIP dengan harga 150% dari harga dasar.

**3. Logic & Database**
  - **Venue.java**: Mengelola mapping kursi konser dan interaksi dengan database.
  - **Database.java**: Menangani pembacaan dan penyimpanan data ke file CSV.

**4. Payment System**
  - **PaymentMethod.java** *(Interface)*: Interface untuk metode pembayaran.
  - **BankTransferPayment.java**: Pembayaran via Transfer Bank.
  - **CreditCardPayment.java**: Pembayaran via Kartu Kredit (Validasi 16 digit).

**Konsep OOP yang Digunakan**

Aplikasi ini menerapkan 4 konsep OOP:

**1. Inheritance**
Konsep di mana sebuah class mewarisi properti class lain.
  - **Implementasi:** Admin dan Customer inherit dari class User. VipSeat dan RegSeat inherit dari class Seat.
  - **Tujuan:** Mengurangi duplikasi kode dan mengelompokkan objek dengan sifat yang sejenis.

**2. Polymorphism**
Kemampuan objek untuk memiliki perilaku berbeda meski namanya sama.
  - **Implementasi:**
      - Method calculatePrice() pada VipSeat (harga x1.5) berbeda dengan RegSeat (harga normal).
      - Interface PaymentMethod yang diimplementasikan secara berbeda oleh BankTransferPayment dan CreditCardPayment.
  - **Tujuan:** Fleksibilitas dalam menentukan harga dan metode pembayaran tanpa mengubah logika utama.

**3. Encapsulation**
Menyembunyikan data sensitif dan hanya mengizinkan akses melalui method tertentu.
  - **Implementasi:** Semua atribut (field) pada class User, Seat, dan Venue bersifat private. Akses dilakukan melalui method Getter dan Setter.
  - **Tujuan:** Melindungi data agar tidak diubah sembarangan dari luar class (misal: saldo tidak bisa diedit langsung tanpa top-up).

**4. Abstraction**
Menyembunyikan detail implementasi dan hanya menampilkan fungsionalitas utama.
  - **Implementasi:**
      - Class User dan Seat dibuat abstract karena program tidak perlu membuat objek "User" atau "Seat" default, harus spesifik Admin/Customer atau VIP/Reg.
      - Interface PaymentMethod hanya mendefinisikan bahwa pembayaran harus diproses. 
