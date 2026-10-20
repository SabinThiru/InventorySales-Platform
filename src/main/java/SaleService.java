import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static java.lang.System.out;

public class SaleService
{
    public void createSale(int customerId, int productId, int quantity)
    {
        if (quantity <= 0)
        {
            System.out.println("Quantity must be greater than zero.");
            return;
        }
        String checkProductSql = """
                SELECT price, stock_quantity
                FROM products
                WHERE product_id = ?
                """;

        String insertSaleSql = """
                INSERT INTO sales (customer_id, total_amount)
                VALUES (?, ?)
                """;

        String insertSaleItemSql = """
                INSERT INTO sale_items (sale_id, product_id, quantity, price_each)
                VALUES (?, ?, ?, ?)
                """;

        String updateStockSql = """
                UPDATE products
                SET stock_quantity = stock_quantity - ?
                WHERE product_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection())
        {
          connection.setAutoCommit(false);

          double price;
          int stock;

          try (PreparedStatement checkProductStatement = connection.prepareStatement(checkProductSql))
          {
              checkProductStatement.setInt(1, productId);

              ResultSet resultSet = checkProductStatement.executeQuery();

              if(!resultSet.next())
              {
                  out.println("Product not found");
                  connection.rollback();
                  return;
              }

              price = resultSet.getDouble("price");
              stock = resultSet.getInt("stock_quantity");
          }

          if (stock < quantity)
          {
              out.println("Not enough stock available.");
              connection.rollback();
              return;
          }

          double totalAmount = price * quantity;
          int saleId;

          try(PreparedStatement insertSaleStatment = connection.prepareStatement(insertSaleSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
              insertSaleStatment.setInt(1, customerId);
              insertSaleStatment.setDouble(2, totalAmount);

              insertSaleStatment.executeUpdate();

              ResultSet generatedKeys = insertSaleStatment.getGeneratedKeys();

              if (!generatedKeys.next()) {
                  System.out.println("Could not create sale.");
                  connection.rollback();
                  return;
              }

              saleId = generatedKeys.getInt(1);
          }

          try (PreparedStatement insertSaleItemStatement = connection.prepareStatement(insertSaleItemSql)) {

                insertSaleItemStatement.setInt(1, saleId);
                insertSaleItemStatement.setInt(2, productId);
                insertSaleItemStatement.setInt(3, quantity);
                insertSaleItemStatement.setDouble(4, price);

                insertSaleItemStatement.executeUpdate();
            }
          try (PreparedStatement updateStockStatement =
                         connection.prepareStatement(updateStockSql)) {

                updateStockStatement.setInt(1, quantity);
                updateStockStatement.setInt(2, productId);

                updateStockStatement.executeUpdate();


          }

            connection.commit();

            System.out.println("Sale completed successfully!");
            System.out.println("Total amount: $" + totalAmount);


        } catch (Exception e) {
            out.println("Error creating sale.");
            e.printStackTrace();
        }
    }
}
