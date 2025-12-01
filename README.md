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
- git clone *<repository-url>*
- cd *<repository-directory>*

2. Navigate to each microservice folder (order-service / inventory-service) and build the project:

- cd order-service
- mvn clean install
- cd inventory-service
- mvn clean install

3. Run each service (default H2 in-memory DB is used for development):
- cd order-service
- Run OrderServiceApplication.java
- cd inventory-service
- Run InventoryServiceApplication.java

## API Documentation

Order Service:
``` http://localhost:8082/order-service ```

Inventory Service:
``` http://localhost:8081/inventory ```




Order Service
- Place an Order

POST:  http://localhost:8082/order-service/order
- *Request Body*
```
{
"productId": 1,
"quantity": 20
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
    "createdAt": "2025-12-01T22:10:38.845275"
}
```
Inventory Service
- Get Batches for a Product

GET:  http://localhost:8081/inventory/{productId}
- Response:
```
{
"id": 1,
"quantity": 20,
"expiryDate": "2025-12-01"
}
```
- Update Inventory

POST http://localhost:8081/inventory/update
- Request body
```
{
"productId": 1,
"quantity": 10
}
```

- Response
"Update successful"

## Testing instructions
JUnit 5 + Mockito is used for unit testing controllers, services, and handlers.

Run tests using Maven:
mvn test

