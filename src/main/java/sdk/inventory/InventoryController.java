package sdk.inventory;

import sdk.util.Globals;
import sdk.util.ValidationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController() {
        this.inventoryService = new InventoryService();
    }

    public boolean addProduct(int productId, String productName, Double productPrice, int quantity) throws ValidationException {
       if(productId <= 0)
           throw new ValidationException("Invalid productId");
       if(productName.length() == 0)
           throw new ValidationException("Enter a valid productName");
       if(productPrice <= 0.0)
           throw new ValidationException("Invalid productPrice");
       if(quantity <= 0)
           throw new ValidationException("Invalid quantity");

       return inventoryService.add(productId, productName, productPrice, quantity);
    }

    public int isProductAvailable(int id) throws ValidationException {

        if(id < 0)
            throw new ValidationException("Enter a valid product id");
        return inventoryService.getProductQuantity(id);
    }

    public boolean increaseStock(int productId, int quantityToBeIncreased) throws ValidationException {
        if(productId <= 0)
            throw new ValidationException("Invalid productId");

        if(quantityToBeIncreased <= 0)
            throw new ValidationException("Invalid quantityToBeIncreased");

        return inventoryService.increaseStock(productId, quantityToBeIncreased);
    }

    public boolean reduceStock(int id, int quantity) throws ValidationException {
        if(id < 0)
            throw new ValidationException("Invalid id");

        if(quantity < 0)
            throw new ValidationException("Invalid quantity");

        return inventoryService.decreaseStock(id, quantity);
    }

    public String getAllProducts(){

        return inventoryService.printAllItems();
    }

}