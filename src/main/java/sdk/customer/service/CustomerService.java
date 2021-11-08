package sdk.customer.service;

import sdk.bill.BillDetails;
import sdk.customer.Customer;

import java.util.List;

/**
 * Service layer for the customer domain
 */
public interface CustomerService {

  boolean checkCustomerId(int customerId);

  int addCustomer(String customerName, String customerAddress);

  List<Customer> getAllCustomers();

  List<BillDetails> getBillsOfCustomer(int customerId);

  int getNoOfBillsOfCustomer(int customerId);

  Double getTotalPurchaseOfCustomer(int customerId);
}
