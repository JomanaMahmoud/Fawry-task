package model;
import Interfaces.Expirable;
import Interfaces.Shippable;
import model.product.Product;

import java.util.LinkedHashMap;
import java.util.Map;

public class ECommerceService {
    private final ShippingService shippingService;
    private static final double SHIPPING_FEE = 45.0; // Assumption: A flat shipping fee.

    public ECommerceService() {
        this.shippingService = new ShippingService();
    }

    public void checkout(Customer customer, Cart cart) {
        try {
            // 1. Validate Cart is not empty
            if (cart.isEmpty()) {
                throw new IllegalStateException("Checkout Error: Cart is empty.");
            }

            double subtotal = 0;
            Map<Shippable, Integer> itemsToShip = new LinkedHashMap<>();

            // 2. Validate items and calculate subtotal
            for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();

                if (product.getQuantity() < quantity) {
                    throw new IllegalStateException("Checkout Error: Product '" + product.getName() + "' is out of stock.");
                }
                if (product instanceof Expirable && ((Expirable) product).isExpired()) {
                    throw new IllegalStateException("Checkout Error: Product '" + product.getName() + "' is expired.");
                }

                subtotal += product.getPrice() * quantity;
                if (product instanceof Shippable) {
                    itemsToShip.put((Shippable) product, quantity);
                }
            }

            // 3. Calculate shipping and total amount
            double shippingCost = itemsToShip.isEmpty() ? 0 : SHIPPING_FEE;
            double totalAmount = subtotal + shippingCost;

            // 4. Validate customer balance
            if (customer.getBalance() < totalAmount) {
                throw new IllegalStateException("Checkout Error: Insufficient balance. Required: " + totalAmount + ", Available: " + customer.getBalance());
            }

            // 5. --- SUCCESS --- Process payment and update stock
            customer.setBalance(customer.getBalance() - totalAmount);
            for (Map.Entry<Product, Integer> entry : cart.getItems().entrySet()) {
                entry.getKey().decreaseStockBy(entry.getValue());
            }

            // 6. Send items to shipping service
            shippingService.processShipment(itemsToShip);

            // 7. Print receipt
            printReceipt(customer, cart, subtotal, shippingCost, totalAmount);

        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

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