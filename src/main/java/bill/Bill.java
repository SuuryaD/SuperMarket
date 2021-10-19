package bill;

import inventory.InventoryService;
import inventory.InventoryItem;
import inventory.InventoryRepository;
import inventory.Product;
import util.Globals;

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
  private final long currentTime;
  private final List<BillItem> billItems;
  private final int employeeId;
  private final String employeeName;
  private final int customerId;
  private double amount;

  public int getCustomerId() {
    return customerId;
  }

  /**
   * Initializes all the properties of the bill.
   *
   * @param employeeId of cashier billing.
   * @param name name of employee
   */
  public Bill(int employeeId, String name, int customerId) {
    id = NUMBER;
    NUMBER++;
    currentTime = System.currentTimeMillis();
    billItems = new ArrayList<>();
    amount = 0.0;
    this.employeeId = employeeId;
    this.employeeName = name;
    this.customerId = customerId;
  }

  /** @return List of Bill items. */
  public List<BillItem> getBillItems() {
    return billItems;
  }

  public int getId() {
    return id;
  }

  public long getCurrentTime() {
    return currentTime;
  }

  public int getEmployeeId() {
    return employeeId;
  }

  public String getEmployeeName() {
    return employeeName;
  }

  public boolean checkIfItemIsPresent(int id){
    for(BillItem item : billItems){
      if(item.getProduct().getId() == id)
        return true;
    }
    return false;
  }

  public void setQuantity(int id, int quantity){
    for(BillItem item : billItems){
      if (item.getProduct().getId() == id) {
        amount += item.getPrice() * (item.getQuantity() - quantity);
        item.setQuantity(quantity);
      }

    }
  }

  public int getQuantity(int id){
    for(BillItem item : billItems){
      if(item.getProduct().getId() == id){
        return item.getQuantity();
      }
    }
    return 0;
  }

  public void addNewItem(Product product, int quantity){
    billItems.add(new BillItem(product, quantity));
    amount += product.getPrice() * quantity;
  }
  /**
   * updates the quantity of an item in bill if it already exists or adds a new item to the bill.
   *
   * @param item the item that has to be added
   * @param quantity the quantity of the item to be added
   */
//  public void addItem(InventoryItem item, int quantity) {
//
//    for (BillItem bItem : billItems) {
//      if (bItem.getProduct().getId() == item.getProduct().getId()) {
//
//        bItem.setQuantity(bItem.getQuantity() + quantity);
////        Inventory.getInstance().reduceStock(item.getProduct().getId(), quantity);
//        InventoryRepository.reduceStock(item.getProduct().getId(), quantity);
//        amount += item.getProduct().getPrice() * quantity;
//        return;
//      }
//    }
//
//    BillItem billItem = new BillItem(item.getProduct(), quantity);
//    billItems.add(billItem);
//    amount += billItem.getPrice();
////    Inventory.getInstance().reduceStock(item.getProduct().getId(), quantity);
//    InventoryRepository.reduceStock(item.getProduct().getId(), quantity);
//
//  }


  /**
   * Removes a product from the bill.
   *
   * @param itemNo the position of bill item in the bill list.
   * @param quantity the quantity to be removed.
   */
//  public void removeProduct(int itemNo, int quantity) {
//
//    if (billItems.get(itemNo - 1).getQuantity() == quantity) {
//
////      Inventory.getInstance()
////          .updateStock(
////              billItems.get(itemNo - 1).getProduct().getId(),
////              billItems.get(itemNo - 1).getQuantity());
//      InventoryRepository.updateStock(billItems.get(itemNo - 1).getProduct().getId(),
//              billItems.get(itemNo - 1).getQuantity());
//      amount -= billItems.get(itemNo - 1).getPrice();
//      billItems.remove(itemNo - 1);
//
//    } else {
//      InventoryService.getInstance().updateStock(billItems.get(itemNo - 1).getProduct().getId(), quantity);
//      billItems.get(itemNo - 1).setQuantity(billItems.get(itemNo - 1).getQuantity() - quantity);
//      amount -= billItems.get(itemNo - 1).getProduct().getPrice() * quantity;
//    }
//  }

  /** Cancels the bill and updates the inventory. */
//  public void cancelBill() {
//
//    for (BillItem item : billItems) {
//      InventoryService.getInstance().updateStock(item.getProduct().getId(), item.getQuantity());
//    }
//    billItems.clear();
//  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    Date date = new Date(currentTime);
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
                  String.valueOf(billItem.getPrice()))));
    }
    builder.append(Globals.printTable(headers, content)).append("\n");
    builder.append("Total Amount: ").append(Math.round(amount * 100.0) / 100.0).append("\n");

    return builder.toString();
  }

  public double getAmount() {
    return amount;
  }

  public void removeBillItem(BillItem item) {
    billItems.removeIf(BillItem -> BillItem == item);
  }

  public int getBillSize() {
    return billItems.size();
  }
}
