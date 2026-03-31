package financemanager.models;

public class User {
    private int id;
    private String username;
    private String password;
    private String role;

    // constructors, getters, setters
    public User() {}
    public User(int id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
    // ... add getters and setters
}