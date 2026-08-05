import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static java.lang.System.out;

public class ProductService
{
  public void viewAllProducts()
  {
      String sql = "SELECT * FROM products";

      try (
          Connection connection = DatabaseConnection.getConnection();
          PreparedStatement statement = connection.prepareStatement(sql);
          ResultSet resultSet = statement.executeQuery();
      ) {
          out.println("\n--- Product ---");

          while (resultSet.next())
          {
              int id = resultSet.getInt("product_id");
              String name = resultSet.getString("name");
              String category = resultSet.getString("category");
              double price = resultSet.getDouble("price");
              int stock = resultSet.getInt("stock_quantity");

              out.println(
                      id + " | " +
                      name + " | " +
                      category + " | $" +
                      price + " | Stock: " +
                      stock
              );
          }
      } catch (Exception e) {
          System.out.println("Error viewing products.");
          e.printStackTrace();
      }

  }

  public void addProduct(String name, String category, double price, int stockQuantity)
  {
      String sql = """ 
                INSERT INTO products (name, category, price, stock_quantity)
                VALUES (?, ?, ?, ?)
                """;
      try (
              Connection connection = DatabaseConnection.getConnection();
              PreparedStatement statement = connection.prepareStatement(sql)
      ){
          statement.setString(1, name);
          statement.setString(2, category);
          statement.setDouble(3, price);
          statement.setInt(4, stockQuantity);

          statement.executeUpdate();

          out.println("Product added successfully!");


      } catch (Exception e) {
          out.println("Error adding product");
          e.printStackTrace();
      }
  }
    public void updateProductStock(int productId, int newStockQuantity) {
        String sql = "UPDATE products SET stock_quantity = ? WHERE product_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, newStockQuantity);
            statement.setInt(2, productId);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Product stock updated successfully!");
            } else {
                System.out.println("No product found with that ID.");
            }

        } catch (Exception e) {
            System.out.println("Error updating product stock.");
            e.printStackTrace();
        }
    }

    public void deleteProduct(int productId) {
        String sql = "DELETE FROM products WHERE product_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, productId);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Product deleted successfully!");
            } else {
                System.out.println("No product found with that ID.");
            }

        } catch (Exception e) {
            System.out.println("Error deleting product.");
            e.printStackTrace();
        }
    }
}
