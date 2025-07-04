package model.product;

public class Product {
    private String name;
    private double price; //double is used instead of int so that it demonstrates the correct real-world thinking
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void decreaseStockBy(int quantity) {
        this.quantity -= quantity;
    }
}
