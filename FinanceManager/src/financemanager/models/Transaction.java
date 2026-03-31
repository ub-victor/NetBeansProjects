package financemanager.models;

import java.math.BigDecimal;
import java.sql.Date;

public class Transaction {
    private int id;
    private int userId;
    private int categoryId;
    private BigDecimal amount;
    private Date date;
    private String description;
    private String type;

    // constructors, getters, setters
}