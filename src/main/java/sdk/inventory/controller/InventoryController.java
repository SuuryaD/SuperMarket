package sdk.inventory.controller;

import sdk.util.ValidationException;

public interface InventoryController {
  boolean addProduct(int productId, String productName, Double productPrice, int quantity)
      throws ValidationException;

  int isProductAvailable(int productId) throws ValidationException;

  boolean increaseStock(int productId, int quantityToBeIncreased) throws ValidationException;

  String getAllProducts();

  String getAllProducts(boolean exec) throws RuntimeException;
}
