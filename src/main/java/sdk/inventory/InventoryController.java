package sdk.inventory;

import sdk.util.ValidationException;

public class InventoryController {
  private final InventoryService inventoryService;

  public InventoryController() {
    this.inventoryService = new InventoryService();
  }

  public boolean addProduct(int productId, String productName, Double productPrice, int quantity)
      throws ValidationException {
    if (productId <= 0) throw new ValidationException("Invalid productId");
    if (productName.length() == 0) throw new ValidationException("Enter a valid productName");
    if (productPrice <= 0.0) throw new ValidationException("Invalid productPrice");
    if (quantity <= 0) throw new ValidationException("Invalid quantity");

    return inventoryService.addNewProduct(productId, productName, productPrice, quantity);
  }

  public int isProductAvailable(int productId) throws ValidationException {

    if (productId <= 0) throw new ValidationException("Enter a valid product productId");
    return inventoryService.getProductQuantity(productId);
  }

  public boolean increaseStock(int productId, int quantityToBeIncreased)
      throws ValidationException {
    if (productId <= 0) throw new ValidationException("Invalid productId");

    if (quantityToBeIncreased <= 0) throw new ValidationException("Invalid quantityToBeIncreased");

    return inventoryService.increaseStock(productId, quantityToBeIncreased);
  }

  //    public boolean reduceStock(int id, int quantity) throws ValidationException {
  //        if(id <= 0)
  //            throw new ValidationException("Invalid id");
  //
  //        if(quantity <= 0)
  //            throw new ValidationException("Invalid quantity");
  //
  //        return inventoryService.decreaseStock(id, quantity);
  //    }

  public String getAllProducts() {

    return inventoryService.printAllItems();
  }
}
