# Database Load Balancer

## Overview

This project implements a **Database Load Balancer** that distributes queries among multiple database instances using **Design Patterns** such as Singleton, Proxy, Strategy, and Facade. The system includes a graphical user interface (GUI) to execute SQL queries, monitor connection statuses, and view logs.

---

## Features

- **Load Balancing:**
  - Ensures even distribution of queries among databases.
  - Uses the **Round Robin** strategy by default.
- **Proxy Design Pattern:**
  - `DatabaseProxy` acts as an intermediary for executing queries.
  - Separates `SELECT` queries from data modification queries (`INSERT`, `UPDATE`, `DELETE`).
- **GUI for Interaction:**
  - View database connection statuses in real-time.
  - Execute SQL queries and view results.
  - Monitor logs in an integrated logging panel.
- **Logging:**
  - Real-time logging using `LoggerPanel` integrated into the GUI.
  - Logs both successful and failed operations.
- **Testable Architecture:**
  - Modular design allows for easy unit testing and expansion.

---

## Design Patterns Used

### 1. Singleton

- **Class:** `LoadBalancer`
- Ensures only one instance of the load balancer exists in the application.
- Centralizes the management of database connections and load-balancing strategies.

### 2. Proxy

- **Class:** `DatabaseProxy`
- Acts as a middle layer between the user and the database connections.
- Adds functionality like query classification and centralized logging.

### 3. Strategy

- **Class:** `RoundRobinStrategy`
- Enables the load balancer to dynamically switch strategies for distributing queries.
- Default strategy: **Round Robin**.

### 4. Facade

- **Class:** `DatabaseConnectionManagerFacade`
- Provides a simplified interface for managing multiple database connections.
- Handles adding, removing, and monitoring connections.

---

## How It Works

### 1. Initialization

- The application initializes `LoadBalancer` with a `RoundRobinStrategy`.
- Database connections are added via `DatabaseConnectionManagerFacade`.

### 2. Query Execution

- The user submits SQL queries through the GUI.
- Queries are routed via `DatabaseProxy`:
  - **`SELECT` Queries:** Sent to a specific database selected by the load balancer.
  - **Data Modification Queries:** Sent to all databases for consistency.

### 3. Monitoring

- The GUI includes a status area that updates every second with the current state of all database connections.
- Logs are displayed in real-time in the integrated logging panel.

---

## Components

### 1. Core Classes

- **`LoadBalancer`**: Manages database connections and distributes queries.
- **`DatabaseProxy`**: Routes queries and interacts with the load balancer.
- **`DatabaseConnectionManagerFacade`**: Manages database connection lifecycle.

### 2. GUI Classes

- **`MainFrame`**: Main window for user interaction.
- **`LoggerPanelFactory`**: Creates a panel for displaying logs.

### 3. Supporting Classes

- **`RoundRobinStrategy`**: Implements a round-robin algorithm for load balancing.
- **`DatabaseConnectionWrapper`**: Wraps individual database connections.

---

## Installation

### Prerequisites

- **Java 17+**
- **Maven**
- At least two running database instances (e.g., PostgreSQL, MySQL).

### Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/database-load-balancer.git
   cd database-load-balancer
2. Configure your database connections in the DatabaseConnectionManagerFacade class.
3. Build the project using Maven:
   ```bash
   mvn clean install
4. Run the application:
   ```bash
   java -jar target/load-balancer.jar

## Usage
1. Launch the application.
2. Add database connections using the GUI or hardcoded configuration.
3. Execute SQL queries through the query input area.
4. Monitor:
   * Query Results: Displayed in the result panel.
   * Connection Status: Real-time updates in the status area.
   * Logs: Integrated logging panel at the bottom.

## Testing
* Tests are located in the src/test directory.
* Run tests with:
  ```bash
  mvn test

## License
This project is licensed under the MIT License. See the LICENSE file for details.
