import java.util.*;

public class SimpleConsoleShop {

    static Scanner scanner = new Scanner(System.in);
    static Admin admin;
    static User currentUser;

    private static void createAdmin() {
        System.out.println("Enter login for the admin.");
        String login = scanner.nextLine();
        System.out.println("Enter password for the admin.");
        String password = scanner.nextLine();
        admin = new Admin(login, password);
        Admin.admin = admin;
    }

    static void start() {
        boolean repeat = false;
        do {
            System.out.println("""
                    Welcome to Shopping Planet!
                    Login Menu.
                    0 | Login.
                    1 | Register.
                    2 | Quit."""
            );
            repeat = false;
            switch (scanner.nextLine().toLowerCase()) {
                case "0" -> {
                    System.out.println("Enter login:");
                    String login = scanner.nextLine();
                    System.out.println("Enter Password:");
                    String password = scanner.nextLine();
                    currentUser = new User(login, password);
                    if (Admin.isAdmin(currentUser)) {
                        currentUser = admin;
                        User.logIn();
                        System.out.println("You were logged in as Admin.");
                        return;
                    }
                    if (Admin.validate(login, password)) {
                        User.logIn();
                        System.out.println("You were logged in!");
                    } else {
                        System.out.println("Incorrect login or password!");
                        start();
                    }
                }
                case "1" -> {
                    System.out.println("Enter login:");
                    String login = scanner.nextLine();
                    System.out.println("Enter Password:");
                    String password = scanner.nextLine();
                    System.out.println("Repeat Password:");
                    String password2 = scanner.nextLine();

                    for (User user : Admin.users) {
                        if (login.equals(user.getLogin())) {
                            System.out.println("Login is taken try new one");
                            repeat = true;
                            break;
                        }
                    }
                    if (repeat == true) {
                        break;
                    }
                    currentUser = new User(login, password, password2);
                }
                case "2" -> {
                    User.logOut();
                    repeat = false;
                }
                default -> {
                    repeat = true;
                    System.out.println("Invalid input! Make sure you input numbers not words itself.");
                }
            }

        } while (repeat);
    }

    static void adminPanel() {
        String input;
        do {
            System.out.println("""
                    Admin Panel.
                    0 | Manage Categories.
                    1 | Manage Products.
                    2 | Manage Users.
                    3 | Log out."""
            );
            input = scanner.nextLine();

            switch (input) {
                case "0" -> categoryManagement();
                case "1" -> productManagement();
                case "2" -> userManagement();
                case "3" -> {
                    User.logOut();
                    start();
                }
                default -> System.out.println("Invalid input! Make sure you input numbers not words itself.");
            }
        } while (!input.equals("3"));
    }

    static void userPanel() {
        String input;
        do {
            System.out.println("""
                    User menu.
                    0 | Show Categories.
                    1 | Show Products.
                    2 | Add products to basket.
                    3 | Show Basket.
                    4 | Remove product from basket.
                    5 | Buy products from basket.
                    6 | Log out."""
            );
            input = scanner.nextLine();
            switch (input) {
                case "0" -> Category.showCategories();
                case "1" -> Product.showProducts();
                case "2" -> {
                    int input2;
                    do {
                        System.out.println("Choose category and product from the list: (type 2 numbers)");
                        Category.showCategories();
                        System.out.println(Category.categories.size() + ". Back.");
                        input2 = scanner.nextInt();
                        if (input2 == Category.categories.size()) {
                            break;
                        }
                        int input3 = scanner.nextInt();
                        currentUser.basket.addProduct(Category.categories.get(input2).getProductList().get(input3));
                    } while (input2 != Category.categories.size());

                }
                case "3" -> currentUser.basket.showBasket();
                case "4" -> {
                    System.out.println("choose product to be removed.");
                    currentUser.basket.showBasket();
                    int index = scanner.nextInt();
                    currentUser.basket.removeProduct(currentUser.basket.basketProducts.get(index));
                }
                case "5" -> {
                    currentUser.basket.showBasket();
                    System.out.println("Proceed the payment.");
                    double cash = scanner.nextDouble();
                    System.out.println("payment " + (cash > currentUser.basket.calculatePrice() ? "" : "not") + "succeed");
                }
                case "6" -> {
                    User.logOut();
                    start();
                }
            }
        } while (!input.equals("6"));
    }

    static void categoryManagement() {
        String input;
        do {
            System.out.println("""
                    Categories Management Panel.
                    0 | Add Category.
                    1 | Show Categories.
                    2 | Edit Category.
                    3 | Delete Category.
                    4 | Back.""");
            Category category;
            input = scanner.nextLine();
            switch (input) {
                case "0" -> {
                    System.out.println("Enter name for the new category");
                    String name = scanner.nextLine();
                    category = new Category(name);
                    Category.categories.add(category);
                    System.out.println("""
                            Want to add products?
                            0 | Yes.
                            1 | No.""");
                    if (!scanner.nextLine().equals("0")) {
                        System.out.println("Category was added, but empty");
                        break;
                    }
                    String l;
                    do {
                        System.out.println("Enter index of product.");
                        Product.showProducts();
                        System.out.println(Product.products.size() + " | Back");
                        l = scanner.nextLine();
                        if (l.equals(String.valueOf(Product.products.size()))) {
                            break;
                        }
                        try {
                            category.addProduct(Product.products.get(Integer.parseInt(l)));
                            Category.categories.set(Category.categories.indexOf(category), category);
                            System.out.println("Product was added!");
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Input!");
                        }
                    } while (!l.equals(String.valueOf(Product.products.size())));
                }
                case "1" -> Category.showCategories();
                case "2" -> {
                    if (Category.categories.size() == 0) {
                        System.out.println("No category to edit");
                        return;
                    }
                    int index;
                    do {
                        System.out.println("Choose category to edit.");
                        Category.showCategories();
                        System.out.println(Category.categories.size() + " | Back.");
                        index = Integer.parseInt(scanner.nextLine());
                        category = Category.categories.get(index);
                        if (index == Category.categories.size()) {
                            break;
                        }
                        String input2 = scanner.nextLine();
                        do {
                            System.out.println("""
                                    Choose action:
                                    0 | Change name.
                                    1 | Add product.
                                    2 | Delete product.
                                    3 | Back.""");
                            switch (input2) {
                                case "0" -> {
                                    System.out.println("Old name: " + category.getName());
                                    System.out.println("Enter new name: ");
                                    category.setName(scanner.nextLine());
                                    Category.categories.set(index, category);
                                }
                                case "1" -> {
                                    String l;
                                    do {
                                        Product.showProducts();
                                        System.out.println(Product.products.size() + " | Back");
                                        System.out.println("Enter index of product");
                                        l = scanner.nextLine();
                                        try {
                                            category.addProduct(Product.products.get(Integer.parseInt(l)));
                                            Category.categories.set(index, category);
                                        } catch (NumberFormatException e) {
                                            System.out.println("Invalid Input!");
                                        }
                                    } while (!l.equals(String.valueOf(Product.products.size())));
                                }
                                case "2" -> {
                                    String l;
                                    do {
                                        Product.showProducts();
                                        System.out.println(Product.products.size() + " | Back");
                                        System.out.println("Enter index of product");
                                        l = scanner.nextLine();
                                        try {
                                            category.removeProduct(Product.products.get(Integer.parseInt(l)));
                                            Category.categories.set(index, category);
                                        } catch (NumberFormatException e) {
                                            System.out.println("Invalid Input!");
                                        }
                                    } while (!l.equals(String.valueOf(Product.products.size())));

                                }
                                case "3" -> {
                                }
                                default -> System.out.println("Incorrect input");
                            }
                        } while (!input2.equals("3"));
                    } while (index != Category.categories.size());
                }
                case "3" -> {
                    System.out.println("Choose category to delete");
                    Category.showCategories();
                    int index = Integer.parseInt(scanner.nextLine());
                    category = Category.categories.get(index);
                    System.out.println("Deletion " + (Category.categories.remove(category) ? "Successful" : "Not Successful. An error occurred."));
                }
                case "4" -> {
                }
                default -> System.out.println("Invalid input! Make sure you input numbers not words itself.");
            }
        } while (!input.equals("4"));
        adminPanel();
    }

    static void productManagement() {
        Product product;
        Category category;
        String input;
        do {
            System.out.println("""
                    Product Management Panel.
                    0 | Add product.
                    1 | Show products.
                    2 | Edit product.
                    3 | Delete product.
                    4 | Back."""
            );
            input = scanner.nextLine();
            int index;
            switch (input) {
                case "0" -> {
                    System.out.println("Enter name, price and rating for the new product:");
                    String name = scanner.nextLine();
                    double price = Double.parseDouble(scanner.nextLine());
                    double rating = Double.parseDouble(scanner.nextLine());
                    product = new Product(name, price, rating);
                    Product.products.add(product);
                    System.out.println("""
                            Want to add it into category?
                            0 | Yes.
                            1 | No.""");
                    if (!scanner.nextLine().equals("0")) {
                        System.out.println("Product was added, but not added to a category.");
                        break;
                    }
                    String l;
                    do {
                        System.out.println("Enter index of category.");
                        Category.showCategories();
                        System.out.println(Category.categories.size() + " | Back");
                        l = scanner.nextLine();

                        try {
                            category = Category.categories.get(Integer.parseInt(l));
                            category.addProduct(product);
                            Category.categories.set(Integer.parseInt(l), category);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Input!");
                        }
                    } while (!l.equals(String.valueOf(Category.categories.size())));
                }
                case "1" -> Product.showProducts();
                case "2" -> {
                    if (Product.products.size() == 0) {
                        System.out.println("No products to edit");
                        return;
                    }
                    System.out.println("Choose product to edit.");
                    Product.showProducts();
                    System.out.println(Product.products.size() + " | Back.");
                    index = Integer.parseInt(scanner.nextLine());
                    product = Product.products.get(index);
                    if (index == Product.products.size()) {
                        break;
                    }
                    String input2 = scanner.nextLine();
                    System.out.println("""
                            Choose action:
                            0 | Change name.
                            1 | Change price.
                            2 | Back.""");
                    switch (input2) {
                        case "0" -> {
                            System.out.println("Old name: " + product.getName());
                            System.out.println("Enter new name: ");
                            product.setName(scanner.nextLine());
                            Product.products.set(index, product);
                        }
                        case "1" -> {
                            System.out.println("Old price: " + product.getPrice());
                            System.out.println("Enter new price: ");
                            product.setPrice(Double.parseDouble(scanner.nextLine()));
                            Product.products.set(index, product);
                        }
                        default -> System.out.println("Incorrect input");
                    }
                }
                case "3" -> {
                    System.out.println("Choose product to delete");
                    Category.showCategories();
                    index = Integer.parseInt(scanner.nextLine());
                    product = Product.products.get(index);
                    System.out.println("Deletion " + (Product.products.remove(product) ? "Successful" : "Not Successful. An error occurred."));
                }
                case "4" -> {
                }
                default -> System.out.println("Invalid input! Make sure you input numbers not words itself.");
            }

        } while (!input.equals("4"));
    }

    static void userManagement() {
        currentUser = admin;
        User user;
        String input;
        do {
            System.out.println("""
                    Categories Management Panel.
                    0 | Add User.
                    1 | Show Users.
                    2 | Edit User.
                    3 | Delete User.
                    4 | Back.""");
            input = scanner.nextLine();
            switch (input) {
                case "0" -> {
                    System.out.println("Enter login and password for the new user");
                    String login = scanner.nextLine();
                    String password = scanner.nextLine();
                    user = new User(login, password);
                    Admin.users.add(user);
                }
                case "1" -> Admin.showUsers();
                case "2" -> {
                    int index;
                    System.out.println("Choose user to edit.");
                    Admin.showUsers();
                    System.out.println(Admin.users.size() + " | Back.");
                    index = Integer.parseInt(scanner.nextLine());
                    user = Admin.users.get(index);
                    if (index == Product.products.size()) {
                        break;
                    }
                    String input2 = scanner.nextLine();
                    System.out.println("""
                            Choose action:
                            0 | Change login.
                            1 | Change password.
                            2 | Back.""");
                    switch (input2) {
                        case "0" -> {
                            System.out.println("Old login: " + user.getLogin());
                            System.out.println("Enter new name: ");
                            user.setLogin(scanner.nextLine());
                        }
                        case "1" -> {
                            System.out.println("Old password: " + user.getPassword());
                            System.out.println("Enter new password: ");
                            user.setPassword(scanner.nextLine());
                            Admin.users.set(index, user);
                        }
                        default -> System.out.println("Incorrect input");
                    }
                    Admin.users.set(index, user);
                }
                case "3" -> {
                    System.out.println("Choose user to delete");
                    Admin.showUsers();
                    int index = Integer.parseInt(scanner.nextLine());
                    user = Admin.users.get(index);
                    System.out.println("Deletion " + (Admin.users.remove(user) ? "Successful" : "Not Successful. An error occurred."));
                }
                case "4" -> {
                }
                default -> System.out.println("Incorrect input");
            }
        } while (!input.equals("4"));
    }

    public static void main(String[] args) {


        Product w1 = new Product("Winter Wheels", 50D, 4);
        Product w2 = new Product("Summer Wheels", 40D, 5);
        Product w3 = new Product("Wheels for bicycle", 30D, 4.5);
        Product w4 = new Product("Bike Wheels", 400D, 4);

        Product f1 = new Product("Apple", 27.49, 5);
        Product f2 = new Product("Mango", 40D, 4);
        Product f3 = new Product("Avocado", 30D, 3);
        Product f4 = new Product("Peach", 25D, 4.5);

        Product a1 = new Product("Chain-mail", 100, 5);
        Product a2 = new Product("Leggings", 100, 5);
        Product a3 = new Product("Gloves", 100, 5);
        Product a4 = new Product("Helmet", 100, 5);

        Category wheels = new Category("Wheels");
        Category fruits = new Category("Fruits");
        Category armor = new Category("Armor");

        wheels.addProduct(w1);
        wheels.addProduct(w2);
        wheels.addProduct(w3);
        wheels.addProduct(w4);

        fruits.addProduct(f1);
        fruits.addProduct(f2);
        fruits.addProduct(f3);
        fruits.addProduct(f4);

        armor.addProduct(a1);
        armor.addProduct(a2);
        armor.addProduct(a3);
        armor.addProduct(a4);

        Product.products.addAll(wheels.getProductList());
        Product.products.addAll(fruits.getProductList());
        Product.products.addAll(armor.getProductList());

        Category.categories.add(wheels);
        Category.categories.add(fruits);
        Category.categories.add(armor);

        for (int i = 0; i < Category.categories.size(); i++) {
            for (int j = 0; j < 4; j++) {
                Category category = Category.categories.get(i);
                category.addProduct(Product.products.get(j));
                Category.categories.set(i, category);
            }
        }

        Category.showCategories();

//        createAdmin();
//
//        start();
//
//        while (User.isLoggedIn()) {
//            if (Admin.isAdmin(currentUser)) {
//                adminPanel();
//            } else {
//                userPanel();
//            }
//        }
//
    }

}
