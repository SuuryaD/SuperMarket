package sdk.inventory.domain;

/** class contains details of an item in inventory. */
public class InventoryItem {

  private Product product;
  private int quantity;

  public InventoryItem(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  public Product getProduct() {
    return product;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
