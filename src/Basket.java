import java.util.ArrayList;

public class Basket {

    final public ArrayList<Product> basketProducts;

    public Basket () {
        basketProducts = new ArrayList<>();
    }

    public void addProduct(Product product) {
        basketProducts.add(product);
    }

    public void removeProduct(Product product) {
        try {
            basketProducts.remove(product);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Product was already removed");
        }
    }

    public int calculatePrice () {
        int sum = 0;
        for (Product basketProduct : basketProducts) {
            sum += basketProduct.getPrice();
        }
        return sum;
    }

    public void showBasket() {
        if(basketProducts.size() == 0) {
            System.out.println("\tBasket is empty...");
            return;
        }
        System.out.println("\tname price rating");
        for (Product p : basketProducts) {
            System.out.println("\t" + p.getName() + p.getPrice() + p.getRating());
        }
        System.out.println("total:" + calculatePrice());
    }
}
