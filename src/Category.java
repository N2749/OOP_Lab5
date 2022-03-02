import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    final private ArrayList<Product> productList;
    static List<Category> categories = new ArrayList<>();;

    public Category(String name) {
        this.name = name;
        productList = new ArrayList<>();
        categories.add(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public void removeProduct(Product product) {
        try {
            productList.remove(product);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Product was already removed");
        }
    }

    public static void showCategories() {
        if (categories == null) {
            System.out.println("No Categories yet.");
            return;
        }
        int i = 0;
        System.out.println("List of categories:");
        for (Category c : categories) {
            System.out.println("Number | Name");
            System.out.printf("%-7n| %s", i, c.name);
            int j = 0;
            for (Product p : c.productList) {
                if (c.productList.size() == 0) {
                    System.out.printf("%-7s| Empty Category...", "");
                    break;
                }

                System.out.println("\t\t" + j++ +". " + p.getName() + " " + p.getPrice() + " " + p.getRating());
            }
        }
    }

}
