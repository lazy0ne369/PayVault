# PayVault

A distributed microservices-based payment and wallet management platform built with Spring Boot, Spring Cloud, and Java 25.

## Architecture & Services

PayVault is architected around the following microservices:

| Service | Description | Port / Role |
| :--- | :--- | :--- |
| **PayVault (Service Registry)** | Eureka Service Discovery Server | Service Registry & Discovery |
| **GatewayService** | Spring Cloud API Gateway | Central entry point, routing, and filtering |
| **AuthService / AuthService1** | Authentication & Authorization Service | JWT token generation, user authentication, and security |
| **WalletService** | Wallet Management Service | Manages user wallets, balances, and ledger entries |
| **TransactionService** | Transaction Processing Service | Handles money transfers, transaction history, and settlement |

## Tech Stack

- **Java**: 25
- **Spring Boot**: 4.1.x
- **Spring Cloud**: 2025.1.x (Netflix Eureka, Spring Cloud Gateway)
- **Security & Data**: Spring Data JPA, Spring Security, JWT
- **Build Tool**: Maven

## Getting Started

### Prerequisites
- JDK 25 installed and configured
- Maven 3.9+

### Running Services Locally

1. **Start Eureka Discovery Server**:
   ```bash
   cd PayVault
   ./mvnw spring-boot:run
   ```

2. **Start API Gateway**:
   ```bash
   cd GatewayService
   ./mvnw spring-boot:run
   ```

3. **Start Core Services**:
   ```bash
   cd AuthService
   ./mvnw spring-boot:run
   ```
   ```bash
   cd WalletService
   ./mvnw spring-boot:run
   ```
   ```bash
   cd TransactionService
   ./mvnw spring-boot:run
   ```
