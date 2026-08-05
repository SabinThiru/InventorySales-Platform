import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportService {

    public void lowStockReport() {
        String sql = """
                SELECT product_id, name, stock_quantity
                FROM products
                WHERE stock_quantity < 10
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            System.out.println("\n--- Low Stock Report ---");

            while (resultSet.next()) {
                int id = resultSet.getInt("product_id");
                String name = resultSet.getString("name");
                int stock = resultSet.getInt("stock_quantity");

                System.out.println(id + " | " + name + " | Stock: " + stock);
            }

        } catch (Exception e) {
            System.out.println("Error generating low stock report.");
            e.printStackTrace();
        }
    }

    public void salesReport() {
        String sql = """
                SELECT
                    s.sale_id,
                    c.first_name,
                    c.last_name,
                    s.sale_date,
                    s.total_amount
                FROM sales s
                JOIN customers c ON s.customer_id = c.customer_id
                ORDER BY s.sale_date DESC
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            System.out.println("\n--- Sales Report ---");

            while (resultSet.next()) {
                int saleId = resultSet.getInt("sale_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String saleDate = resultSet.getString("sale_date");
                double total = resultSet.getDouble("total_amount");

                System.out.println(
                        "Sale #" + saleId +
                                " | " + firstName + " " + lastName +
                                " | " + saleDate +
                                " | $" + total
                );
            }

        } catch (Exception e) {
            System.out.println("Error generating sales report.");
            e.printStackTrace();
        }
    }

    public void bestSellingProductsReport() {
        String sql = """
                SELECT
                    p.name,
                    SUM(si.quantity) AS total_sold
                FROM sale_items si
                JOIN products p ON si.product_id = p.product_id
                GROUP BY p.name
                ORDER BY total_sold DESC
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            System.out.println("\n--- Best Selling Products ---");

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int totalSold = resultSet.getInt("total_sold");

                System.out.println(name + " | Units sold: " + totalSold);
            }

        } catch (Exception e) {
            System.out.println("Error generating best-selling-products report.");
            e.printStackTrace();
        }
    }
}