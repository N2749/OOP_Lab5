import java.util.ArrayList;
import java.util.List;

public class Product {
    private String name;
    private double price;
    private double rating;
    static List<Product> products = new ArrayList<>();

    public Product(String name, double price, double rating) {
        this.name = name;
        this.price = price;
        this.rating = rating;
        products.add(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price > 0D) {
            this.price = price;
        } else {
            System.out.println("Invalid Price!");
        }
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }

    public static void showProducts() {
        int i = 0;
        if (products == null) {
            System.out.println("No Products yet.");
            return;
        }
        for (Product p : Product.products) {
            System.out.println("List of products:");
            System.out.println("\tname price rating");
            System.out.println("\t" + i++ +". " + p.name + " " + p.price + " " + p.getRating());
        }
    }
}
