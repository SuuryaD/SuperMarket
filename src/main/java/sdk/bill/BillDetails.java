package sdk.bill;

import sdk.util.Globals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BillDetails {

  private final int id;
  private final long billingTime;
  private final List<BillItemDetails> billItems;
  private final int employeeId;
  private final String employeeName;
  private final int customerId;
  private double amount;
  private boolean paid;

  public BillDetails(
      int id,
      long billingTime,
      List<BillItemDetails> billItems,
      int employeeId,
      String employeeName,
      int customerId,
      double amount,
      boolean paid) {
    this.id = id;
    this.billingTime = billingTime;
    this.billItems = billItems;
    this.employeeId = employeeId;
    this.employeeName = employeeName;
    this.customerId = customerId;
    this.amount = amount;
    this.paid = paid;
  }

  public int getId() {
    return id;
  }

  public long getBillingTime() {
    return billingTime;
  }

  public int getEmployeeId() {
    return employeeId;
  }

  public String getEmployeeName() {
    return employeeName;
  }

  public int getCustomerId() {
    return customerId;
  }

  public double getAmount() {
    return amount;
  }

  public boolean isPaid() {
    return paid;
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
    for (BillItemDetails billItem : billItems) {
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
    builder.append("Total Amount: ").append(String.format("%.2f", amount)).append("\n");

    return builder.toString();
  }
}
