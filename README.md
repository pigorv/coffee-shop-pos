# Coffee Shop POS

## Overview
Coffee Shop POS is a CLI tool that processes and extracts orders from a formatted string input.

## Prerequisites
- Java
- Maven

## Installation
1. Clone the repository:
    ```bash
    git clone https://https://github.com/pigorv/coffee-shop-pos.git
    cd coffee-shop-pos
    ```

2. Build the project using Maven:
    ```bash
    mvn clean install
    ```

## Usage
Run the application with the following command:
```bash
java -cp target/coffee-shop-pos-1.0-SNAPSHOT.jar org.example.CoffeeShopPOSApplicationStarter --order=\"large coffee with extra milk, bacon roll\"
```

Format orders as:
- With extras: "product with extra1 and extra2"
- Without extras: "product"

Supported Products:
- small coffee
- medium coffee
- large coffee
- bacon roll
- freshly squeezed orange juice

Supported Extras:
- extra milk
- foamed milk
- special roast coffee

Examples:
- --order=\"large coffee with extra milk, bacon roll\"