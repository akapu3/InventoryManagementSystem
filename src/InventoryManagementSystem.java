import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;



import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class InventoryManagementSystem extends Application {
    private TableView<Product> productTable;
    private TableView<Sales> salesTable;
    private TableView<Purchases> purchasesTable;
    private ProductDAO productDAO;
    private SalesDAO salesDAO;
    private PurchasesDAO purchasesDAO;

    private TextField productNameField;
    private TextField productQuantityField;
    private TextField productPriceField;

    private TextField salesProductNameField;
    private TextField salesQuantityField;
    private TextField salesTotalPriceField;
    private TextField salesDateField;

    private TextField purchasesProductNameField;
    private TextField purchasesQuantityField;
    private TextField purchasesTotalPriceField;
    private TextField purchasesDateField;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inventory Management System");

        productDAO = new ProductDAO();
        salesDAO = new SalesDAO();
        purchasesDAO = new PurchasesDAO();
        DatabaseHelper.createTables();

        // Product Table
        productTable = createProductTable();
        refreshProductTable();

        // Sales Table
        salesTable = createSalesTable();
        refreshSalesTable();

        // Purchases Table
        purchasesTable = createPurchasesTable();
        refreshPurchasesTable();

        // Layout
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.getChildren().addAll(productTable, salesTable, purchasesTable);

        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TableView<Product> createProductTable() {
        TableView<Product> table = new TableView<>();
        table.setEditable(true);

        // Columns

        // Add Product Form
        productNameField = new TextField();
        productNameField.setPromptText("Product Name");
        productQuantityField = new TextField();
        productQuantityField.setPromptText("Quantity");
        productPriceField = new TextField();
        productPriceField.setPromptText("Price");

        Button addProductButton = new Button("Add Product");
        addProductButton.setOnAction(e -> {
            String name = productNameField.getText();
            int quantity = Integer.parseInt(productQuantityField.getText());
            double price = Double.parseDouble(productPriceField.getText());
            productDAO.addProduct(new Product(0, name, quantity, price));
            refreshProductTable();
            clearProductFields();
        });

        Button editProductButton = new Button("Edit Product");
        editProductButton.setOnAction(e -> {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                String name = productNameField.getText();
                int quantity = Integer.parseInt(productQuantityField.getText());
                double price = Double.parseDouble(productPriceField.getText());
                selectedProduct.setName(name);
                selectedProduct.setQuantity(quantity);
                selectedProduct.setPrice(price);
                productDAO.updateProduct(selectedProduct);
                refreshProductTable();
                clearProductFields();
            } else {
                showAlert("No Product Selected", "Please select a product to edit.");
            }
        });

        Button deleteProductButton = new Button("Delete Product");
        deleteProductButton.setOnAction(e -> {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                productDAO.deleteProduct(selectedProduct.getProductID());
                refreshProductTable();
                clearProductFields();
            } else {
                showAlert("No Product Selected", "Please select a product to delete.");
            }
        });

        HBox productForm = new HBox();
        productForm.setSpacing(10);
        productForm.getChildren().addAll(productNameField, productQuantityField, productPriceField, addProductButton, editProductButton, deleteProductButton);

        VBox productLayout = new VBox();
        productLayout.getChildren().addAll(new Label("Products"), table, productForm);
        return table;
    }

    private void refreshProductTable() {
        productTable.getItems().clear();
        List<Product> products = productDAO.getAllProducts();
        productTable.getItems().addAll(products);
    }

    private void clearProductFields() {
        productNameField.clear();
        productQuantityField.clear();
        productPriceField.clear();
    }

    private TableView<Sales> createSalesTable() {
        TableView<Sales> table = new TableView<>();
        table.setEditable(true);

        // Columns

        // Add Sales Form
        salesProductNameField = new TextField();
        salesProductNameField.setPromptText("Product Name");
        salesQuantityField = new TextField();
        salesQuantityField.setPromptText("Quantity");
        salesTotalPriceField = new TextField();
        salesTotalPriceField.setPromptText("Total Price");
        salesDateField = new TextField();
        salesDateField.setPromptText("Date");

        Button addSalesButton = new Button("Add Sale");
        addSalesButton.setOnAction(e -> {
            String name = salesProductNameField.getText();
            int quantity = Integer.parseInt(salesQuantityField.getText());
            double totalPrice = Double.parseDouble(salesTotalPriceField.getText());
            String date = salesDateField.getText();
            salesDAO.addSale(new Sales(0, 0, name, quantity, totalPrice, date));
            refreshSalesTable();
            clearSalesFields();
        });

        HBox salesForm = new HBox();
        salesForm.setSpacing(10);
        salesForm.getChildren().addAll(salesProductNameField, salesQuantityField, salesTotalPriceField, salesDateField, addSalesButton);

        VBox salesLayout = new VBox();
        salesLayout.getChildren().addAll(new Label("Sales"), table, salesForm);
        return table;
    }

    private void refreshSalesTable() {
        salesTable.getItems().clear();
        List<Sales> sales = salesDAO.getAllSales();
        salesTable.getItems().addAll(sales);
    }

    private void clearSalesFields() {
        salesProductNameField.clear();
        salesQuantityField.clear();
        salesTotalPriceField.clear();
        salesDateField.clear();
    }

    private TableView<Purchases> createPurchasesTable() {
        TableView<Purchases> table = new TableView<>();
        table.setEditable(true);

        // Columns

        // Add Purchases Form
        purchasesProductNameField = new TextField();
        purchasesProductNameField.setPromptText("Product Name");
        purchasesQuantityField = new TextField();
        purchasesQuantityField.setPromptText("Quantity");
        purchasesTotalPriceField = new TextField();
        purchasesTotalPriceField.setPromptText("Total Price");
        purchasesDateField = new TextField();
        purchasesDateField.setPromptText("Date");

        Button addPurchasesButton = new Button("Add Purchase");
        addPurchasesButton.setOnAction(e -> {
            String name = purchasesProductNameField.getText();
            int quantity = Integer.parseInt(purchasesQuantityField.getText());
            double totalPrice = Double.parseDouble(purchasesTotalPriceField.getText());
            String date = purchasesDateField.getText();
            purchasesDAO.addPurchases(new Purchases(0, 0, name, quantity, totalPrice, date));
            refreshPurchasesTable();
            clearPurchasesFields();
        });

        HBox purchasesForm = new HBox();
        purchasesForm.setSpacing(10);
        purchasesForm.getChildren().addAll(purchasesProductNameField, purchasesQuantityField, purchasesTotalPriceField, purchasesDateField, addPurchasesButton);

        VBox purchasesLayout = new VBox();
        purchasesLayout.getChildren().addAll(new Label("Purchases"), table, purchasesForm);
        return table;
    }

    private void refreshPurchasesTable() {
        purchasesTable.getItems().clear();
        List<Purchases> purchases = purchasesDAO.getAllPurchases();
        purchasesTable.getItems().addAll(purchases);
    }

    private void clearPurchasesFields() {
        purchasesProductNameField.clear();
        purchasesQuantityField.clear();
        purchasesTotalPriceField.clear();
        purchasesDateField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
