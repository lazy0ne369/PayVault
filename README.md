# PayVault — Project PS020: Fault-Tolerant Digital Wallet & Real-Time Financial Ledger System

> **A Microservices Architecture for Secure, Reliable, and Transparent Transactions**  
> *"More Than Payments, A Trusted Tomorrow"*

[![Java](https://img.shields.io/badge/Java-25-orange.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.1.x-green.svg)](https://spring.io/projects/spring-cloud)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16+-blue.svg)](https://www.postgresql.org/)
[![Security](https://img.shields.io/badge/Auth-JWT%20%2B%20Spring%20Security-red.svg)](https://jwt.io/)

---

## Architecture Overview

![PayVault Architecture](docs/assets/architecture.png)

PayVault is an enterprise-grade, distributed microservices platform engineered for high-concurrency payment processing, wallet balance management, and immutable double-entry financial bookkeeping.

---

## Key System Features

- **Centralized API Gateway**: Dynamic routing, URL pattern matching, and client traffic management.
- **Service Discovery & Registry**: Netflix Eureka server for resilient service lookup, load balancing, and heartbeat health checks.
- **Secure Authentication (JWT)**: Stateless authentication with BCrypt password hashing and role-based JWT claim verification.
- **Digital Wallet Management**: Real-time balance queries, wallet provisioning, and state handling.
- **Idempotent Transaction Processing**: Ensures non-duplicate transfers with unique idempotency keys.
- **Double-Entry Financial Ledger**: Immutable accounting ledger entries (Debit/Credit pairs) tracking every movement of funds.
- **Event Streaming**: Asynchronous event publishing and consumption via Kafka / RabbitMQ (`transaction.created`, `ledger.posted`, `transaction.completed`).
- **Resilience & Fault Tolerance**:
  - Circuit Breakers & Retries with exponential backoff (Resilience4j)
  - Automatic service failover across multiple instances
  - Service timeouts and graceful error handling

---

## Service Registry & Port Mappings

| Service | Component Role | Default Port | Gateway Route Path |
| :--- | :--- | :--- | :--- |
| **PayVault Server** | Netflix Eureka Discovery Server | `9000` | — |
| **GatewayService** | Spring Cloud API Gateway | `8000` | Entry Point (`/api/**`) |
| **AuthService1** | Authentication & JWT Service | `8080` / `8081` | `/api/auth/**` |
| **WalletService** | Digital Wallet Management | `8082` | `/api/wallet/**` |
| **TransactionService** | Fund Transfers & Idempotency | `8083` | `/api/transactions/**` |
| **LedgerService** | Double-Entry Immutable Ledger | `8084` | `/api/ledger/**` |

---

## Database Schema (PostgreSQL)

| Table | Primary Key | Foreign Keys | Key Attributes |
| :--- | :--- | :--- | :--- |
| `users` | `id` / `user_id` | — | `name`, `email`, `password`, `role`, `created_at` |
| `wallets` | `wallet_id` | `user_id` | `balance`, `status`, `created_at` |
| `transactions` | `transaction_id` | `sender_id`, `receiver_id` | `amount`, `status`, `idempotency_key`, `created_at` |
| `ledger_entries` | `entry_id` | `transaction_id` | `account_id`, `type` (DEBIT/CREDIT), `amount`, `created_at` |

---

## Review 1: REST Client Verification (Auth & JWT)

This section covers the testing verification for **Review 1**, demonstrating:
1. **User Registration**
2. **User Login & JWT Token Generation**
3. **Protected API Access with Bearer Token**

> An executable REST Client file is provided in [`docs/auth-review1.http`](docs/auth-review1.http) for VS Code REST Client or IntelliJ HTTP Client.

---

### 1. User Registration

Creates a new user account with BCrypt-hashed password storage and default role assignment.

- **Method**: `POST`
- **Gateway Endpoint**: `http://localhost:8000/api/auth/register`
- **Direct Service Endpoint**: `http://localhost:8080/api/auth/register`
- **Headers**:
  ```http
  Content-Type: application/json
  ```

#### Request Body
```json
{
  "name": "Sohan Kumar",
  "email": "user@example.com",
  "password": "Secure123"
}
```

#### Expected Response (`200 OK`)
```json
{
  "id": 1,
  "name": "Sohan Kumar",
  "email": "user@example.com",
  "role": "USER"
}
```

#### cURL Command
```bash
curl -X POST http://localhost:8000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Sohan Kumar",
    "email": "user@example.com",
    "password": "Secure123"
  }'
```

---

### 2. User Login & JWT Token Generation

Authenticates user credentials against the database and returns a signed JSON Web Token (JWT).

- **Method**: `POST`
- **Gateway Endpoint**: `http://localhost:8000/api/auth/login`
- **Direct Service Endpoint**: `http://localhost:8080/api/auth/login`
- **Headers**:
  ```http
  Content-Type: application/json
  ```

#### Request Body
```json
{
  "email": "user@example.com",
  "password": "Secure123"
}
```

#### Expected Response (`200 OK`)
```json
{
  "message": "Login successful",
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE3NDI1ODcyMDAsImV4cCI6MTc0MjU5MDgwMH0.xxxx...",
  "tokenType": "Bearer"
}
```

#### cURL Command
```bash
curl -X POST http://localhost:8000/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "Secure123"
  }'
```

---

### 3. JWT Bearer Token Verification on Protected APIs

Pass the generated token in the `Authorization` header to access protected endpoints across the microservices ecosystem.

- **Method**: `GET`
- **Endpoint**: `http://localhost:8000/api/wallet/1`
- **Headers**:
  ```http
  Authorization: Bearer <YOUR_JWT_TOKEN>
  Content-Type: application/json
  ```

#### cURL Command
```bash
curl -X GET http://localhost:8000/api/wallet/1 \
  -H "Authorization: Bearer <YOUR_JWT_TOKEN>" \
  -H "Content-Type: application/json"
```

---

## System Requirements & Dependencies

### 1. Environment Prerequisites

| Requirement | Supported Version | Purpose |
| :--- | :--- | :--- |
| **Java JDK** | 25 (Compatible with 17+) | Core runtime for Spring Boot 4 / Java microservices |
| **Apache Maven** | 3.9+ | Build and dependency lifecycle manager |
| **PostgreSQL** | 16+ (Port: `5433`) | Relational persistence store for users, wallets, and transactions |
| **Docker & Docker Compose** | Latest (Optional) | Fast containerized startup for PostgreSQL |

### 2. Multi-Module Root Aggregator (`pom.xml`)

PayVault provides a top-level aggregator [`pom.xml`](pom.xml) enabling you to resolve, download, and build all dependencies across the entire microservices ecosystem with a single command:

```bash
# Resolve and download all dependencies for all microservices in one go:
mvn dependency:resolve

# Or build and package all microservices simultaneously:
mvn clean install -DskipTests
```

### 3. Microservice Dependency Manifest

The table below lists the dependencies defined and loaded in each microservice's `pom.xml`:

| Microservice | Core Dependencies Loaded | Purpose |
| :--- | :--- | :--- |
| **PayVault Server** | • `spring-cloud-starter-netflix-eureka-server`<br>• `spring-boot-starter-webmvc`<br>• `spring-boot-starter-actuator` | Service registry, health heartbeats, and cluster discovery |
| **GatewayService** | • `spring-cloud-starter-gateway-server-webmvc`<br>• `spring-cloud-starter-netflix-eureka-client`<br>• `spring-cloud-starter-loadbalancer`<br>• `spring-boot-starter-actuator` | Reverse proxy routing, client load balancing, and route predicates |
| **AuthService1** | • `spring-boot-starter-webmvc`<br>• `spring-boot-starter-security`<br>• `spring-boot-starter-data-jpa`<br>• `org.postgresql:postgresql`<br>• `io.jsonwebtoken:jjwt-api:0.12.6`<br>• `io.jsonwebtoken:jjwt-impl:0.12.6`<br>• `io.jsonwebtoken:jjwt-jackson:0.12.6`<br>• `spring-cloud-starter-netflix-eureka-client` | User registration, password encryption (BCrypt), JWT token signing and verification, JPA database mapping |
| **WalletService** | • `spring-boot-starter-webmvc`<br>• `spring-boot-starter-data-jpa`<br>• `org.postgresql:postgresql`<br>• `spring-cloud-starter-netflix-eureka-client`<br>• `spring-boot-starter-actuator` | Digital wallet accounts, balance queries and updates, transactional persistence |
| **TransactionService** | • `spring-boot-starter-webmvc`<br>• `spring-boot-starter-data-jpa`<br>• `org.postgresql:postgresql`<br>• `spring-cloud-starter-netflix-eureka-client`<br>• `spring-boot-starter-actuator` | Money transfers, idempotency key checks, audit transaction logs |

### 4. Infrastructure Dependencies (Docker Compose)

Launch the required PostgreSQL instance with the pre-configured `payvault` database and credentials (`postgres` / `root` on port `5433`) using the bundled [`docker-compose.yml`](docker-compose.yml):

```bash
docker compose up -d
```

---

## Local Development & Setup

### 1. Prerequisites
- **JDK 25** (or compatible JDK 17+)
- **PostgreSQL** running on port `5433` (or configured port) with database `payvault` (or run `docker compose up -d`)
- **Maven 3.9+**

### 2. Startup Order
To ensure proper service discovery and routing, start the microservices in the following sequence:

1. **Start Database (if using Docker)**:
   ```bash
   docker compose up -d
   ```

2. **Service Registry (Eureka)**:
   ```bash
   cd PayVault
   ./mvnw spring-boot:run
   ```
   *Dashboard available at:* `http://localhost:9000`

3. **API Gateway**:
   ```bash
   cd GatewayService
   ./mvnw spring-boot:run
   ```
   *Gateway listening on:* `http://localhost:8000`

4. **Authentication Service**:
   ```bash
   cd AuthService1
   ./mvnw spring-boot:run
   ```
   *Service listening on:* `http://localhost:8080`

5. **Wallet & Transaction Services**:
   ```bash
   cd WalletService
   ./mvnw spring-boot:run
   ```
   ```bash
   cd TransactionService
   ./mvnw spring-boot:run
   ```

---

## License & Contributing

Built for secure, transparent, and resilient digital financial transactions.
Distributed under the MIT License.

