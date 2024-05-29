import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseHelper {
    private static final String URL = "jdbc:sqlite:inventory.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void createTables() {
        String createProductsTable = "CREATE TABLE IF NOT EXISTS Products ("
                + "productID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL, "
                + "quantity INTEGER NOT NULL, "
                + "price REAL NOT NULL);";

        String createSalesTable = "CREATE TABLE IF NOT EXISTS Sales ("
                + "saleID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "productID INTEGER, "
                + "quantity INTEGER, "
                + "date TEXT, "
                + "FOREIGN KEY(productID) REFERENCES Products(productID));";

        String createPurchasesTable = "CREATE TABLE IF NOT EXISTS Purchases ("
                + "purchaseID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "productID INTEGER, "
                + "quantity INTEGER, "
                + "date TEXT, "
                + "FOREIGN KEY(productID) REFERENCES Products(productID));";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createProductsTable);
            stmt.execute(createSalesTable);
            stmt.execute(createPurchasesTable);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
