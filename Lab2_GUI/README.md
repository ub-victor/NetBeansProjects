# Lab2_GUI

This is a Java Swing desktop application built as part of Lab 2. It demonstrates a simple GUI with two modules:

- **Mushroom identification** (decision logic + persistence)
- **Bicycle rental/usage calculator** (time-based calculation + persistence)

Both modules store data in a local MySQL database.

---

## ✅ Features

### 🍄 Mushroom Identification
- Pick a combination of mushroom traits (gills, forest, ring, convex).
- Click **Identify** to classify the mushroom using simple boolean logic.
- Click **Save** to persist the identified mushroom to the database.

> 🔎 The logic is implemented in `src/main/java/util/MushroomLogic.java`.

### 🚲 Bicycle Time/Amount Calculator
- Enter a start time and end time (hours, integers).
- Click **Calculate** to compute total hours and the rental amount using a time-based pricing model.
- Save, update, and delete records from the database using the form.

> 🔎 The pricing logic is implemented in `src/main/java/util/Calculator.java`.

---

## 🧩 Project Structure

- `src/main/java/ui/` – Swing UI forms for **MushroomForm**, **BicycleForm** and launcher `Lab2_GUI`
- `src/main/java/util/` – Helper logic (`Calculator`, `MushroomLogic`) and database connection (`DBConnection`)
- `src/main/java/model/` – POJOs (`Mushroom`, `Bicycle`)
- `src/main/java/dao/` – Data access objects for saving/updating/deleting data

---

## 🛠 Requirements

- Java 21 (configured via Maven `maven.compiler.release`)
- Maven
- MySQL running locally (or reachable via JDBC)

---

## 🔧 Database Setup

This project expects a MySQL database named `lab2_db`. Update the connection details in:

- `src/main/java/util/DBConnection.java`

```java
private static final String URL = "jdbc:mysql://localhost:3306/lab2_db";
private static final String USER = "root";
private static final String PASSWORD = "Ushindi123!"; // change this
```

⚠️ **Security note:** Storing credentials in source code is not recommended for production. Prefer environment variables or a configuration properties file.

### Recommended schema (example)

```sql
CREATE DATABASE IF NOT EXISTS lab2_db;
USE lab2_db;

CREATE TABLE IF NOT EXISTS mushroom (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  has_gills BOOLEAN NOT NULL,
  forest BOOLEAN NOT NULL,
  has_ring BOOLEAN NOT NULL,
  convex BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS bicycle (
  id INT AUTO_INCREMENT PRIMARY KEY,
  start_time INT NOT NULL,
  end_time INT NOT NULL,
  total_hours INT NOT NULL,
  amount INT NOT NULL
);
```

---

## ▶️ Run the Application

### Option 1: From IDE (recommended)
1. Import the project as a Maven project (NetBeans / IntelliJ / Eclipse).
2. Ensure the database is running and credentials in `DBConnection` are correct.
3. Run `ui.Lab2_GUI` (`Lab2_GUI` class) to open the GUI.

> 💡 Note: `Lab2_GUI` launches the Mushroom form by default. To run the Bicycle form directly, launch `ui.BicycleForm` instead.

### Option 2: Using Maven from the command line

```bash
mvn clean package
```

Then run the app with the classpath including the MySQL connector:

```bash
java -cp "target/Lab2_GUI-1.0-SNAPSHOT.jar:$(echo ~/.m2/repository/com/mysql/mysql-connector-j/9.0.0/mysql-connector-j-9.0.0.jar)" ui.Lab2_GUI
```

> ⚠️ **Note:** The classpath command above is for Unix/Linux. On Windows, replace `:` with `;`.

---

## 🧪 Quick Smoke Tests

There are two tiny helper classes useful for verifying database connectivity:

- `src/main/java/util/TestDB.java` — attempts to open a DB connection and prints success/failure.
- `src/main/java/util/TestSave.java` — saves a sample bicycle record to the database.

Run either class from your IDE (or using `mvn exec:java` with the correct main class).

---

## 🛠 How the Code Works (Quick Notes)

### Mushroom Identification
- The GUI reads checkbox values and calls `MushroomLogic.identify(...)`.
- The result text is shown in the form and can be saved to the DB via `MushroomDAO.save()`.

### Bicycle Calculator
- `Calculate` converts `start`/`end` (hour integers) into `totalHours` and `amount`.
- The amount uses this pricing model:
  - 0–6, 21–23 → 500 per hour
  - 7–13, 19–20 → 1000 per hour
  - 14–18 → 1500 per hour
- Save / Update / Delete operations are handled by `BicycleDAO`.

---

## ✅ Notes / Next Improvements

- Add full CRUD list view for bicycles and mushrooms (currently there is no listing view).
- Improve validation and error reporting (numeric parsing, missing fields, invalid times).
- Externalize DB configuration (properties file, env vars, or a secrets manager).
- Add unit tests for logic classes (`Calculator`, `MushroomLogic`).

---
