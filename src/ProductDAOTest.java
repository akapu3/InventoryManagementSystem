import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ProductDAOTest {
    private ProductDAO productDAO;

    @BeforeEach
    public void setUp() {
        productDAO = new ProductDAO();
        try (Connection conn = DatabaseHelper.connect()) {
            conn.createStatement().execute("DROP TABLE IF EXISTS Products");
            DatabaseHelper.createTables();
        } catch (SQLException e) {
            fail("Failed to set up test database.");
        }
    }

    @Test
    public void testAddAndGetAllProducts() {
        Product product = new Product(0, "Test Product", 10, 19.99);
        productDAO.addProduct(product);
        assertEquals(1, productDAO.getAllProducts().size());
    }

    @Test
    public void testUpdateProduct() {
        Product product = new Product(0, "Test Product", 10, 19.99);
        productDAO.addProduct(product);
        Product retrievedProduct = productDAO.getAllProducts().get(0);

        retrievedProduct.setName("Updated Product");
        retrievedProduct.setQuantity(20);
        retrievedProduct.setPrice(29.99);
        productDAO.updateProduct(retrievedProduct);

        Product updatedProduct = productDAO.getAllProducts().get(0);
        assertEquals("Updated Product", updatedProduct.getName());
        assertEquals(20, updatedProduct.getQuantity());
        assertEquals(29.99, updatedProduct.getPrice());
    }

    @Test
    public void testDeleteProduct() {
        Product product = new Product(0, "Test Product", 10, 19.99);
        productDAO.addProduct(product);
        Product retrievedProduct = productDAO.getAllProducts().get(0);

        productDAO.deleteProduct(retrievedProduct.getProductID());
        assertEquals(0, productDAO.getAllProducts().size());
    }
}
