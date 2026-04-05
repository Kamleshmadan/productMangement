# Product Management API

Spring Boot REST API for product catalog, shopping cart, and order placement, backed by Oracle Database and secured with Spring Security (HTTP Basic).

## Tech stack

- **Java** 17  
- **Spring Boot** 4.0.5 (`spring-boot-starter-webmvc`, `spring-boot-starter-data-jpa`, `spring-boot-starter-security`, `spring-boot-starter-validation`)  
- **Oracle** JDBC (`ojdbc11`)  
- **Lombok** (optional, for some classes)

## Prerequisites

- JDK 17  
- Oracle Database reachable from your machine (default config uses `localhost:1521:XE`)  
- Maven **or** use the included Maven Wrapper (`mvnw` / `mvnw.cmd`)

## Configuration

Edit `src/main/resources/application.properties` for your environment:

| Property | Description |
|----------|-------------|
| `spring.datasource.url` | Oracle JDBC URL |
| `spring.datasource.username` / `password` | DB credentials |
| `spring.jpa.hibernate.ddl-auto` | `update` (schema evolves with entities) |
| `server.port` | Default **8093** |

## Run the application

```bash
./mvnw spring-boot:run
```

On Windows:

```cmd
mvnw.cmd spring-boot:run
```

The API base URL is typically: `http://localhost:8093`

## Authentication

- **HTTP Basic** is enabled (`SecurityConfig`).
- Users are loaded from the **`users`** table via `CustomUserDetailService` (username = `userName`, password stored in DB).
- New users registered through **`POST /auth/register`** have passwords hashed with **BCrypt** (`AuthService`).
- **First-time setup:** you need at least one user row in the database (or register via `/auth/register` if your security rules allow the caller to hit that endpoint). Use a role value that maps to Spring Security roles (e.g. `ADMIN` or `USER` — the `ROLE_` prefix is added by Spring when using `.roles(...)`).

In Postman: **Authorization → Basic Auth**, then send the request.

CSRF is **disabled** for easier API testing (`csrf.disable()` in `SecurityConfig`).

## API overview

Base URL: `http://localhost:8093`

### Users (`/user`)

| Method | Path | Description | Roles (typical) |
|--------|------|-------------|-----------------|
| `POST` | `/user/addUser` | Create or update user (upsert by `userName`) | `ADMIN` |
| `PUT` | `/user/updateUser` | Update user (upsert) | `ADMIN` |

Request body example:

```json
{
  "userName": "alice",
  "password": "secret",
  "role": "USER"
}
```

### Auth (`/auth`)

| Method | Path | Description | Roles (typical) |
|--------|------|-------------|-----------------|
| `POST` | `/auth/register` | Register user (BCrypt password) | `ADMIN` |

### Products (`/products`)

| Method | Path | Description | Roles (typical) |
|--------|------|-------------|-----------------|
| `GET` | `/products` | List all products | `USER`, `ADMIN` |
| `GET` | `/products/{id}` | Get product by id | `USER`, `ADMIN` |
| `POST` | `/products/addProduct` | Create product | `ADMIN` |
| `PUT` | `/products/updateProduct` | Update product | `ADMIN` |
| `PATCH` | `/products/{id}/price-quantity` | Update price and quantity (query: `newPrice`, `newQuantity`) | `ADMIN` |
| `PATCH` | `/products/{id}/visibility` | Update visibility (query: `isVisible`) | `ADMIN` |

Example PATCH (price/quantity):

```http
PATCH /products/1/price-quantity?newPrice=99.99&newQuantity=50
```

### Cart (`/cart`)

| Method | Path | Description | Roles (typical) |
|--------|------|-------------|-----------------|
| `POST` | `/cart/addItem` | Add to cart (`userName`, `productId`, `quantity`) | `USER`, `ADMIN` |
| `PUT` | `/cart/updateItems` | Set line quantity (`userName`, `productId`, `quantity`) | `USER`, `ADMIN` |
| `DELETE` | `/cart/removeItems` | Remove line (`userName`, `productId`) | `USER`, `ADMIN` |

Example:

```http
POST /cart/addItem?userName=alice&productId=1&quantity=2
```

### Orders (`/orders`)

| Method | Path | Description | Roles (typical) |
|--------|------|-------------|-----------------|
| `POST` | `/orders/place` | Place order from user’s cart (`userName`) | `USER`, `ADMIN` |

Example:

```http
POST /orders/place?userName=alice
```

## Database notes

- JPA maps entities to tables such as `users`, `products`, `cart`, `cart_items`, `orders`, `order_items` (see `@Table` on entity classes).
- Primary keys in this project use **`GenerationType.SEQUENCE`** for several entities; ensure your Oracle schema and Hibernate DDL settings match your expectations.

## Build & test

```bash
./mvnw test
```

## Project layout (main packages)

- `com.example.product.management` — application entry  
- `com.example.product.management.controller` — REST controllers  
- `com.example.product.management.service` — business logic  
- `com.example.product.management.repository` — Spring Data JPA  
- `com.example.product.management.entity` — JPA entities  
- `com.example.product.management.config` — security configuration  

## License

Add a license file or section here if you publish the repository publicly.
