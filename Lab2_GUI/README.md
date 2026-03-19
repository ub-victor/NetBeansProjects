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

### 🚲 Bicycle Time/Amount Calculator
- Enter a start time and end time (hours, integers).
- Click **Calculate** to compute total hours and the rental amount using a time-based pricing model.
- Save, update, and delete records from the database using the form.

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
private static final String PASSWORD = "Ushindi123!";
```

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

## 🧪 Testing / Quick Smoke Test

A tiny helper class exists at `src/main/java/util/TestSave.java` that can be used to verify DB persistence quickly (it saves a sample bicycle record).

---

## ✅ Notes / Next Improvements

- Add full CRUD list view for bicycles and mushrooms.
- Improve validation and error reporting (numeric parsing, missing fields).
- Externalize DB configuration (properties file or environment variables).
- Add unit tests for logic classes (`Calculator`, `MushroomLogic`).

---
