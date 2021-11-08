package sdk.bill;

import sdk.inventory.domain.Product;

public class BillItemDetails {

  private final Product product;
  private int quantity;
  private double price;

  public BillItemDetails(Product product, int quantity, double price) {
    this.product = product;
    this.quantity = quantity;
    this.price = price;
  }

  public Product getProduct() {
    return product;
  }

  public int getQuantity() {
    return quantity;
  }

  public double getPrice() {
    return price;
  }
}
