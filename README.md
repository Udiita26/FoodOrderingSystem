# 🍴 Restaurant Management System (Java)

A **console-based Restaurant Management System** built in **Java** using **Object-Oriented Programming (OOP)**, **DSA concepts**, and **File Handling**.  
The system provides three roles — **Admin, Customer, and Restaurant** — each with their own features and workflows.

---

## 🚀 Features

### 👨‍💼 Admin
- Admin login authentication
- Register new customers and restaurants
- Manage restaurant data (add/remove/update)
- Control access and assign restaurant IDs

### 🍽️ Restaurant
- Secure login with Restaurant ID & password
- Add, update, delete food items
- Manage food menu
- View and manage customer orders

### 🧑‍💻 Customer
- Register and login with credentials
- Browse restaurants & food menus
- Filter/search food items
- Place orders and calculate totals
- View order history (stored in files)
- Supports **Undo/Redo** for order history (customer-specific)

---

## 🛠️ Tech Stack
- **Language:** Java  
- **Concepts:** OOP, DSA (LinkedList, Queue, BST, Sorting, Searching)  
- **Storage:** File Handling (`.txt` files for users, restaurants, foods, and orders)  
- **Tools:** Scanner for input, modular services (`AdminService`, `CustomerService`, etc.)

---

## 📂 Project Structure
Restaurant-Management-System/
│── src/
│ ├── Main.java
│ ├── service/
│ │ ├── AdminService.java
│ │ ├── CustomerService.java
│ ├── model/
│ │ ├── Customer.java
│ │ ├── Restaurant.java
│ │ ├── Order.java
│ │ ├── FoodItem.java
│ ├── utils/
│ │ ├── FileHandler.java
│
│── data/
│ ├── users.txt
│ ├── restaurants.txt
│ ├── foods.txt
│ ├── orders.txt
│
│── README.md
