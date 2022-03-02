public class User {
    private String login;
    private String password;
    public Basket basket;
    static boolean isLoggedIn;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        basket = new Basket();
        Admin.users.add(this);
    }

    public User(String login, String password, String repeatedPassword) {
        if (password.equals(repeatedPassword)) {
            this.login = login;
            this.password = password;
            basket = new Basket();
            isLoggedIn = true;
            Admin.users.add(this);
        } else {
            System.out.println("Password mismatch!");
            return;
        }
        System.out.println("You were successfully registered");
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void changePassword(String oldPassword, String newPassword, String repeatedNewPassword) {
        if (oldPassword.equals(password)) {
            if (newPassword.equals(repeatedNewPassword)) {
                password = newPassword;
            } else {
                System.out.println("Password mismatch!");
            }
        } else {
            System.out.println("Wrong Password!");
        }
    }

    public static boolean isLoggedIn() {
        return isLoggedIn;
    }

    public static void logIn() {
        isLoggedIn = true;
    }

    public static void logOut() {
        isLoggedIn = false;
    }

}
