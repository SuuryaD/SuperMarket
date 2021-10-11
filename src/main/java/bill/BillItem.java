package bill;

import inventory.Product;

/**
 * Contains the values of each bill item.
 */
public class BillItem  {

    private final Product product;
    private int quantity;
    private double price;

    public BillItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.price = quantity * product.getPrice();
        price = Math.round(price * 100.0)/100.0;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        price = quantity * product.getPrice();
        price = Math.round(price * 100.0)/100.0;
        return price;
    }

}
