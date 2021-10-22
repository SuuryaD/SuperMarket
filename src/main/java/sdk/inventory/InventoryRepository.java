package sdk.inventory;

import java.util.ArrayList;
import java.util.List;

public class InventoryRepository {

    private final List<InventoryItem> inventoryItems;
    private static InventoryRepository instance;

    public static InventoryRepository getInstance(){
        if(instance == null)
            instance = new InventoryRepository();
        return instance;
    }

    public InventoryRepository(){
        this.inventoryItems = new ArrayList<>();
        initializeRepo();
    }

    public boolean addProduct(int productId, String productName, Double productPrice, int quantity){
        return inventoryItems.add(new InventoryItem(new Product(productId, productName, productPrice), quantity));
    }


    public boolean updateStock(int id, int quantity){
        for(InventoryItem item : inventoryItems){
            if(item.getProduct().getId() == id){
                item.setQuantity(quantity);
                return true;
            }
        }
        return false;
    }

    public int getQuantity(int productId){
        for(InventoryItem item : inventoryItems){
            if(item.getProduct().getId() == productId){
                return item.getQuantity();
            }
        }
        return -1;
    }

    public List<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }

    public InventoryItem getProductById(int id) {
        for(InventoryItem item : inventoryItems){
            if(item.getProduct().getId() == id){
                return item;
            }
        }
        return null;
    }

    private void initializeRepo(){
        inventoryItems.add(new InventoryItem(new Product(1, "toothpaste", 10.23), 25));
        inventoryItems.add(new InventoryItem(new Product(2, "soap", 5.00), 15));
        inventoryItems.add(new InventoryItem(new Product(3, "shampoo", 25.50), 30));
        inventoryItems.add(new InventoryItem(new Product(4, "keyboard", 1.25), 32));
        inventoryItems.add(new InventoryItem(new Product(5, "mouse", 100.00), 10));
    }
}
