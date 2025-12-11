# finance-ai-backend
Spring Boot backend for AI-Powered Personal Finance Assistant

### Project Folder Structure
```
finance-ai-backend/
 └── src/main/java/com/finance
      ├── controller
      ├── service
      ├── repository
      ├── entity           <— Entities = Database Classes
      ├── dto              <— DTOs = API Request/Response Models
      ├── config
      ├── security
      └── FinanceAiBackendApplication.java
 └── src/main/resources
      ├── application.properties
 └── pom.xml
 ```
 
### SYSTEM ARCHITECTURE DIAGRAM

```
           	   ┌────────────────────────────┐
                 │         React UI            │
                 │ (Vite + Tailwind + Axios)   │
                 └──────────────┬─────────────┘
                                │ HTTP (JWT)
                                ▼
             ┌────────────────────────────────────┐
             │           Spring Boot API          │
             │   (Auth, Expense CRUD, Analytics)  │
             └──────────────┬───────────┬────────┘
                            │           │
               DB Queries   │           │ Calls AI Engine
                            ▼           ▼
             ┌────────────────┐   ┌────────────────────────┐
             │   MySQL DB     │   │   Python AI Engine      │
             │ (Users, Exp.)  │   │ (FastAPI + LLM insights)│
             └────────────────┘   └────────────────────────┘
                                         │
                                         │ AI Model Calls
                                         ▼
                               ┌────────────────────┐
                               │   OpenAI / LLaMA    │
                               └────────────────────┘
```


```
Login → JWT Token → Client Stores → Client Sends Token Each Request → AuthFilter Validates → Controller Executes
```

### HOW THE SYSTEM WORKS (Step-By-Step Flow)

#### 1️. User uses the React UI

Logs in / registers

Adds expenses

Views charts

Requests AI Insight button

React communicates only with Spring Boot, not directly with the Python AI.

#### 2. Spring Boot is the main backend:

Stores users

Stores expenses & categories

Calculates monthly totals

Secures everything with JWT

Acts as the bridge between React ↔ Python AI Engine

When a user clicks “Generate Insights”:

```
	React → Spring Boot → Python AI Engine → Spring Boot → React
```
You keep AI engine separate for cleaner architecture.

#### 3. MySQL database stores all structured data

Users

Expenses

Income

Categories

Monthly totals

The backend reads/writes to MySQL using JPA.

#### 4. Python AI Engine processes financial data

This is where your GenAI logic lives.

Spring Boot sends data to Python like: 

```
{
  "month": "2025-02",
  "totalIncome": 50000,
  "expenses": [
    {"category": "Food", "amount": 5000},
    {"category": "Rent", "amount": 15000}
  ]
}
```

The AI Engine:

- Summarizes spending

- Detects overspending

- Gives personalized budgeting insights

- Generates a financial plan

- Suggests saving strategies

 