package sdk.inventory.controller;

import sdk.employee.domain.Employee;
import sdk.inventory.service.InventoryService;
import sdk.util.Factory;
import sdk.util.ValidationException;

public class InventoryController {
  private final InventoryService inventoryService;
  private final InventoryValidation inventoryValidation;
  private final Employee emp;

  public InventoryController(Employee emp) {
    this.emp = emp;
    this.inventoryService = Factory.createInventoryService();
    inventoryValidation = new InventoryValidation();
  }

  public boolean addProduct(int productId, String productName, Double productPrice, int quantity)
      throws ValidationException {

    inventoryValidation.validateProductId(productId);
    inventoryValidation.validateProductName(productName);
    inventoryValidation.validateProductPrice(productPrice);
    inventoryValidation.validateQuantity(quantity);

    return inventoryService.addNewProduct(productId, productName, productPrice, quantity);
  }

  public int isProductAvailable(int productId) throws ValidationException {
    inventoryValidation.validateProductId(productId);

    return inventoryService.getProductQuantity(productId);
  }

  public boolean increaseStock(int productId, int quantityToBeIncreased)
      throws ValidationException {

    inventoryValidation.validateProductId(productId);
    inventoryValidation.validateQuantity(quantityToBeIncreased);
    return inventoryService.increaseStock(productId, quantityToBeIncreased);
  }

  public String getAllProducts() {
    return inventoryService.printAllItems();
  }
}
