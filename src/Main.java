import model.Cart;
import model.Customer;
import model.ECommerceService;
import model.product.FoodProduct;
import model.product.Product;

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
        cartForCustomer1.add(cheese, 2);
        cartForCustomer1.add(biscuits, 1);

        // Perform checkout.
        eCommerceService.checkout(customer1, cartForCustomer1);

        // Example: Insufficient Balance
        Customer customer2 = new Customer("Customer 2", 50.0);
        eCommerceService.checkout(customer2, cartForCustomer1); // Use the same cart

    }
}
