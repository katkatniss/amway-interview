# 🎯 Interview Project

This project consists of a microservices-based architecture that includes four main services:

-Cloud Gateway - API gateway for routing requests.

-Service Registry - Eureka server for service discovery.

-Calculator Service - A RESTful API that performs mathematical calculations.

-Prize Wheel Service - An API for an e-commerce spin-the-wheel lottery system.

## 🏗️ Project Structure

├── amway-interview/

│   ├── cloud-gateway/       # API Gateway (Spring Cloud Gateway)

│   ├── service-registry/    # Eureka Service Registry

│   ├── calculator-service/  # Calculator API with undo/redo support

│   ├── prize-wheel-service/ # Lottery system with prize control

│   ├── README.md            # Project Documentation

│   ├── compose.yml   # Docker setup (if applicable)

│   ├── pom.xml              # Parent POM for Maven multi-module project


## 🚀 Getting Started

Ensure you have the following installed:

Java 17+

Maven 3+

Docker (optional for containerization)

# Installation & Running the Services

Clone the repository:

```sh
git clone https://github.com/katkatniss/amway-interview.git
cd amway-interview
```

Start the Service Registry (Eureka):
```sh
cd service-registry
mvn spring-boot:run
```
Start Cloud Gateway:
```sh
cd cloud-gateway
mvn spring-boot:run
```
Start Calculator Service:
```sh
cd calculator-service
mvn spring-boot:run
```
Start Prize Wheel Service:
```sh
cd prize-wheel-service
mvn spring-boot:run
```
Alternatively, use docker-compose up if a compose.yml file is provided.

## 📡 API Documentation

Each service exposes RESTful APIs:

### **Calculator Service (/calculator)**

| Method | Endpoint          | Description              |
|--------|------------------|--------------------------|
| GET    | `/calculator/{exp}` | Evaluate an expression  |
| GET    | `/calculator/undo`  | Undo last operation     |
| GET    | `/calculator/redo`  | Redo last undone operation |

Example:

GET /calculator/10+3  # Returns 13


### **Prize Wheel Service (/prize-wheel)**

| Method | Endpoint          | Description              |
|--------|------------------|--------------------------|
| POST   | `/prize-wheel/setupPrizes` | Setup the prizes  |
| GET    | `/prize-wheel/{times}`  | Spin the wheel multiple times and get prizes |


Example:


