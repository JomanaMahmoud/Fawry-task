package model;

import Interfaces.Expirable;
import Interfaces.Shippable;
import model.product.Product;

import java.util.LinkedHashMap;
import java.util.Map;

public class ECommerceService {
    private final ShippingService shippingService;
    private static final double SHIPPING_FEE = 45.0; // A flat shipping fee for any shippable order.

    public ECommerceService() {
        this.shippingService = new ShippingService();
    }

    public void checkout(Customer customer, Cart cart) {
        try {
            // Ensure the cart is not empty before proceeding.
            if (cart.isEmpty()) {
                throw new IllegalStateException("Checkout Error: Cart is empty.");
            }

            double subtotal = 0;
            Map<Shippable, Integer> itemsToShip = new LinkedHashMap<>();

            // Loop through cart items to validate and calculate subtotal.
            for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();

                // Fail-safe check to ensure product is in stock.
                if (product.getQuantity() < quantity) {
                    throw new IllegalStateException("Checkout Error: Product '" + product.getName() + "' is out of stock.");
                }
                // Check if an expirable product has expired.
                if (product instanceof Expirable && ((Expirable) product).isExpired()) {
                    throw new IllegalStateException("Checkout Error: Product '" + product.getName() + "' is expired.");
                }

                subtotal += product.getPrice() * quantity;

                // Collect all shippable items for the shipping service.
                if (product instanceof Shippable) {
                    itemsToShip.put((Shippable) product, quantity);
                }
            }

            // Calculate shipping cost (flat fee if there are shippable items).
            double shippingCost = itemsToShip.isEmpty() ? 0 : SHIPPING_FEE;
            double totalAmount = subtotal + shippingCost;

            // Verify the customer has enough money to complete the purchase.
            if (customer.getBalance() < totalAmount) {
                throw new IllegalStateException("Checkout Error: Insufficient balance. Required: " + totalAmount + ", Available: " + customer.getBalance());
            }

            // --- If all checks pass, finalize the transaction ---

            // Deduct the total amount from the customer's balance.
            customer.setBalance(customer.getBalance() - totalAmount);
            // Decrease the stock for each purchased product.
            for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
                entry.getKey().decreaseStockBy(entry.getValue());
            }

            // Send all applicable items to the shipping service.
            shippingService.processShipment(itemsToShip);

            // Print the final receipt for the customer.
            printReceipt(customer, cart, subtotal, shippingCost, totalAmount);

        } catch (IllegalStateException e) {
            // Catch any checkout validation errors and print them.
            System.out.println(e.getMessage());
        }
    }

    // Helper method to display the formatted receipt.
    private void printReceipt(Customer customer, Cart cart, double subtotal, double shippingCost, double totalAmount) {
        System.out.println("\n** Checkout Receipt **");
        for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
            System.out.println(entry.getValue() + "x " + entry.getKey().getName() + "\t\t" + (entry.getKey().getPrice() * entry.getValue()));
        }
        System.out.println("--------------------");
        System.out.println("Subtotal\t" + subtotal);
        System.out.println("Shipping\t" + shippingCost);
        System.out.println("Amount\t\t" + totalAmount);
        System.out.println("\nCustomer balance after payment: " + customer.getBalance());
    }
}