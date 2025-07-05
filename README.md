# Fawry Full Stack Internship Challenge

This project is a Java-based solution for the Fawry Rise Journey Full Stack Development Internship Challenge. It implements a command-line e-commerce system that handles products, inventory, customer carts, and a complete checkout process with various validation rules.

## Table of Contents
1.  [Project Features](#project-features)
2.  [Design and Architecture](#design-and-architecture)
    -   [Core Principles](#core-principles)
    -   [Project Structure](#project-structure)
    -   [Class Overview](#class-overview)
3.  [Setup and Prerequisites](#setup-and-prerequisites)
4.  [How to Run](#how-to-run)
    -   [Running from IntelliJ IDEA](#running-from-intellij-idea)
    -   [Running from the Command Line](#running-from-the-command-line)
5.  [Test Cases Covered](#test-cases-covered)

## Project Features

The system successfully implements the following features as required by the challenge description:
- **Product Management**: Defines products with a name, price, and stock quantity.
- **Product Variations**:
    - **Expirable Products**: Products like food can expire.
    - **Shippable Products**: Products like electronics or food have a weight and require shipping.
    - **Digital Products**: Products like scratch cards are neither expirable nor shippable.
- **Shopping Cart**: Customers can add a specific quantity of a product to their cart, with validation to prevent adding more than the available stock.
- **Checkout Process**: A comprehensive checkout flow that:
    - Calculates order subtotal, shipping fees, and the final paid amount.
    - Validates against common errors (empty cart, insufficient balance, out of stock, expired items).
    - Updates customer balance and product stock upon successful purchase.
- **Shipping Service Integration**: Collects all shippable items and sends them to a `ShippingService` for processing, which then prints a shipment notice.
- **Console Output**: Prints detailed checkout receipts and shipment notices as specified.

## Design and Architecture

The project is designed using Object-Oriented Principles to be modular, extensible, and easy to maintain.

### Core Principles

- **Interface-Based Design**: Core capabilities like `Shippable` and `Expirable` are defined as interfaces. This allows for great flexibility, as a product can implement any combination of these capabilities.
- **Inheritance**: An abstract `Product` class contains common attributes (`name`, `price`, `stockQuantity`), which concrete product classes (`FoodProduct`, `ElectronicsProduct`, `DigitalProduct`) extend. This promotes code reuse.
- **Separation of Concerns (SoC)**: The project is divided into logical packages for models and interfaces, while services reside in the main package.
- **Immutability**: Model objects are designed to be as immutable as possible to enhance safety and predictability.
- **Defense in Depth**: Critical operations like checkout include redundant validation checks to ensure system integrity.

### Project Structure

The project is organized into the following packages and files, directly reflecting the structure in the provided image:

```
Fawry task/
└── src/
    ├── Interfaces/
    │   ├── Expirable.java
    │   └── Shippable.java
    ├── model/
    │   ├── Cart.java
    │   ├── Customer.java
    │   └── product/
    │       ├── DigitalProduct.java
    │       ├── ElectronicsProduct.java
    │       ├── FoodProduct.java
    │       └── Product.java
    ├── ECommerceService.java
    ├── ShippingService.java
    └── Main.java
```

### Class Overview

- **`Main.java`**: The entry point of the application. It contains a comprehensive suite of test cases that demonstrate all features and edge cases.
- **`Interfaces` package**: Contains `Shippable` and `Expirable` interfaces defining the contracts for different product capabilities.
- **`model` package**: Contains data entities.
    - **`Product` (abstract)**: The base class for all products.
    - **`FoodProduct`, `ElectronicsProduct`, `DigitalProduct`**: Concrete product implementations.
    - **`Customer`**: Represents a customer with a name and balance.
    - **`Cart`**: Manages a collection of products for a shopping session.
- **`ECommerceService.java`**: Orchestrates the entire checkout process.
- **`ShippingService.java`**: Responsible for processing shippable items.

## Setup and Prerequisites

- **Java Development Kit (JDK)**: Version 11 or higher is recommended.
- **IntelliJ IDEA** (or another Java IDE).
- **Git**: To clone the repository.

## How to Run

### Running from IntelliJ IDEA

This is the recommended method.
1.  Open the `Fawry task` project in IntelliJ IDEA.
2.  Locate the `Main.java` file in the `src` directory.
3.  Right-click on the `Main.java` file and select **Run 'Main.main()'**.
4.  The console will display the output from all the predefined test cases.

### Running from the Command Line

1.  Open a terminal or command prompt and navigate to the project's `src` directory.
    ```bash
    cd "path/to/Fawry task/src"
    ```
2.  Compile all the Java files. This command compiles files in the current directory, the `Interfaces` directory, and the `model` subdirectories, placing the `.class` files in the `../out` directory.

    ```bash
    # For Windows
    javac -d ../out Interfaces\*.java model\*.java model\product\*.java *.java

    # For macOS/Linux
    javac -d ../out Interfaces/*.java model/*.java model/product/*.java *.java
    ```
3.  Run the `Main` class from the parent directory (`Fawry task`). The `-cp` flag tells Java where to find the compiled classes.

    ```bash
    cd ..
    java -cp out Main
    ```
4.  The console will display the test case results.

## Test Cases Covered

The `Main.java` file includes a comprehensive set of automated tests to verify all functionalities and edge cases, including:

- **Successful Checkout**: A standard, successful purchase as described in the PDF.
- **Insufficient Balance**: A checkout attempt that fails because the customer's balance is too low.
- **Product Out of Stock**: An attempt to add more items to the cart than are available.
- **Expired Product**: A checkout attempt with an expired item, which is rejected.
- **Empty Cart**: A checkout attempt with an empty cart.
- **Digital-Only Purchase**: A successful checkout with only non-shippable items, resulting in zero shipping fees.
- **Exact Balance Purchase**: A successful checkout where the customer's balance exactly matches the total amount.
- **Zero/Negative Quantity**: An attempt to add an invalid quantity to the cart, which is ignored.
