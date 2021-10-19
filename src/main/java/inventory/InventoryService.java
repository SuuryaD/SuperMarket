package inventory;

import util.Globals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Singleton Class to hold a list of all the items in the inventory */
public class InventoryService {

  private InventoryRepository inventoryRepository;

  public InventoryService(){
    inventoryRepository = InventoryRepository.getInstance();
  }


  public boolean add(int id, String name, Double price, int quantity){

    if(isProductAvailable(id)){
      System.out.println("Product already present");
      return false;
    }

    return inventoryRepository.addProduct(id, name, price, quantity);

  }



  /**
   * updates the quantity of the product
   *
   * @param id id of the product
   * @param quantity the quantity to be added.
   */
  public boolean increaseStock(int id, int quantity) {
    if(!isProductAvailable(id)){
      return false;
    }
    int finalQuantity = getQuantity(id) + quantity;
    return inventoryRepository.updateStock(id,finalQuantity);
  }

  public boolean decreaseStock(int id, int quantity){
    if(!isProductAvailable(id)){
      return false;
    }
    int finalQuantity = getQuantity(id) - quantity;
    return inventoryRepository.updateStock(id,finalQuantity);
  }

  public int getQuantity(int id){
    return inventoryRepository.getQuantity(id);
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

//  @Override
//  public String toString() {
//
//    ArrayList<String> header = new ArrayList<>();
//    header.add("S. No");
//    header.add("Product Id");
//    header.add("Product Name");
//    header.add("Quantity Available");
//    header.add("Price");
//    ArrayList<ArrayList<String>> content = new ArrayList<>();
//    int no = 1;
//    for (InventoryItem item : inventoryRepository.getInventoryItems()) {
//      content.add(
//          new ArrayList<>(
//              Arrays.asList(
//                  String.valueOf(no),
//                  String.valueOf(item.getProduct().getId()),
//                  item.getProduct().getName(),
//                  String.valueOf(item.getQuantity()),
//                  String.valueOf(item.getProduct().getPrice()))));
//      no++;
//    }
//
//    return Globals.printTable(header, content);
//  }
}
