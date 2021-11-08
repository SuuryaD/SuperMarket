package sdk.inventory.controller;

import sdk.employee.domain.Employee;
import sdk.inventory.service.InventoryService;
import sdk.util.Factory;
import sdk.util.ValidationException;

public class InventoryControllerImpl implements InventoryController {
  private final InventoryService inventoryService;
  private final InventoryValidation inventoryValidation;
  private final Employee emp;

  public InventoryControllerImpl(Employee emp) {
    this.emp = emp;
    this.inventoryService = Factory.createInventoryService();
    inventoryValidation = Factory.createInventoryValidation();
  }

  @Override
  public boolean addProduct(int productId, String productName, Double productPrice, int quantity)
      throws ValidationException {

    inventoryValidation.validateProductId(productId);
    inventoryValidation.validateProductName(productName);
    inventoryValidation.validateProductPrice(productPrice);
    inventoryValidation.validateQuantity(quantity);

    return inventoryService.addNewProduct(productId, productName, productPrice, quantity);
  }

  @Override
  public int isProductAvailable(int productId) throws ValidationException {
    inventoryValidation.validateProductId(productId);
    return inventoryService.getProductQuantity(productId);
  }

  @Override
  public boolean increaseStock(int productId, int quantityToBeIncreased)
      throws ValidationException {

    inventoryValidation.validateProductId(productId);
    inventoryValidation.validateQuantity(quantityToBeIncreased);
    return inventoryService.increaseStock(productId, quantityToBeIncreased);
  }

  @Override
  public String getAllProducts() {
    return inventoryService.printAllItems();
  }

  public String getAllProducts(boolean exec) throws RuntimeException {
    if (exec) throw new RuntimeException("Fetching failed. Try again");
    return inventoryService.printAllItems();
  }

}
