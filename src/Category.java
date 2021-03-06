import wagu.Block;
import wagu.Board;
import wagu.Table;

import java.awt.desktop.AboutEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Category {
    private String name;
    final private ArrayList<Product> productList;
    static List<Category> categories = new ArrayList<>();
    ;

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

        List<String> categoryHeaders = Arrays.asList("NUMBER", "NAME");
        List<List<String>> categoryData = new ArrayList<>();

        Board board = new Board(200);
        Block tableHeader = new Block(board, 74, 1, "LIST OF CATEGORIES");
        board.setInitialBlock(tableHeader.setDataAlign(Block.DATA_CENTER));
        Block categoryNumber = new Block(board, 8, 1, "NUMBER");
        tableHeader.setBelowBlock(categoryNumber.setDataAlign(Block.DATA_CENTER));
        Block categoryName = new Block(board, 65, 1, "NAME");
        categoryNumber.setRightBlock(categoryName.setDataAlign(Block.DATA_CENTER));

        List<Integer> productAlign = Arrays.asList(
                Block.DATA_CENTER,
                Block.DATA_MIDDLE_LEFT,
                Block.DATA_CENTER,
                Block.DATA_CENTER
        );
        int i = 0;
        int k = 5;
        for (Category c : Category.categories) {

            Block nextCategoryNumber = new Block(board, 8, 6 + c.productList.size(), String.valueOf(i++));
            categoryNumber.setBelowBlock(nextCategoryNumber.setDataAlign(Block.DATA_CENTER));
            categoryNumber = nextCategoryNumber;

            Block nextCategoryName = new Block(board, 65, 1, String.valueOf(c.name));
            categoryNumber.setRightBlock(nextCategoryName.setDataAlign(Block.DATA_CENTER));
            categoryName = nextCategoryName;

            Block productTableHeader = new Block(board, 65, 1, "LIST OF PRODUCTS");
            categoryName.setBelowBlock(productTableHeader.setDataAlign(Block.DATA_CENTER));
            categoryName = nextCategoryName;

            List<String> productHeaders = Arrays.asList("NUMBER", "NAME", "PRICE", "RATING");
            List<List<String>> productData = new ArrayList<>();
            List<Integer> productColumnWidth = Arrays.asList(8, 39, 7, 8);
            int j = 0;
            for (Product p : c.productList) {
                productData.add(Arrays.asList(
                        String.valueOf(j++),
                        p.getName(),
                        String.valueOf(p.getPrice()),
                        String.valueOf(p.getRating())));
            }
            board.appendTableTo(k, Board.APPEND_BELOW, new Table(board, 65, productHeaders, productData, productColumnWidth).setColAlignsList(productAlign));
            k += 11;
        }

        board.build();
        System.out.println(board.getPreview());

    }

}
