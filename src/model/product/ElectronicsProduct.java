package model.product;

import Interfaces.Shippable;

public class ElectronicsProduct extends Product implements Shippable {
    private double weight;

    public ElectronicsProduct(String name, double price, int quantity, double weight) {
        super(name, price, quantity);
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }
}
