import java.util.ArrayList;
import java.util.List;

public class Admin extends User {

    static List<User> users = new ArrayList<>();
    static Admin admin;

    public Admin(String login, String password) {
        super(login, password);
    }

    public static boolean isAdmin(User pretender) {
        if(!pretender.getLogin().equals(admin.getLogin())) {
            if(!pretender.getPassword().equals(admin.getPassword())) {
                return false;
            }
        }
        return true;
    }

    public static boolean validate(String login, String password) {
        for (User u : users) {
            if (u.getLogin().equals(login)) {
                if (u.getPassword().equals(password)) {
                    return true;
                }
            }
        }
        return false;
    }

    static public void showUsers() {
        int i = 0;
        if (users == null) {
            System.out.println("No users yet.");
            return;
        }
        System.out.println("List of users");
        System.out.println("\tlogin password");
        for (User user : users) {
            System.out.println("\t " + i + ". " + user.getLogin() + " " + user.getPassword());
        }
    }
}
