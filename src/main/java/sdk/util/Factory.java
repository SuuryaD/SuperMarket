package sdk.util;

import sdk.bill.controller.BillController;
import sdk.bill.controller.BillControllerImpl;
import sdk.bill.controller.BillValidation;
import sdk.bill.controller.BillValidationImpl;
import sdk.bill.repository.BillRepository;
import sdk.bill.repository.BillRepositoryImpl;
import sdk.bill.service.BillService;
import sdk.bill.service.BillServiceImpl;
import sdk.customer.controller.CustomerController;
import sdk.customer.controller.CustomerControllerImpl;
import sdk.customer.controller.CustomerValidation;
import sdk.customer.controller.CustomerValidationImpl;
import sdk.customer.repository.CustomerRepository;
import sdk.customer.repository.CustomerRepositoryImpl;
import sdk.customer.service.CustomerService;
import sdk.customer.service.CustomerServiceImpl;
import sdk.employee.controller.EmployeeController;
import sdk.employee.controller.EmployeeControllerImpl;
import sdk.employee.controller.EmployeeValidation;
import sdk.employee.controller.EmployeeValidationImpl;
import sdk.employee.domain.Employee;
import sdk.employee.repository.EmployeeLoginRepository;
import sdk.employee.repository.EmployeeLoginRepositoryImpl;
import sdk.employee.repository.EmployeeRepository;
import sdk.employee.repository.EmployeeRepositoryImpl;
import sdk.employee.service.EmployeeService;
import sdk.employee.service.EmployeeServiceImpl;
import sdk.inventory.controller.InventoryController;
import sdk.inventory.controller.InventoryControllerImpl;
import sdk.inventory.controller.InventoryValidation;
import sdk.inventory.controller.InventoryValidationImpl;
import sdk.inventory.repository.InventoryRepository;
import sdk.inventory.repository.InventoryRepositoryImpl;
import sdk.inventory.service.InventoryService;
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

  public static BillValidation createBillValidation() {
    return new BillValidationImpl();
  }

  public static EmployeeLoginRepository createEmployeeLoginRepository() {
    return EmployeeLoginRepositoryImpl.getInstance();
  }

  public static InventoryValidation createInventoryValidation() {
    return new InventoryValidationImpl();
  }

  public static CustomerController createCustomerController(Employee emp) {
    return new CustomerControllerImpl(emp);
  }

  public static EmployeeController createEmployeeController(Employee emp) {
    return new EmployeeControllerImpl(emp);
  }

  public static InventoryController createInventoryController(Employee emp) {
    return new InventoryControllerImpl(emp);
  }

  public static EmployeeValidation createEmployeeValidation() {
    return new EmployeeValidationImpl();
  }

  public static CustomerValidation createCustomerValidation() {
    return new CustomerValidationImpl();
  }
}
