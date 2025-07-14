# GestiuneMagazin

This is a simple Java  console-based application for managing products in a store. It supports different product types (food, electronics, clothing), organizing them by category and distributor, and provides basic inventory operations.

##  Features

- Add new products (food, electronics, clothing)
- Delete existing products
- Update product stock quantity
- Search products by category
- Display products sorted by price (ascending or descending)
- Show all available distributors
- Display products with stock below a certain threshold
- Calculate total inventory value
- Show all registered products

##  Structure

- **`Produs`** – abstract class for products
    - `ProdusAlimentar` – food product
    - `ProdusElectronic` – electronic product
    - `ProdusVestimentar` – clothing product
- **`Stoc`** – represents stock quantity
- **`Categorie`** – product category
- **`Distribuitor`** – distributor details
- **`ServiciuMagazin`** – handles product logic, sorting, filtering, etc.
- **`Main`** – console interface for interacting with the application
