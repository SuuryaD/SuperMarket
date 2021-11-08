package sdk.inventory.service;

import sdk.inventory.domain.InventoryItem;
import sdk.inventory.repository.InventoryRepository;
import sdk.util.Factory;
import sdk.util.Globals;


import java.util.ArrayList;
import java.util.Arrays;

public class InventoryServiceImpl implements InventoryService {

  private final InventoryRepository inventoryRepository;

  public InventoryServiceImpl() {
    inventoryRepository = Factory.createInventoryRepository();
  }

  @Override
  public boolean addNewProduct(int productId, String productName, Double productPrice, int quantity)
       {
    return inventoryRepository.addProduct(productId, productName, productPrice, quantity);
  }

  @Override
  public boolean increaseStock(int productId, int quantity)  {

    int finalQuantity = getProductQuantity(productId) + quantity;
    return inventoryRepository.updateStock(productId, finalQuantity);
  }

  @Override
  public boolean decreaseStock(int productId, int quantity) {

    int finalQuantity = getProductQuantity(productId) - quantity;
    return inventoryRepository.updateStock(productId, finalQuantity);
  }

  public InventoryItem getInventoryItem(int productId) {
    return inventoryRepository.getProductById(productId);
  }



  @Override
  public int getProductQuantity(int productId) {
    InventoryItem item = inventoryRepository.getProductById(productId);
    return item.getQuantity();
  }

  @Override
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
