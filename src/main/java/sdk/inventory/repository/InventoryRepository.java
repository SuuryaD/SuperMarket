package sdk.inventory.repository;

import sdk.inventory.domain.InventoryItem;

import java.util.List;

public interface InventoryRepository {

  boolean addProduct(int productId, String productName, Double productPrice, int quantity);

  boolean updateStock(int productId, int quantity);

  List<InventoryItem> getInventoryItems();

  InventoryItem getProductById(int productId);
}
