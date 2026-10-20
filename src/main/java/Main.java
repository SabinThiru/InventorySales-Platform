import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ProductService productService = new ProductService();
        CustomerService customerService = new CustomerService();
        SaleService saleService = new SaleService();
        ReportService reportService = new ReportService();

        boolean running = true;

        while (running) {
            System.out.println("\n===== Inventory & Sales System =====");
            System.out.println("1. View all products");
            System.out.println("2. Add product");
            System.out.println("3. Update product stock");
            System.out.println("4. Delete product");
            System.out.println("5. View all customers");
            System.out.println("6. Add customer");
            System.out.println("7. Create sale");
            System.out.println("8. Low stock report");
            System.out.println("9. Sales report");
            System.out.println("10. Best-selling products report");
            System.out.println("11. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    productService.viewAllProducts();
                    break;

                case 2:
                    System.out.print("Enter product name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();

                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();

                    System.out.print("Enter stock quantity: ");
                    int stock = scanner.nextInt();
                    scanner.nextLine();

                    productService.addProduct(name, category, price, stock);
                    break;

                case 3:
                    System.out.print("Enter product ID: ");
                    int updateProductId = scanner.nextInt();

                    System.out.print("Enter new stock quantity: ");
                    int newStockQuantity = scanner.nextInt();
                    scanner.nextLine();

                    productService.updateProductStock(updateProductId, newStockQuantity);
                    break;

                case 4:
                    System.out.print("Enter product ID to delete: ");
                    int deleteProductId = scanner.nextInt();
                    scanner.nextLine();

                    productService.deleteProduct(deleteProductId);
                    break;

                case 5:
                    customerService.viewAllCustomers();
                    break;

                case 6:
                    System.out.print("Enter first name: ");
                    String firstName = scanner.nextLine();

                    System.out.print("Enter last name: ");
                    String lastName = scanner.nextLine();

                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();

                    customerService.addCustomer(firstName, lastName, email);
                    break;

                case 7:
                    System.out.print("Enter customer ID: ");
                    int customerId = scanner.nextInt();

                    System.out.print("Enter product ID: ");
                    int saleProductId = scanner.nextInt();

                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();


                    saleService.createSale(customerId, saleProductId, quantity);

                    break;

                case 8:
                    reportService.lowStockReport();
                    break;

                case 9:
                    reportService.salesReport();
                    break;

                case 10:
                    reportService.bestSellingProductsReport();
                    break;

                case 11:
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }
}