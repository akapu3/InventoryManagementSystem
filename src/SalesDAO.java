import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalesDAO {

    public void addSale(Sales sale) {
        String sql = "INSERT INTO Sales(productID, productName, quantity, totalPrice, date) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sale.getProductID());
            pstmt.setString(2, sale.getProductName());
            pstmt.setInt(3, sale.getQuantity());
            pstmt.setDouble(4, sale.getTotalPrice());
            pstmt.setString(5, sale.getDate());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Sales> getAllSales() {
        String sql = "SELECT * FROM Sales";
        List<Sales> sales = new ArrayList<>();

        try (Connection conn = DatabaseHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Sales sale = new Sales(
                        rs.getInt("salesID"),
                        rs.getInt("productID"),
                        rs.getString("productName"),
                        rs.getInt("quantity"),
                        rs.getDouble("totalPrice"),
                        rs.getString("date")
                );
                sales.add(sale);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return sales;
    }

    public void updateSale(Sales sale) {
        String sql = "UPDATE Sales SET productID = ?, productName = ?, quantity = ?, totalPrice = ?, date = ? WHERE salesID = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sale.getProductID());
            pstmt.setString(2, sale.getProductName());
            pstmt.setInt(3, sale.getQuantity());
            pstmt.setDouble(4, sale.getTotalPrice());
            pstmt.setString(5, sale.getDate());
            pstmt.setInt(6, sale.getSalesID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteSale(int salesID) {
        String sql = "DELETE FROM Sales WHERE salesID = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, salesID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
