# 🎯 Amyway Interview

This project consists of a microservices-based architecture that includes four main services:

- Cloud Gateway - API gateway for routing requests.

- Service Registry - Eureka server for service discovery.

- Calculator Service - A RESTful API that performs mathematical calculations.

- Prize Wheel Service - An API for an e-commerce spin-the-wheel lottery system.

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

**Clone the repository:**

```sh
git clone https://github.com/katkatniss/amway-interview.git
cd amway-interview
```

**Run services with Docker:**

- Generate the jar file for running:
```sh
mvn clean install -Dskiptests=true
```
- Builds, (re)creates, starts, and attaches to containers for services
```sh
docker-compose -f compose.yml up --build --force-recreate
```

**Run services with spring boot embedded server:**

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

## 📡 API Documentation

Each service exposes RESTful APIs:

### **Calculator Service (/calculator)**

| Method | Endpoint          | Description              |
|--------|------------------|--------------------------|
| GET    | `/calculator/{exp}` | Evaluate an expression  |
| GET    | `/calculator/undo`  | Undo last operation     |
| GET    | `/calculator/redo`  | Redo last undone operation |

1️⃣ Evaluate an Expression

Method: GET

Endpoint: /calculator/{exp}

Description: Evaluates a mathematical expression and returns the result.

Examples:

✅ /calculator/10+3 → 13

✅ /calculator/2.5*4 → 10

Possible Errors:

/calculator/abc – Invalid expression format (e.g., /calculator/10++3)

2️⃣ Undo Last Operation

Method: GET

Endpoint: /calculator/undo

Description: Undoes the last operation and restores the previous result.

Example:

✅ /calculator/undo → Previous result restored

3️⃣ Redo Last Undone Operation

Method: GET

Endpoint: /calculator/redo

Description: Redoes the last undone operation.

Example:

✅ /calculator/redo → Redo last undone operation

### **Prize Wheel Service (/prize-wheel)**

| Method | Endpoint         | Description                 |
|--------|------------------|-----------------------------|
| POST   | `/prize-wheel/setupPrizes` | Setup the prizes  |
| GET    | `/prize-wheel/{times}`  | Spin the wheel multiple times and get prizes |

1️⃣ Setup the Prizes

Method: POST

Endpoint: /prize-wheel/setupPrizes

Description: Sets up the prize list for the spinning wheel.

Request Body Example:
```json
{
    "prizes" : [
        {
            "name": "Prize A",
            "stock": 10,
            "probability": 0.5
        },
        {
            "name": "Prize B",
            "stock": 10,
            "probability": 0.1
        },
        {
            "name": "Prize C",
            "stock": 10,
            "probability": 0.2
        }]
}

Example Response: ✅ { "message": "Prizes configured successfully" }

Possible Errors:
400 Bad Request – Invalid prize structure

2️⃣ Spin the Wheel Multiple Times

Method: GET

Endpoint: /prize-wheel/{times}

Description: Spins the wheel and returns the list of prizes won.

Examples:

✅ /prize-wheel/3 → ["Prize A", "Prize B", "Prize C"]

✅ /prize-wheel/5 → ["Prize A", "Prize B", "Prize C", "Prize D", "Prize E"]

Possible Errors:

/prize-wheel/a → 

/prize-wheel/0 →





