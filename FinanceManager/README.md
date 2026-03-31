# FinanceManager

A comprehensive Personal Finance Management application built in Java using Swing for the GUI and MySQL for data persistence. This application allows users to track income, expenses, set budgets, and generate reports, with role-based access for regular users and administrators.

## Features

- **User Management**: Secure login system with role-based access (User and Admin roles)
- **Transaction Tracking**: Record and categorize income and expenses with detailed descriptions
- **Budget Management**: Set monthly budgets for different categories and track spending against them
- **Category Management**: Organize transactions into customizable income and expense categories
- **Reports and Analytics**: Generate reports on financial activities and budget performance
- **Admin Dashboard**: Administrative interface for managing users, categories, and system-wide data
- **User Dashboard**: Personalized dashboard for individual financial management

## Architecture

The application follows a layered architecture:

- **UI Layer**: Swing-based graphical user interface components in the `ui` package
- **Business Logic Layer**: Data Access Objects (DAOs) in the `dao` package for database operations
- **Data Layer**: Model classes in the `models` package representing database entities
- **Database Layer**: MySQL database connection and utilities in the `db` package
- **Utilities**: Session management and helper classes in the `utils` package

### Key Components

- **Models**: `User`, `Transaction`, `Category`, `Budget` - Represent database tables
- **DAOs**: `UserDAO`, `TransactionDAO`, `CategoryDAO`, `BudgetDAO` - Handle CRUD operations
- **UI Components**: Login screen, user/admin dashboards, dialogs for adding/editing data
- **Database**: MySQL database named `finance_manager`

## Prerequisites

- **Java Development Kit (JDK)**: Version 8 or higher
- **Apache NetBeans IDE**: Version 12.0 or higher (recommended for development)
- **MySQL Server**: Version 5.7 or higher
- **MySQL Connector/J**: JDBC driver (included in the project)

## Installation and Setup

### 1. Clone or Download the Project

Place the project in your NetBeans projects directory:
```
/path/to/netbeans/projects/FinanceManager
```

### 2. Database Setup

1. Install and start MySQL Server
2. Create a database named `finance_manager`:
   ```sql
   CREATE DATABASE finance_manager;
   ```
3. Create the required tables (see Database Schema section below)
4. Update database credentials in `src/financemanager/db/DatabaseConnection.java` if needed:
   - Default URL: `jdbc:mysql://localhost:3306/finance_manager`
   - Default User: `root`
   - Default Password: `Ushindi123!`

### 3. MySQL Connector Setup

The project includes a reference to MySQL Connector/J. Ensure the JAR file is available at:
```
/home/victoire/mysql-connector-j-9.6.0/mysql-connector-j-9.6.0.jar
```
Update the path in `nbproject/project.properties` if your connector is located elsewhere.

## Running the Application

### Using Apache NetBeans

1. **Open the Project**:
   - Launch Apache NetBeans
   - Go to `File > Open Project`
   - Navigate to the `FinanceManager` directory and select it
   - Click `Open Project`

2. **Build the Project**:
   - Right-click the project in the Projects window
   - Select `Build` or press `F11`
   - This compiles the source code and creates the JAR file

3. **Run the Application**:
   - Right-click the project
   - Select `Run` or press `F6`
   - The application will start with the login screen

### Manual Build and Run

If you prefer to build manually:

1. **Compile**:
   ```bash
   ant compile
   ```

2. **Build JAR**:
   ```bash
   ant jar
   ```

3. **Run**:
   ```bash
   java -cp "build/classes:lib/mysql-connector-j-9.6.0.jar" financemanager.ui.LoginFrame
   ```

## Database Schema

The application uses the following MySQL tables:

### Users Table
```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('user', 'admin') NOT NULL
);
```

### Categories Table
```sql
CREATE TABLE categories (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    type ENUM('income', 'expense') NOT NULL
);
```

### Transactions Table
```sql
CREATE TABLE transactions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    category_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    date DATE NOT NULL,
    description TEXT,
    type ENUM('income', 'expense') NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);
```

### Budgets Table
```sql
CREATE TABLE budgets (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    category_id INT NOT NULL,
    month_year DATE NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);
```

## Sample Data

To test the application, you can insert the following sample data:

### Sample Users
```sql
INSERT INTO users (username, password, role) VALUES
('admin', 'admin123', 'admin'),
('john_doe', 'password123', 'user'),
('jane_smith', 'password456', 'user');
```

### Sample Categories
```sql
INSERT INTO categories (name, type) VALUES
('Salary', 'income'),
('Freelance', 'income'),
('Food', 'expense'),
('Transportation', 'expense'),
('Entertainment', 'expense'),
('Utilities', 'expense');
```

### Sample Transactions (for user john_doe, id=2)
```sql
INSERT INTO transactions (user_id, category_id, amount, date, description, type) VALUES
(2, 1, 3000.00, '2024-01-01', 'Monthly salary', 'income'),
(2, 3, 150.00, '2024-01-05', 'Groceries', 'expense'),
(2, 4, 50.00, '2024-01-10', 'Bus fare', 'expense'),
(2, 2, 500.00, '2024-01-15', 'Freelance project', 'income'),
(2, 5, 75.00, '2024-01-20', 'Movie tickets', 'expense');
```

### Sample Budgets (for user john_doe, id=2)
```sql
INSERT INTO budgets (user_id, category_id, month_year, amount) VALUES
(2, 3, '2024-01-01', 400.00),  -- Food budget
(2, 4, '2024-01-01', 100.00),  -- Transportation budget
(2, 5, '2024-01-01', 150.00);  -- Entertainment budget
```

## Usage Guide

### Login
- Use the provided sample usernames and passwords to log in
- Admin users have access to user management and system-wide features
- Regular users can manage their personal finances

### User Dashboard
- View recent transactions
- Add new transactions
- Set and monitor budgets
- Generate financial reports

### Admin Dashboard
- Manage user accounts
- Add/edit/delete categories
- View system-wide reports

### Adding Transactions
1. Click "Add Transaction" in the user dashboard
2. Select transaction type (Income/Expense)
3. Choose a category
4. Enter amount and date
5. Add description (optional)
6. Save the transaction

### Managing Budgets
1. Go to "Manage Budgets" in the user dashboard
2. Select a category and month
3. Set the budget amount
4. Monitor spending against budget limits

## Development

### Project Structure
```
FinanceManager/
├── build.xml                 # Ant build script
├── manifest.mf              # JAR manifest
├── nbproject/               # NetBeans project files
├── src/
│   └── financemanager/
│       ├── dao/            # Data Access Objects
│       ├── db/             # Database connection
│       ├── models/         # Data models
│       ├── ui/             # User interface components
│       └── utils/          # Utility classes
└── lib/                    # External libraries
```

### Technologies Used
- **Java**: Core programming language
- **Swing**: GUI framework
- **MySQL**: Database management system
- **JDBC**: Database connectivity
- **Apache Ant**: Build automation

## Troubleshooting

### Common Issues

1. **Database Connection Error**:
   - Ensure MySQL server is running
   - Verify database credentials in `DatabaseConnection.java`
   - Check if `finance_manager` database exists

2. **MySQL Connector Not Found**:
   - Verify the path to `mysql-connector-j-9.6.0.jar` in `project.properties`
   - Ensure the JAR file exists at the specified location

3. **Build Errors**:
   - Clean and rebuild the project in NetBeans
   - Check JDK version compatibility

4. **Application Won't Start**:
   - Ensure main class is set correctly (should be auto-detected)
   - Check console output for error messages

### Logs and Debugging
- Run the application from NetBeans to see console output
- Check MySQL error logs for database issues
- Use NetBeans debugger for step-through debugging

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support or questions, please contact the development team or create an issue in the project repository.