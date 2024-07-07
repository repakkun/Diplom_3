package api.pojo;

import com.github.javafaker.Faker;

public class UserCreate {
    private String email;
    private String password;
    private String name;
    static Faker faker = new Faker();

    public UserCreate() {
    }

    public UserCreate(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static UserCreate random() {
        return new UserCreate("Pakkun" + faker.name().firstName() + "@gmail.com", "88888888", "Ilya" + faker.name().lastName());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
