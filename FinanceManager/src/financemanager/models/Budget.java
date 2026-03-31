package financemanager.models;

import java.math.BigDecimal;
import java.sql.Date;

public class Budget {
    private int id;
    private int userId;
    private int categoryId;
    private Date monthYear;
    private BigDecimal amount;

    public Budget() {}

    public Budget(int id, int userId, int categoryId, Date monthYear, BigDecimal amount) {
        this.id = id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.monthYear = monthYear;
        this.amount = amount;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public Date getMonthYear() { return monthYear; }
    public void setMonthYear(Date monthYear) { this.monthYear = monthYear; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}