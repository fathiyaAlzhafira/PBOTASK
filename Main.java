import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("===== Sistem Kasir Supermarket Zhafira =====");
        System.out.println("1. Tambah Barang");
        System.out.println("2. Lihat Semua Barang");
        System.out.println("3. Ubah Barang");
        System.out.println("4. Hapus Barang");
        System.out.println("5. Transaksi");
        System.out.print("Pilih menu: ");
        int pilihan = scanner.nextInt();
        scanner.nextLine(); // Clear buffer


        try {
            switch (pilihan) {
                case 1 -> {
                    System.out.print("Masukkan Kode Barang: ");
                    String kodeBarang = scanner.nextLine();
                    System.out.print("Masukkan Nama Barang: ");
                    String namaBarang = scanner.nextLine();
                    System.out.print("Masukkan Harga Barang: ");
                    double hargaBarang = scanner.nextDouble();


                    Barang barang = new Barang(kodeBarang, namaBarang, hargaBarang);
                    barang.saveToDatabase();
                    System.out.println("Barang berhasil ditambahkan.");
                }
                case 2 -> Barang.readAllFromDatabase();
                case 3 -> {
                    System.out.print("Masukkan Kode Barang yang akan diubah: ");
                    String kodeBarang = scanner.nextLine();
                    System.out.print("Masukkan Nama Barang Baru: ");
                    String namaBarang = scanner.nextLine();
                    System.out.print("Masukkan Harga Barang Baru: ");
                    double hargaBarang = scanner.nextDouble();


                    Barang barang = new Barang(kodeBarang, namaBarang, hargaBarang);
                    barang.updateInDatabase();
                    System.out.println("Barang berhasil diubah.");
                }
                case 4 -> {
                    System.out.print("Masukkan Kode Barang yang akan dihapus: ");
                    String kodeBarang = scanner.nextLine();


                    Barang barang = new Barang(kodeBarang, "", 0);
                    barang.deleteFromDatabase();
                    System.out.println("Barang berhasil dihapus.");
                }
                case 5 -> {
                    System.out.print("Masukkan No Faktur: ");
                    String noFaktur = scanner.nextLine();


                    System.out.print("Masukkan Kode Barang: ");
                    String kodeBarang = scanner.nextLine();
                    System.out.print("Masukkan Jumlah Beli: ");
                    int jumlahBeli = scanner.nextInt();


                    // Fetch Barang from DB based on kodeBarang
                    String query = "SELECT * FROM barang WHERE kode_barang = ?";
                    try (Connection conn = DatabaseConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setString(1, kodeBarang);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                            // If Barang is found, create the Barang object
                            Barang barang = new Barang(rs.getString("kode_barang"), rs.getString("nama_barang"), rs.getDouble("harga_barang"));
                            // Now create the transaction
                            Transaksi transaksi = new Transaksi(barang, jumlahBeli);
                            transaksi.saveToDatabase(noFaktur);
                            System.out.println("Transaksi berhasil disimpan.");
                        } else {
                            System.out.println("Barang dengan kode tersebut tidak ditemukan.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Kesalahan saat mengambil data barang: " + e.getMessage());
                    }
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        } catch (SQLException e) {
            System.out.println("Kesalahan database: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Kesalahan sistem: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
