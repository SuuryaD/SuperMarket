package sdk.util;

import sdk.bill.controller.BillController;
import sdk.bill.controller.BillControllerImpl;
import sdk.bill.repository.BillRepositoryImpl;
import sdk.bill.service.BillServiceImpl;
import sdk.bill.repository.BillRepository;
import sdk.bill.service.BillService;
import sdk.customer.repository.CustomerRepositoryImpl;
import sdk.customer.service.CustomerServiceImpl;
import sdk.customer.repository.CustomerRepository;
import sdk.customer.service.CustomerService;
import sdk.employee.domain.Employee;
import sdk.employee.repository.EmployeeRepositoryImpl;
import sdk.employee.service.EmployeeServiceImpl;
import sdk.employee.repository.EmployeeRepository;
import sdk.employee.service.EmployeeService;
import sdk.inventory.repository.InventoryRepository;
import sdk.inventory.service.InventoryService;
import sdk.inventory.repository.InventoryRepositoryImpl;
import sdk.inventory.service.InventoryServiceImpl;
import sdk.payment.CreditCardPayment;
import sdk.payment.Payment;
import sdk.payment.UpiPaymentImpl;

public class Factory {

  public static CustomerRepository createCustomerRepository() {
    return CustomerRepositoryImpl.getInstance();
  }

  public static CustomerService createCustomerService() {
    return new CustomerServiceImpl();
  }

  public static InventoryService createInventoryService() {
    return new InventoryServiceImpl();
  }

  public static InventoryRepository createInventoryRepository() {
    return InventoryRepositoryImpl.getInstance();
  }

  public static BillRepository createBillRepository() {
    return BillRepositoryImpl.getInstance();
  }

  public static BillService createBillService() {
    return new BillServiceImpl();
  }

  public static EmployeeRepository createEmployeeRepository() {
    return EmployeeRepositoryImpl.getInstance();
  }

  public static EmployeeService createEmployeeService() {
    return new EmployeeServiceImpl();
  }

  public static Payment createCreditCardPayment(
      String cardNumber, String cardName, String cardExpiry, int billId) {
    return new CreditCardPayment(cardNumber, cardName, cardExpiry, billId);
  }

  public static Payment createUpiPayment(String upiId, int billId) {
    return new UpiPaymentImpl(upiId, billId);
  }

  public static BillController createBillController(Employee emp) {
    return new BillControllerImpl(emp);
  }
}
