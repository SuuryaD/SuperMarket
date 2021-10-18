package inventory;

import util.Globals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryController {
    private InventoryService inventoryService;

    public InventoryController() {
        this.inventoryService = new InventoryService();
    }

    public boolean addProduct(int id, String name, Double price, int quantity) throws ValidationException {
       if(id < 0)
           throw new ValidationException("Ivalid id");
       if(name.length() == 0)
           throw new ValidationException("Enter a valid name");
       if(price <= 0.0)
           throw new ValidationException("Invalid price");
       if(quantity < 0)
           throw new ValidationException("Invalid quantity");

       return inventoryService.add(id, name, price, quantity);
    }

    public boolean isProductAvailable(int id){
        return inventoryService.isProductAvailable(id);
    }

    public boolean increaseStock(int id, int quantity) throws ValidationException {
        if(id < 0)
            throw new ValidationException("Ivalid id");

        if(quantity < 0)
            throw new ValidationException("Invalid quantity");

        return inventoryService.updateStock(id, quantity);
    }

    public String getAllProducts(){

        List<InventoryItem> ls = inventoryService.getAllItems();

        ArrayList<String> header = new ArrayList<>();
        header.add("S. No");
        header.add("Product Id");
        header.add("Product Name");
        header.add("Quantity Available");
        header.add("Price");
        ArrayList<ArrayList<String>> content = new ArrayList<>();
        int no = 1;
        for (InventoryItem item : ls) {
            content.add(
                    new ArrayList<>(
                            Arrays.asList(
                                    String.valueOf(no),
                                    String.valueOf(item.getProduct().getId()),
                                    item.getProduct().getName(),
                                    String.valueOf(item.getQuantity()),
                                    String.valueOf(item.getProduct().getPrice()))));
            no++;
        }

        return Globals.printTable(header, content);
    }

}