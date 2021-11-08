package sdk.bill.domain;

import sdk.inventory.domain.Product;
import sdk.util.Globals;
import sdk.util.ValidationException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/** Bill Class contains all the information related to a bill. Holds list of all items added. */
public class Bill {
  private static int NUMBER = 1;
  private final int id;
  private final long billingTime;
  private final List<BillItem> billItems;
  private final int employeeId;
  private final String employeeName;
  private final int customerId;
  private double amount;
  private boolean paid;

  /**
   * Initializes all the properties of the bill.
   *
   * @param employeeId of cashier billing.
   * @param employeeName Name of employee
   */
  public Bill(int employeeId, String employeeName, int customerId) {
    id = NUMBER;
    NUMBER++;
    billingTime = System.currentTimeMillis();
    billItems = new ArrayList<>();
    amount = 0.0;
    this.employeeId = employeeId;
    this.employeeName = employeeName;
    this.customerId = customerId;
    paid = false;
  }

  public void changePaid() {
    paid = true;
  }

  public long getBillingTime() {
    return billingTime;
  }

  public boolean isPaid() {
    return paid;
  }

  public int getCustomerId() {
    return customerId;
  }

  /** @return List of Bill items. */
  public List<BillItem> getBillItems() {
    return billItems;
  }

  public int getId() {
    return id;
  }

  public boolean checkIfItemIsPresent(int productId) {
    for (BillItem item : billItems) {
      if (item.getProduct().getId() == productId) return true;
    }
    return false;
  }

  public void setQuantity(int productId, int quantity) {
    for (BillItem item : billItems) {
      if (item.getProduct().getId() == productId) {
        item.setQuantity(quantity);
        setAmount();
      }
    }
  }

  private void setAmount() {
    amount = 0;
    for (BillItem item : billItems) {
      amount += item.getPrice();
    }
  }

  public Integer getQuantity(int productId){
    for (BillItem item : billItems) {
      if (item.getProduct().getId() == productId) {
        return item.getQuantity();
      }
    }
    return null;
  }

  public void addNewItem(Product product, int quantity) {
    billItems.add(new BillItem(product, quantity));
    setAmount();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    Date date = new Date(billingTime);
    DateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
    builder
        .append("Bill ID: ")
        .append(id)
        .append("\t Time: ")
        .append(sdf.format(date))
        .append("\n");
    builder
        .append("Employee Id: ")
        .append(employeeId)
        .append("\t Employee Name: ")
        .append(employeeName)
        .append("\n");

    ArrayList<String> headers = new ArrayList<>();
    headers.add("Item No");
    headers.add("Name");
    headers.add("Quantity");
    headers.add("MRP");
    headers.add("Price");

    ArrayList<ArrayList<String>> content = new ArrayList<>();
    int no = 0;
    for (BillItem billItem : billItems) {
      no++;
      content.add(
          new ArrayList<>(
              Arrays.asList(
                  String.valueOf(no),
                  billItem.getProduct().getName(),
                  String.valueOf(billItem.getQuantity()),
                  String.valueOf(billItem.getProduct().getPrice()),
                  String.valueOf(String.format("%.2f", billItem.getPrice())))));
    }
    builder.append(Globals.printTable(headers, content)).append("\n");
    //    builder.append("Total Amount: ").append(Math.round(amount * 100.0) / 100.0).append("\n");
    builder.append("Total Amount: ").append(String.format("%.2f", amount)).append("\n");

    return builder.toString();
  }

  public double getAmount() {
    return amount;
  }

  public void removeBillItem(BillItem item) {
    billItems.removeIf(BillItem -> BillItem == item);
    setAmount();
  }

  public int getBillSize() {
    return billItems.size();
  }

  public int getEmployeeId() {
    return employeeId;
  }

  public String getEmployeeName() {
    return employeeName;
  }
}
