package sdk.bill.domain;

import sdk.inventory.domain.Product;

/** Contains the attributes of bill item. */
public class BillItem {

  private final Product product;
  private int quantity;
  private double price;

  public BillItem(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
    this.price = quantity * product.getPrice();
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

  public double getPrice() {
    price = quantity * product.getPrice();
    return price;
  }

}
