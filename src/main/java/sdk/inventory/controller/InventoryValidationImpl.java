package sdk.inventory.controller;

import sdk.bill.domain.Bill;
import sdk.inventory.service.InventoryService;
import sdk.util.Factory;
import sdk.util.ValidationException;

public class InventoryValidationImpl implements InventoryValidation {

  private final InventoryService inventoryService;

  public InventoryValidationImpl() {
    inventoryService = Factory.createInventoryService();
  }

  public void validateProductId(int productId) throws ValidationException {
    if (inventoryService.getInventoryItem(productId) == null) {
      throw new ValidationException("Enter a valid product Id");
    }
  }

  public void validateProductQuantityToBeAdded(int productId, int quantity)
          throws ValidationException {
    if (quantity <= 0) throw new ValidationException("Enter a valid quantity");
    if (inventoryService.getProductQuantity(productId) < quantity)
      throw new ValidationException(
              "The quantity available is " + inventoryService.getProductQuantity(productId));
  }

  @Override
  public void validateQuantity(int quantity) throws ValidationException {
    if (quantity <= 0) throw new ValidationException("Enter a valid quantity");
  }

  @Override
  public void validateProductName(String productName) throws ValidationException {
    if (!productName.matches("^\\S+(?: \\S+)*$"))
      throw new ValidationException("Enter a valid Product Name");
  }

  @Override
  public void validateProductPrice(Double price) throws ValidationException {
    if (price <= 0.0) throw new ValidationException("Enter a valid Product price");
  }
}
