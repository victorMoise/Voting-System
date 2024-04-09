public class User {
    private int id;
    private String username;
    private String password;
    private String role;
    private int age;

    public User(int id, String username, String password, String role, int age) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public int getAge() {
        return age;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toString() {
        return "User [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + ", age=" + age + "]";
    }
}
