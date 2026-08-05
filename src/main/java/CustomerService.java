import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static java.lang.System.out;

public class CustomerService
{
  public void viewAllCustomers()
  {
      String sql = "SELECT * FROM customers";
      try (
              Connection connection = DatabaseConnection.getConnection();
              PreparedStatement statement = connection.prepareStatement(sql);
              ResultSet resultSet = statement.executeQuery()
      ) {
          out.println("\n--- Customers ---");

          while (resultSet.next())
          {
              int id = resultSet.getInt("customer_id");
              String firstName = resultSet.getString("first_name");
              String lastName = resultSet.getString("last_name");
              String email = resultSet.getString("email");

              System.out.println(
                      id + " | " +
                              firstName + " " +
                              lastName + " | " +
                              email
              );
          }
      } catch (Exception e) {
          out.println("Error viewing customers");
          e.printStackTrace();
      }
  }

  public void addCustomer(String firstName, String lastName, String email)
  {
      String sql = """
               INSERT INTO customers (first_name, last_name, email)
               VALUES (?, ?, ?)
               """;
      try (
              Connection connection = DatabaseConnection.getConnection();
              PreparedStatement statement = connection.prepareStatement(sql)
              ) {
          statement.setString(1, firstName);
          statement.setString(2, lastName);
          statement.setString(3, email);

          statement.executeUpdate();

          out.println("Customer added sucessfully!");
      } catch (Exception e) {
          out.println("Error adding customer");
          e.printStackTrace();
      }
  }
}
