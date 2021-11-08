package sdk.inventory.controller;

import sdk.util.ValidationException;

public interface InventoryValidation {
  void validateProductId(int productId) throws ValidationException;

  void validateQuantity(int quantity) throws ValidationException;

  void validateProductName(String productName) throws ValidationException;

  void validateProductPrice(Double price) throws ValidationException;

  void validateProductQuantityToBeAdded(int productId, int quantity) throws ValidationException;
}
