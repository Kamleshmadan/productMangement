# 🛒 Product Management System (Spring Boot)

## 📌 Overview

This is a **Spring Boot-based backend application** for managing an e-commerce-like system.
It supports operations for **Products, Users, Cart, and Orders**.

The project demonstrates a layered architecture using:

* Controller
* Service
* Repository
* Entity

---

## 🚀 Features

### ✅ Product Management

* Create Product
* Get all Products
* Get Product by ID
* Update Product
* Delete Product

### 👤 User Management

* Create User
* Get User details

### 🛒 Cart Management

* Add item to cart
* Remove item from cart
* View cart

### 📦 Order Management

* Place order
* View orders

---

## 🏗️ Tech Stack

* Java 17+
* Spring Boot
* Spring Data JPA
* Hibernate
* Maven
* MySQL / H2 (depending on config)

---

## 📂 Project Structure

```
com.example.productmanagement
│
├── controller
├── service
├── repository
├── entity
├── exception
└── config
```

---

## 🔗 API Endpoints

### 📦 Product APIs

#### 1. Create Product

```
POST /products
```

**Request Body:**

```json
{
  "name": "Laptop",
  "price": 50000,
  "quantity": 10
}
```

---

#### 2. Get All Products

```
GET /products
```

---

#### 3. Get Product by ID

```
GET /products/{id}
```

---

#### 4. Update Product

```
PUT /products/{id}
```

**Request Body:**

```json
{
  "name": "Laptop",
  "price": 55000,
  "quantity": 8
}
```

---

#### 5. Delete Product

```
DELETE /products/{id}
```

---

### 👤 User APIs

#### 1. Create User

```
POST /users
```

**Request Body:**

```json
{
  "name": "Kamlesh",
  "email": "kamlesh@example.com"
}
```

---

#### 2. Get User by ID

```
GET /users/{id}
```

---

### 🛒 Cart APIs

#### 1. Add Item to Cart

```
POST /cart/add
```

**Request Body:**

```json
{
  "userId": 1,
  "productId": 2,
  "quantity": 3
}
```

---

#### 2. Remove Item from Cart

```
DELETE /cart/remove/{cartItemId}
```

---

#### 3. View Cart

```
GET /cart/{userId}
```

---

### 📦 Order APIs

#### 1. Place Order

```
POST /orders/place/{userId}
```

---

#### 2. Get Orders by User

```
GET /orders/{userId}
```

---

## ⚙️ How to Run

1. Clone the repository:

```
git clone https://github.com/Kamleshmadan/productMangement.git
```

2. Navigate to project:

```
cd productMangement
```

3. Run the application:

```
mvn spring-boot:run
```

---

## 🧠 Key Concepts Used

* REST APIs
* Dependency Injection
* Layered Architecture
* Exception Handling
* JPA & Hibernate ORM

---

## 🚧 Future Improvements

* Add DTO layer
* Add validation annotations
* Implement authentication (JWT)
* Improve logging

---

## 👨‍💻 Author

**Kamlesh Madan**

Give it a ⭐ on GitHub!
