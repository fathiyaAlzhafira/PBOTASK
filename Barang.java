import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Barang {
    String kodeBarang;
    String namaBarang;
    double hargaBarang;


    public void saveToDatabase() throws SQLException {
    Connection connection = DatabaseConnection.getConnection();
    String query = "INSERT INTO barang (kode_barang, nama_barang, harga_barang) VALUES (?, ?, ?)";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.setString(1, kodeBarang);
    statement.setString(2, namaBarang);
    statement.setDouble(3, hargaBarang);
    statement.executeUpdate();
    statement.close();
    connection.close();
}


public static void readAllFromDatabase() throws SQLException {
    Connection connection = DatabaseConnection.getConnection();
    String query = "SELECT * FROM barang";
    PreparedStatement statement = connection.prepareStatement(query);
    ResultSet resultSet = statement.executeQuery();


    while (resultSet.next()) {
        System.out.println("Kode Barang: " + resultSet.getString("kode_barang"));
        System.out.println("Nama Barang: " + resultSet.getString("nama_barang"));
        System.out.println("Harga Barang: " + resultSet.getDouble("harga_barang"));
        System.out.println("==================================");
    }


    resultSet.close();
    statement.close();
    connection.close();
}


public void updateInDatabase() throws SQLException {
    Connection connection = DatabaseConnection.getConnection();
    String query = "UPDATE barang SET nama_barang = ?, harga_barang = ? WHERE kode_barang = ?";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.setString(1, namaBarang);
    statement.setDouble(2, hargaBarang);
    statement.setString(3, kodeBarang);
    statement.executeUpdate();
    statement.close();
    connection.close();
}


public void deleteFromDatabase() throws SQLException {
    Connection connection = DatabaseConnection.getConnection();
    String query = "DELETE FROM barang WHERE kode_barang = ?";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.setString(1, kodeBarang);
    statement.executeUpdate();
    statement.close();
    connection.close();
}
    public Barang(String kodeBarang, String namaBarang, double hargaBarang) {
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.hargaBarang = hargaBarang;
    }




    public void displayBarang() {
        System.out.println("Kode Barang: " + kodeBarang);
        System.out.println("Nama Barang: " + namaBarang);
        System.out.println("Harga Barang: " + hargaBarang);
    }




    public String getCurrentDateTime() { //penggunaan metode String dan Date
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return formatter.format(date);
    }
}
