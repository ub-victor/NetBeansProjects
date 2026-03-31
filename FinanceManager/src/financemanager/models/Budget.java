package financemanager.models;

import java.math.BigDecimal;
import java.sql.Date;

public class Budget {
    private int id;
    private int userId;
    private int categoryId;
    private Date monthYear;   // first day of month
    private BigDecimal amount;

    // constructors, getters, setters
}