package main.main;

import main.utils.Globals;
import sdk.bill.controller.BillController;
import sdk.employee.domain.Employee;
import sdk.payment.Payment;
import sdk.payment.PaymentController;
import sdk.util.Factory;
import sdk.util.ValidationException;

import java.io.IOException;

/** UI of the Cashier Employee */
public class CashierUI {

  private final PaymentController paymentController;
  private final BillController billController;
  private Payment payment;
  private final Employee emp;

  public CashierUI(Employee emp) {
    this.emp = emp;
    this.paymentController = new PaymentController();
    billController = Factory.createBillController(emp);
  }

  /** Displays the menu for the employee and redirects accordingly. */
  public void menu() {
    do {
      System.out.println("Menu");
      System.out.println("0. Logout");
      System.out.println("1. Payment");
      System.out.println("2. Cancel Bill");
      try {
        String choice = Globals.input.readLine();
        switch (choice) {
          case "0":
            return;
          case "1":
            makePayment();
            break;
          case "2":
            cancelBill();
            break;
          default:
            System.out.println("Enter a valid input");
        }

      } catch (IOException e) {
        System.out.println("Enter a valid input");
      }
    } while (true);
  }

  /**
   * Cancels the bill
   *
   * @throws IOException
   */
  private void cancelBill() throws IOException {
    System.out.println("Enter the bill Id");
    String billId = Globals.input.readLine();
    if (billId.equals("0")) return;
    try {
      billController.cancelBill(Integer.parseInt(billId));
    } catch (ValidationException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Displays different options for payment
   *
   * @throws IOException
   */
  private void makePayment() throws IOException {

    do {
      System.out.println("Enter the bill ID");
      String id = Globals.input.readLine();
      if (id.equals("0")) return;
      int billId;
      try {
        billId = Integer.parseInt(id);
        if (billController.isPaid(billId)) {
          System.out.println("Bill is already paid");
          return;
        }
        System.out.println(billController.getBill(billId));
      } catch (NumberFormatException e) {
        System.out.println("Enter a valid number");
        return;
      } catch (ValidationException e) {
        System.out.println(e.getMessage());
        return;
      }
      System.out.println("Enter the payment mode");
      System.out.println("1. Card");
      System.out.println("2. UPI");
      String choice = Globals.input.readLine();
      switch (choice) {
        case "0":
          return;
        case "1":
          makePaymentWithCard(billId);
          return;
        case "2":
          makePaymentWithUpi(billId);
          return;
        default:
          System.out.println("Enter a valid input");
      }
    } while (true);
  }

  /**
   * Does the payment with upi id.
   *
   * @param billId id of the paying bill.
   * @throws IOException
   */
  private void makePaymentWithUpi(int billId) throws IOException {

    do {
      System.out.println("Enter the upi id (0 to exit)");
      String upiId = Globals.input.readLine();
      if (upiId.equals("0")) return;
      try {
        payment = Factory.createUpiPayment(upiId, billId);
        if (payment.pay()) {
          System.out.println("Payment Successful. Thank you");
          return;
        }
        System.out.println("Payment Failed. Try again");
      } catch (ValidationException e) {
        System.out.println(e.getMessage());
      }
    } while (true);
  }

  /**
   * Does the payment with card.
   *
   * @param billId id of the paying bill.
   * @throws IOException
   */
  private void makePaymentWithCard(int billId) throws IOException {
    do {
      System.out.println("Enter the card Number (1111-1111-1111-1111): ");
      String cardNumber = Globals.input.readLine();
      if (cardNumber.equals("0")) return;
      System.out.println("Enter the Name on card");
      String name = Globals.input.readLine();
      if (name.equals("0")) return;
      System.out.println("Enter the expiry date (MM-YY):");
      String date = Globals.input.readLine();
      if (date.equals("0")) return;
      try {
        //        if (paymentController.payWitchCard(cardNumber, name, date, billId)) {
        //          System.out.println("Payment Successful. Thank You!");
        //          return;
        //        }
        payment = Factory.createCreditCardPayment(cardNumber, name, date, billId);
        if (payment.pay()) {
          System.out.println("Payment Successful. Thank You!");
          return;
        }
        System.out.println("Payment Failed. Try again");
      } catch (ValidationException e) {
        System.out.println(e.getMessage());
      }
    } while (true);
  }
}
