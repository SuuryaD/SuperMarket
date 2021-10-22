package sdk.inventory;

import sdk.util.Globals;
import sdk.util.ValidationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Singleton Class to hold a list of all the items in the inventory */
public class InventoryService {

  private InventoryRepository inventoryRepository;

  public InventoryService(){
    inventoryRepository = InventoryRepository.getInstance();
  }


  public boolean add(int productId, String productName, Double productPrice, int quantity) throws ValidationException {

    if(isProductAvailable(productId)){
      throw new ValidationException("Product Id is already taken");
    }

    return inventoryRepository.addProduct(productId, productName, productPrice, quantity);
  }



  /**
   * updates the quantityToBeIncreased of the product
   *
   * @param productId productId of the product
   * @param quantityToBeIncreased the quantityToBeIncreased to be added.
   */
  public boolean increaseStock(int productId, int quantityToBeIncreased) throws ValidationException {
    if(!isProductAvailable(productId)){
      throw new ValidationException("The product Id is invalid");
    }
    int finalQuantity = getQuantity(productId) + quantityToBeIncreased;
    return inventoryRepository.updateStock(productId,finalQuantity);
  }

  public boolean decreaseStock(int id, int quantity){
    if(!isProductAvailable(id)){
      return false;
    }
    int finalQuantity = getQuantity(id) - quantity;
    return inventoryRepository.updateStock(id,finalQuantity);
  }

  public int getQuantity(int productId){
    return inventoryRepository.getQuantity(productId);
  }

  public List<InventoryItem> getAllItems(){
    return inventoryRepository.getInventoryItems();
  }

  /**
   * Check if a product is available in inventory.
   *
   * @param id id of the product to be checked.
   * @return true if the product is present.
   */
  public boolean isProductAvailable(int id) {
    InventoryItem item = inventoryRepository.getProductById(id);
    return item != null;
  }

  public int getProductQuantity(int id) throws ValidationException {
    InventoryItem item = inventoryRepository.getProductById(id);
    if(item == null)
      throw new ValidationException("Product id is not valid.");

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
