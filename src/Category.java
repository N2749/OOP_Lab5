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
        int i = 0;


        String tHeader = "LIST OF CATEGORIES";

        List<String> categoryHeaders = Arrays.asList("NUMBER", "NAME");
        List<List<String>> categoryData = new ArrayList<>();

        Board board = new Board(83);
        Block tableHeader = new Block(board, 81, 1, tHeader);
        board.setInitialBlock(tableHeader.setDataAlign(Block.DATA_CENTER));
        Block categoryNumber = new Block(board, 8, 1, "Number");
        tableHeader.setBelowBlock(categoryNumber.setDataAlign(Block.DATA_CENTER));
        Block categoryName = new Block(board, 72, 1, "Name");
        categoryNumber.setRightBlock(categoryName.setDataAlign(Block.DATA_CENTER));

//        List<Integer> colAlignList = Arrays.asList(
//                Block.DATA_CENTER,
//                Block.DATA_MIDDLE_LEFT,
//                Block.DATA_CENTER,
//                Block.DATA_CENTER);
//
//        List<Integer> colWidths = Arrays.asList(8, 8, 39, 7, 8);

        for (Category c : Category.categories) {

            Block nextCategoryNumber = new Block(board, 8, c.productList.size() - 1, String.valueOf(i++));
            categoryNumber.setBelowBlock(nextCategoryNumber.setDataAlign(Block.DATA_TOP_MIDDLE));
            categoryNumber = nextCategoryNumber;

            Block nextCategoryName = new Block(board, 72,  1, String.valueOf(c.name));
            categoryName.setBelowBlock(nextCategoryName.setDataAlign(Block.DATA_CENTER));
            categoryName = nextCategoryName;

            List<String> productHeaders = Arrays.asList("NUMBER", "NAME", "PRICE", "RATING");
            List<List<String>> productData = new ArrayList<>();
            List<Integer> productColumnWidth = Arrays.asList(8, 8, 39, 7, 8);
            for (Product p : c.productList) {
                productData.add(Arrays.asList(
                        String.valueOf(i++),
                        p.getName(),
                        String.valueOf(p.getPrice()),
                        String.valueOf(p.getRating())));
            }
            //board.appendTableTo(0, Board.APPEND_BELOW, new Table(board, 74, categoryHeaders, categoryData));
        }

        board.build();
        System.out.println(board.getPreview());

//        for (Category c : categories) {
//            System.out.println("Number | Name");
//            System.out.printf("%-7n| %s", i, c.name);
//            int j = 0;
//            for (Product p : c.productList) {
//                if (c.productList.size() == 0) {
//                    System.out.printf("%-7s| Empty Category...", "");
//                    break;
//                }
//
//                System.out.println("\t\t" + j++ +". " + p.getName() + " " + p.getPrice() + " " + p.getRating());
//            }
//        }
    }

}
