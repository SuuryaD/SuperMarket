package sdk.customer;

import sdk.bill.Bill;

import java.util.ArrayList;
import java.util.List;

/** Contains all the information of a customer */
public class Customer {

  private static int NUMBER = 1;
  private final int id;
  private final String name;
  private final String address;

  private final List<Bill> bills;
  private double totalPurchase;

  public String getAddress() {
    return address;
  }

  public Customer(String name, String address) {
    id = NUMBER;
    NUMBER++;
    this.name = name;
    this.address = address;
    bills = new ArrayList<>();
    totalPurchase = 0.0;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<Bill> getBills() {
    return bills;
  }

  public double getTotalPurchase() {
    return Math.round(totalPurchase * 100.0) / 100.0;
  }

  public void addBill(Bill bill) {
    bills.add(bill);
    totalPurchase += bill.getAmount();
  }
}
