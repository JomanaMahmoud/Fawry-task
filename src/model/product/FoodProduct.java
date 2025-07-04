package model.product;

import Interfaces.Expirable;
import Interfaces.Shippable;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FoodProduct extends Product implements Shippable, Expirable {
    private double weight;
    private LocalDate expiryDate;

    public FoodProduct(String name, double price, int quantity, double weight, LocalDate expiryDate) {
        super(name,price,quantity);
        this.weight = weight;
        this.expiryDate = expiryDate;
    }
    @Override
    public LocalDate getExpiryDate() {
        return this.expiryDate;
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    @Override
    public double getWeight() {
        return this.weight;
    }
}
