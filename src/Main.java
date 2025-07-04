import model.Cart;
import model.Customer;
import model.ECommerceService;
import model.product.FoodProduct;
import model.product.Product;
import model.product.ElectronicsProduct;
import model.product.DigitalProduct; // Added for the new test case

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

        // Example: Checkout with only non-shippable items
        Product scratchCard = new DigitalProduct("Scratch Card", 10.0, 100);
        Customer customer6 = new Customer("Customer 6", 100.0);
        Cart cartForCustomer6 = new Cart();
        cartForCustomer6.addProduct(scratchCard, 3);
        System.out.println("\nCustomer 6 checkout (Digital Only - No Shipping)");
        eCommerceService.checkout(customer6, cartForCustomer6);

        // Example: Customer has the exact balance required
        Product headphones = new ElectronicsProduct("Headphones", 100.0, 10, 500);
        // Assuming shipping is 45.0, total is 145.0
        Customer customer7 = new Customer("Customer 7", 145.0);
        Cart cartForCustomer7 = new Cart();
        cartForCustomer7.addProduct(headphones, 1);
        System.out.println("\nCustomer 7 checkout (Exact Balance)");
        eCommerceService.checkout(customer7, cartForCustomer7);

        // Example: Adding zero or negative quantity to cart
        Customer customer8 = new Customer("Customer 8", 500.0);
        Cart cartForCustomer8 = new Cart();
        System.out.println("\nCustomer 8 adding to cart (Zero/Negative Quantity)");
        cartForCustomer8.addProduct(cheese, 0);  // Should print an error
        cartForCustomer8.addProduct(cheese, -5); // Should print an error
    }
}