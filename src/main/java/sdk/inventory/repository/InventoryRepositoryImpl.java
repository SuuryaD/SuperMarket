package sdk.inventory.repository;

import sdk.inventory.domain.InventoryItem;
import sdk.inventory.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class InventoryRepositoryImpl implements InventoryRepository {

  private final List<InventoryItem> inventoryItems;
  private static InventoryRepositoryImpl instance;

  public static InventoryRepositoryImpl getInstance() {
    if (instance == null) instance = new InventoryRepositoryImpl();
    return instance;
  }

  public InventoryRepositoryImpl() {
    this.inventoryItems = new ArrayList<>();
    initializeRepo();
  }

  @Override
  public boolean addProduct(int productId, String productName, Double productPrice, int quantity) {
    return inventoryItems.add(
        new InventoryItem(new Product(productId, productName, productPrice), quantity));
  }

  @Override
  public boolean updateStock(int productId, int quantity) {
    for (InventoryItem item : inventoryItems) {
      if (item.getProduct().getId() == productId) {
        item.setQuantity(quantity);
        return true;
      }
    }
    return false;
  }

  @Override
  public List<InventoryItem> getInventoryItems() {
    return inventoryItems;
  }

  @Override
  public InventoryItem getProductById(int productId) {
    for (InventoryItem item : inventoryItems) {
      if (item.getProduct().getId() == productId) {
        return item;
      }
    }
    return null;
  }

  private void initializeRepo() {
    inventoryItems.add(new InventoryItem(new Product(1, "toothpaste", 10.23), 25));
    inventoryItems.add(new InventoryItem(new Product(2, "soap", 5.00), 15));
    inventoryItems.add(new InventoryItem(new Product(3, "shampoo", 25.50), 30));
    inventoryItems.add(new InventoryItem(new Product(4, "keyboard", 1.25), 32));
    inventoryItems.add(new InventoryItem(new Product(5, "mouse", 100.00), 10));
  }
}
