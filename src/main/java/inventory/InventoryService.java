package inventory;

import util.Globals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Singleton Class to hold a list of all the items in the inventory */
public class InventoryService {

  private static InventoryService instance;
  private final ArrayList<InventoryItem> productList = new ArrayList<>();


  public static InventoryService getInstance() {
    if (instance == null) {
      instance = new InventoryService();
    }
    return instance;
  }

  private InventoryRepository inventoryRepository;

  public InventoryService(){
    inventoryRepository = new InventoryRepository();
  }

  /**
   * Adds a new Product to the inventory
   *
   * @param product Product to be added
   * @param quantity quantity of the product
   */
//  public void add(Product product, int quantity) {
//    productList.add(new InventoryItem(product, quantity));
//  }

  public boolean add(int id, String name, Double price, int quantity){

    if(isProductAvailable(id)){
      System.out.println("Product already present");
      return false;
    }

    return InventoryRepository.addProduct(id, name, price, quantity);

  }


  /**
   * reduces the quantity of a product.
   *
   * @param id id of the product
   * @param quantity quantity to be reduced.
   */
  public void reduceStock(int id, int quantity) {

    for (InventoryItem item : productList) {
      if (item.getProduct().getId() == id) {
        item.setQuantity(item.getQuantity() - quantity);
      }
    }
  }

  /**
   * updates the quantity of the product
   *
   * @param id id of the product
   * @param quantity the quantity to be added.
   */
  public boolean updateStock(int id, int quantity) {
    if(!isProductAvailable(id)){
      return false;
    }
    int finalQuantity = getQuantity(id) + quantity;
    return InventoryRepository.updateStock(id,finalQuantity);
  }

  public int getQuantity(int id){
    return InventoryRepository.getQuantity(id);
  }
  /**
   * Searches for product in inventory.
   *
   * @param id id of the product to be found
   * @return InventoryItem if product is found
   */
//  public InventoryItem getProductById(int id) {
//
//    for (InventoryItem item : productList) {
//      if (item.getProduct().getId() == id) {
//        return item;
//      }
//    }
//    return null;
//  }

  public List<InventoryItem> getAllItems(){
    return InventoryRepository.getAllProducts();
  }
  /**
   * Check if a product is available in inventory.
   *
   * @param id id of the product to be checked.
   * @return true if the product is present.
   */
  public boolean isProductAvailable(int id) {
    InventoryItem item = InventoryRepository.getProductById(id);
    return item != null;
  }

  @Override
  public String toString() {

    ArrayList<String> header = new ArrayList<>();
    header.add("S. No");
    header.add("Product Id");
    header.add("Product Name");
    header.add("Quantity Available");
    header.add("Price");
    ArrayList<ArrayList<String>> content = new ArrayList<>();
    int no = 1;
    for (InventoryItem item : InventoryRepository.getAllProducts()) {
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
