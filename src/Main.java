import model.Cart;
import model.Customer;
import model.ECommerceService;
import model.product.FoodProduct;
import model.product.Product;
import model.product.ElectronicsProduct;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Setup Store and Service
        ECommerceService eCommerceService = new ECommerceService();

        // Testing the test case provided in the task
        Product cheese = new FoodProduct("Cheese", 100.0, 10, 200, LocalDate.now().plusMonths(6));
        Product biscuits = new FoodProduct("Biscuits", 150.0, 5, 700, LocalDate.now().plusYears(1));

        // Create customer and cart for this transaction.
        Customer customer1 = new Customer("Customer 1", 1000.0);
        Cart cartForCustomer1 = new Cart();

        // Add items to cart to match the receipt in the task PDF
        cartForCustomer1.addProduct(cheese, 2);
        cartForCustomer1.addProduct(biscuits, 1);

        // Perform checkout.
        System.out.println("Customer 1 checkout");
        eCommerceService.checkout(customer1, cartForCustomer1);

        // --- Additional Test Cases for Errors and Corner Cases ---

        // Example: Insufficient Balance
        Customer customer2 = new Customer("Customer 2", 50.0);
        System.out.println("\nCustomer 2 checkout (Insufficient Balance)");
        eCommerceService.checkout(customer2, cartForCustomer1); // Use the same cart as customer 1

        // Example: Product Out of Stock
        Product laptop = new ElectronicsProduct("Laptop", 3000.0, 1, 2500);
        Customer customer3 = new Customer("Customer 3", 5000.0);
        Cart cartForCustomer3 = new Cart();
        System.out.println("\nCustomer 3 adding to cart (Out of Stock)");
        cartForCustomer3.addProduct(laptop, 1); // Should succeed
        cartForCustomer3.addProduct(laptop, 1); // Should fail

        // Example: Expired Product
        Product expiredMilk = new FoodProduct("Milk", 20.0, 50, 1000, LocalDate.now().minusDays(1));
        Customer customer4 = new Customer("Customer 4", 100.0);
        Cart cartForCustomer4 = new Cart();
        cartForCustomer4.addProduct(expiredMilk, 1);
        System.out.println("\nCustomer 4 checkout (Expired Product)");
        eCommerceService.checkout(customer4, cartForCustomer4);

        // Example: Empty Cart
        Customer customer5 = new Customer("Customer 5", 200.0);
        Cart cartForCustomer5 = new Cart(); // Cart is empty
        System.out.println("\nCustomer 5 checkout (Empty Cart)");
        eCommerceService.checkout(customer5, cartForCustomer5);
    }
}