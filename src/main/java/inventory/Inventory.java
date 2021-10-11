package inventory;

import util.Globals;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Singleton Class to hold a list of all the items in the inventory
 */
public class Inventory {

    private static Inventory instance;

    public static Inventory getInstance(){
        if(instance == null){
            instance = new Inventory();
        }
        return instance;
    }

    private final ArrayList<InventoryItem> productList = new ArrayList<>();

    /**
     * Adds a new Product to the inventory
     * @param product Product to be added
     * @param quantity quantity of the product
     */
    public void add(Product product, int quantity){
        productList.add(new InventoryItem(product, quantity));
    }

    /**
     * reduces the quantity of a product.
     * @param id id of the product
     * @param quantity quantity to be reduced.
     */
    public void reduceStock(int id, int quantity){

        for(InventoryItem item : productList){
            if(item.getProduct().getId() == id){
                item.setQuantity(item.getQuantity() - quantity);
            }
        }
    }

    /**
     * updates the quantity of the product
     * @param id id of the product
     * @param quantity the quantity to be added.
     */
    public void updateStock(int id, int quantity){
        for(InventoryItem item : productList){
            if(item.getProduct().getId() == id){
                item.setQuantity(item.getQuantity() + quantity);
            }
        }
    }

    /**
     * Searches for product in inventory.
     * @param id id of the product to be found
     * @return InventoryItem if product is found
     */
    public InventoryItem getProductById(int id){

        for(InventoryItem item : productList){
            if(item.getProduct().getId() == id){
                return item;
            }
        }
        return null;
    }

    /**
     * Check if a product is available in inventory.
     * @param id id of the product to be checked.
     * @return true if the product is present.
     */
    public  boolean checkIfProductIsAvailable(int id){
        for(InventoryItem item : productList){
            if(item.getProduct().getId() == id){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){

        ArrayList<String> header = new ArrayList<>();
        header.add("S. No");
        header.add("Product Id");
        header.add("Product Name");
        header.add("Quantity Available");
        header.add("Price");
        ArrayList<ArrayList<String>> content = new ArrayList<>();
        int no = 1;
        for(InventoryItem item : productList){
                content.add(new ArrayList<>(Arrays.asList(String.valueOf(no),
                        String.valueOf(item.getProduct().getId()),
                        item.getProduct().getName(),
                        String.valueOf(item.getQuantity()),
                        String.valueOf(item.getProduct().getPrice())
                        )));
                no++;
        }

        return Globals.printTable(header, content);
    }

}
