package sdk.inventory;

import sdk.util.Globals;
import sdk.util.ValidationException;

import java.util.ArrayList;
import java.util.Arrays;

/** Singleton Class to hold a list of all the items in the inventory */
public class InventoryService {

  private final InventoryRepository inventoryRepository;

  public InventoryService(){
    inventoryRepository = InventoryRepository.getInstance();
  }


  public boolean addNewProduct(int productId, String productName, Double productPrice, int quantity) throws ValidationException {

    if(isProductAvailable(productId)){
      throw new ValidationException("Product Id is already taken");
    }
    return inventoryRepository.addProduct(productId, productName, productPrice, quantity);
  }



  /**
   * updates the quantity of the product
   *
   * @param productId productId of the product
   * @param quantity the quantity to be added.
   */
  public boolean increaseStock(int productId, int quantity) throws ValidationException {
    if(!isProductAvailable(productId)){
      throw new ValidationException("The product Id is invalid");
    }
    int finalQuantity = getProductQuantity(productId) + quantity;
    return inventoryRepository.updateStock(productId,finalQuantity);
  }

  public boolean decreaseStock(int productId, int quantity) throws ValidationException {
    if(!isProductAvailable(productId)){
      throw new ValidationException("The product Id is invalid");
    }
    int finalQuantity = getProductQuantity(productId) - quantity;
    return inventoryRepository.updateStock(productId,finalQuantity);
  }

  public Product getProduct(int productId) throws ValidationException {
    InventoryItem item = inventoryRepository.getProductById(productId);
    if(item == null)
      throw new ValidationException("Invalid product id");
    return item.getProduct();
  }

  /**
   * Check if a product is available in inventory.
   *
   * @param productId productId of the product to be checked.
   * @return true if the product is present.
   */
  public boolean isProductAvailable(int productId) {
    InventoryItem item = inventoryRepository.getProductById(productId);
    return item != null;
  }

  public int getProductQuantity(int productId) throws ValidationException {
    InventoryItem item = inventoryRepository.getProductById(productId);
    if(item == null)
      throw new ValidationException("Product Id is not valid.");

    return item.getQuantity();
  }

  public String printAllItems() {
    ArrayList<String> header = new ArrayList<>();
    header.add("S. No");
    header.add("Product Id");
    header.add("Product Name");
    header.add("Quantity Available");
    header.add("Price");
    ArrayList<ArrayList<String>> content = new ArrayList<>();
    int no = 1;
    for (InventoryItem item : inventoryRepository.getInventoryItems()) {
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
