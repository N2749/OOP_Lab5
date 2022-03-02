import wagu.Block;
import wagu.Board;
import wagu.Table;

import java.util.ArrayList;
import java.util.Arrays;
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
        if (products == null) {
            System.out.println("No Products yet.");
            return;
        }
        int i = 0;
        String tHeader = "LIST OF PRODUCTS";
        List<String> headers = Arrays.asList("NUMBER", "NAME", "PRICE", "RATING");
        List<List<String>> tData = new ArrayList<>();
        for (Product p : Product.products) {
            tData.add(Arrays.asList(
                    String.valueOf(i++),
                    p.name,
                    String.valueOf(p.price),
                    String.valueOf(p.getRating())));

        }

        Board board = new Board(67);

        List<Integer> colAlignList = Arrays.asList(
                Block.DATA_CENTER,
                Block.DATA_MIDDLE_LEFT,
                Block.DATA_CENTER,
                Block.DATA_CENTER);

        List<Integer> colWidths = Arrays.asList(8, 39, 7, 8);

        board.setInitialBlock(new Block(board, 65, 1, tHeader).setDataAlign(Block.DATA_CENTER));
        board.appendTableTo(0, Board.APPEND_BELOW, new Table(board, 67, headers, tData).setColWidthsList(colWidths).setColAlignsList(colAlignList));

        board.build();
        System.out.println(board.getPreview());
    }
}
