package model;

import model.product.Product;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    // Stores the products in the cart and their corresponding quantities.
    private Map<Product, Integer> items;

    // Constructor to create a cart with a pre-existing set of items.
    public Cart(Map<Product, Integer> initialItems) {
        this.items = new LinkedHashMap<>(initialItems);
    }

    // Default constructor to create a new, empty cart.
    public Cart(){
        this.items = new LinkedHashMap<>();
    }

    public void addProduct(Product product, int quantity){
        // Do not allow adding a zero or negative quantity.
        if(quantity <= 0){
            System.out.println("Error: quantity must be greater than 0");
            return;
        }
        // Get the current quantity of the product in the cart, or 0 if it's not present.
        int quantityAlreadyAdded = this.items.getOrDefault(product,0);

        // Calculate what the new total quantity would be.
        int desiredTotalQuantity = quantity + quantityAlreadyAdded;

        // Check if the desired total exceeds the available product quantity.
        if(desiredTotalQuantity > product.getQuantity()){
            System.out.println("Error: Cannot add " + quantity + " of '" + product.getName() + "' to cart. " +
                    "You are trying to add " + desiredTotalQuantity + " but only " +
                    product.getQuantity() + " are available in stock."
            );
            return; // Stop if there is not enough stock.
        }
        // Add the requested quantity to the existing quantity in the cart.
        this.items.put(product, this.items.getOrDefault(product, 0) + quantity);
    }

    // Returns all items currently in the cart.
    public Map<Product, Integer> getItems(){
        return this.items;
    }

    // Checks if there are any items in the cart.
    public boolean isEmpty(){
        return this.items.isEmpty();
    }
}