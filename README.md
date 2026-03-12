# 📘 Finance AI Backend

Spring Boot backend for **Finance AI – an AI-powered personal finance analytics platform**.

This service acts as the **core API layer**, handling authentication, financial data management, analytics, and orchestration with the AI engine.

---

# 🚀 Tech Stack

### Backend Framework
- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate

### Authentication
- JWT (Stateless Authentication)

### Database
- MySQL

### AI Integration
- Python AI Engine (FastAPI)

### Tools
- Maven
- Git
- REST APIs

---

# 📂 Project Folder Structure

```
finance-ai-backend/
 └── src/main/java/com/finance
      ├── controller        → REST Controllers (API layer)
      ├── service           → Business Logic
      ├── repository        → JPA Repositories
      ├── entity            → Database Entities
      ├── dto               → API Request/Response Models
      ├── config            → App Configuration
      ├── security          → JWT & Security Config
      └── FinanceAiBackendApplication.java

 └── src/main/resources
      ├── application.properties

 └── pom.xml
```

---

# 🏗 SYSTEM ARCHITECTURE DIAGRAM

Finance AI follows a **microservice-inspired architecture** with three main components.

```
           	   ┌────────────────────────────┐
                 │         React UI            │
                 │ (Vite + Tailwind + Axios)   │
                 └──────────────┬─────────────┘
                                │ HTTP (JWT)
                                ▼
             ┌────────────────────────────────────┐
             │           Spring Boot API          │
             │  (Auth, Accounts, Transactions,    │
             │       Analytics, AI Bridge)        │
             └──────────────┬───────────┬────────┘
                            │           │
               DB Queries   │           │ Calls AI Engine
                            ▼           ▼
             ┌────────────────┐   ┌────────────────────────┐
             │   MySQL DB     │   │   Python AI Engine     │
             │ (Users, Acc,   │   │   (FastAPI Service)    │
             │ Transactions)  │   └──────────────┬─────────┘
             └────────────────┘                  │
                                                 ▼
                                         ┌────────────────────┐
                                         │   ML Layer         │
                                         └────────────────────┘
```

---

# 🔐 Authentication Flow

Finance AI uses **JWT-based stateless authentication**.

```
User Login
↓
Spring Security Authentication
↓
JWT Token Generated
↓
Client Stores Token
↓
Client Sends Token in Authorization Header
↓
JwtAuthFilter Validates Request
↓
Controller Executes
```

---

# 🔁 HOW THE SYSTEM WORKS (Step-by-Step Flow)

## 1️⃣ React UI Layer

User can:

* Register / Login
* Create Bank Accounts
* Add Transactions (Income / Expense)
* View Analytics Dashboard
* Generate AI Insights

⚠ React never talks directly to Python AI.
All requests go through Spring Boot.

---

## 2️⃣ Spring Boot (Main Backend)

Responsibilities:

* User authentication (JWT)
* Account management
* Transaction CRUD
* Monthly analytics
* AI request orchestration
* Security enforcement

When user clicks **“Generate Insights”**:

```
React → Spring Boot → Python AI Engine → Spring Boot → React
```

Spring Boot collects structured financial data and sends it to AI engine.

---

## 3️⃣ MySQL Database

Stores structured relational data:

* Users
* Bank Accounts
* Transactions
* Categories

Uses Spring Data JPA + Hibernate.

---

## 4️⃣ Python AI Engine

Receives structured JSON like:

```json
{
  "month": "2026-02",
  "totalIncome": 50000,
  "totalExpense": 30000,
  "transactions": [
    {"category": "Food", "amount": 5000},
    {"category": "Rent", "amount": 15000}
  ]
}
```

AI Engine:

* Summarizes spending
* Detects overspending
* Suggests budget improvements
* Generates financial plan
* Suggests saving strategies

---

# 🗄 DATABASE DATA RELATIONSHIP

## 🔗 Entity Relationship Diagram (ERD)

```
User
 └── 1 ──────────────── * ─── BankAccount
                              └── 1 ──────────────── * ─── Transaction
```

---

## 📊 Detailed Relationship Diagram

```
┌────────────┐
│   USER     │
├────────────┤
│ id (PK)    │
│ name       │
│ email      │
│ password   │
└─────┬──────┘
      │ 1
      │
      │
      ▼ *
┌──────────────┐
│ BANK_ACCOUNT │
├──────────────┤
│ id (PK)      │
│ name         │
│ balance      │
│ user_id (FK) │
└─────┬────────┘
      │ 1
      │
      ▼ *
┌──────────────┐
│ TRANSACTION  │
├──────────────┤
│ id (PK)      │
│ amount       │
│ type         │
│ category     │
│ date         │
│ account_idFK │
└──────────────┘
```

---

# 📌 Relationship Explanation

### ✅ One User → Many Bank Accounts

A user can create multiple accounts:

* Savings
* Current
* Credit

### ✅ One Bank Account → Many Transactions

Each transaction belongs to exactly one bank account.

### ❌ Transaction does NOT directly store userId

User is derived through:

```
Transaction → BankAccount → User
```

That is why repository queries must use:

```java
findByBankAccountUserId(...)
```

---

# 🧠 Why This Design is Good

✔ Clean separation
✔ Proper normalization
✔ No redundant userId in Transaction
✔ Scalable architecture
✔ Easy to extend (Loans, Investments later)

---

# 🚀 Future Improvements (Planned)

* Pagination for transactions
* Account-level filtering
* Monthly reports API
* Budget planning module
* Recurring transaction support
* Role-based access control
* Docker deployment
* CI/CD pipeline

---
