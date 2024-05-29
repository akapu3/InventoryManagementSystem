import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchasesDAO {

    public void addPurchases(Purchases Purchases) {
        String sql = "INSERT INTO Purchasess(productID, productName, quantity, totalPrice, date) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Purchases.getProductID());
            pstmt.setString(2, Purchases.getProductName());
            pstmt.setInt(3, Purchases.getQuantity());
            pstmt.setDouble(4, Purchases.getTotalPrice());
            pstmt.setString(5, Purchases.getDate());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Purchases> getAllPurchases() {
        String sql = "SELECT * FROM Purchasess";
        List<Purchases> Purchasess = new ArrayList<>();

        try (Connection conn = DatabaseHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Purchases Purchase = new Purchases(
                        rs.getInt("PurchasesID"),
                        rs.getInt("productID"),
                        rs.getString("productName"),
                        rs.getInt("quantity"),
                        rs.getDouble("totalPrice"),
                        rs.getString("date")
                );
                Purchasess.add(Purchase);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Purchasess;
    }

    public void updatePurchases(Purchases Purchases) {
        String sql = "UPDATE Purchasess SET productID = ?, productName = ?, quantity = ?, totalPrice = ?, date = ? WHERE PurchasesID = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Purchases.getProductID());
            pstmt.setString(2, Purchases.getProductName());
            pstmt.setInt(3, Purchases.getQuantity());
            pstmt.setDouble(4, Purchases.getTotalPrice());
            pstmt.setString(5, Purchases.getDate());
            pstmt.setInt(6, Purchases.getPurchaseID());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deletePurchases(int PurchasesID) {
        String sql = "DELETE FROM Purchasess WHERE PurchasesID = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, PurchasesID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
