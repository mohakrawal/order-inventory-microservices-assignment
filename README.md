# order-inventory-microservices-assignment

Two independent Spring Boot microservices in the same repository:

- `inventory-service` — runs on port 8081
- `order-service` — runs on port 8082

## Requirements
- Java 21
- Maven


## Table of Contents
1. [Project Setup](#project-setup)
2. [API Documentation](#api-documentation)
3. [Testing Instructions](#testing-instructions)

## Project Setup

### Steps
1. Clone the repository:
```bash
git clone <repository-url>
cd <repository-directory>

Navigate to each microservice folder (order-service / inventory-service) and build the project:
cd order-service
mvn clean install

cd ../inventory-service
mvn clean install

Run each service (default H2 in-memory DB is used for development):
cd order-service
mvn spring-boot:run

cd ../inventory-service
mvn spring-boot:run

Order Service:
``` http://localhost:8080/order-service ```

Inventory Service:
``` http://localhost:8081/inventory-service ```


## API Documentation
Order Service
Place an Order

POST /order-service/order
*Request Body*
```
{
  "productId": 1,
  "quantity": 2
}
```

*Response*:
```
{
    "id": 1,
    "productId": 1,
    "quantity": 10,
    "price": null,
    "status": "PLACED",
    "createdAt": "2025-12-01T21:40:58.831346"
}
```

