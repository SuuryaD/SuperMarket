package inventory;

import java.util.ArrayList;


public class Inventory {

    private static final ArrayList<InventoryItem> productList = new ArrayList<>();

    public static void add(Product product, int quantity){
        productList.add(new InventoryItem(product, quantity));
    }

    public static void reduceStock(int id, int quantity){

        for(InventoryItem item : productList){
            if(item.getProduct().getId() == id){
                item.setQuantity(item.getQuantity() - quantity);
            }
        }
    }

    public static void updateStock(int id, int quantity){
        for(InventoryItem item : productList){
            if(item.getProduct().getId() == id){
                item.setQuantity(item.getQuantity() + quantity);
            }
        }
    }

    public static InventoryItem getProductById(int id){

        for(InventoryItem item : productList){
            if(item.getProduct().getId() == id){
                return item;
            }
        }
        return null;
    }

    public static boolean checkIfProductIsAvailable(int id){
        for(InventoryItem item : productList){
            if(item.getProduct().getId() == id){
                return true;
            }
        }
        return false;
    }

}
