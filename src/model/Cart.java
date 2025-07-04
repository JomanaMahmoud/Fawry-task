package model;

import model.product.Product;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> items;

    public Cart(Map<Product, Integer> initialItems) {
        this.items = new LinkedHashMap<>(initialItems);
    }

    public Cart(){
        this.items = new LinkedHashMap<>();
    }

    public void add(Product product, int quantity){
        if(quantity <= 0){
            System.err.println("Error: quantity must be greater than 0");
            return;
        }
        int quantityAlreadyAdded = this.items.getOrDefault(product,0);

        int desiredTotalQuantity = quantity + quantityAlreadyAdded;

        if(desiredTotalQuantity > product.getQuantity()){
            System.err.println("Error: Cannot add " + quantity + " of '" + product.getName() + "' to cart. " +
                            "You are trying to add " + desiredTotalQuantity + " but only " +
                            product.getQuantity() + " are available in stock."
            );
            return;
        }
        this.items.put(product, this.items.getOrDefault(product, 0) + quantity);
    }

    public Map<Product, Integer> getItems(){
        return this.items;
    }

    public boolean isEmpty(){
        return this.items.isEmpty();
    }
}
