package sdk.inventory.service;

import sdk.inventory.domain.InventoryItem;
import sdk.util.ValidationException;

public interface InventoryService {

  boolean addNewProduct(int productId, String productName, Double productPrice, int quantity)
      throws ValidationException;

  boolean increaseStock(int productId, int quantity) ;

  boolean decreaseStock(int productId, int quantity);

  InventoryItem getInventoryItem(int productId) ;

  int getProductQuantity(int productId);

  String printAllItems();
}
