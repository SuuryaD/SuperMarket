package sdk.inventory.service;

import sdk.inventory.domain.InventoryItem;
import sdk.inventory.domain.Product;
import sdk.util.ValidationException;

public interface InventoryService {

  boolean addNewProduct(int productId, String productName, Double productPrice, int quantity)
      throws ValidationException;

  boolean increaseStock(int productId, int quantity) throws ValidationException;

  boolean decreaseStock(int productId, int quantity) throws ValidationException;

  InventoryItem getInventoryItem(int productId) throws ValidationException;

  int getProductQuantity(int productId) throws ValidationException;

  String printAllItems();
}
