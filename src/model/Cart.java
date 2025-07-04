package model;

import model.product.Product;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> items;

    public Cart(Map<Product, Integer> initialItems) {
        this.items = new LinkedHashMap<>(initialItems); // Create a copy for safety
    }

    public Cart(){
        this.items = new LinkedHashMap<>();
    }

    public void add(Product product, int quantity){
        if(quantity <= 0){
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
