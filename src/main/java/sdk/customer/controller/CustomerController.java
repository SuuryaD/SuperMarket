package sdk.customer.controller;

import sdk.bill.BillDetails;
import sdk.customer.Customer;
import sdk.util.ValidationException;

import java.util.List;

/**
 * holds all functionality of customer
 */
public interface CustomerController {
  boolean checkCustomerId(int customerId) throws ValidationException;

  int addNewCustomer(String name, String address) throws ValidationException;

  List<BillDetails> getBillsOfCustomer(int customerId) throws ValidationException;

  List<Customer> getAllCustomers();

  Double getTotalPurchaseOfCustomer(int customerId) throws ValidationException;

  int getNoOfBillsOfCustomer(int customerId) throws ValidationException;
}
