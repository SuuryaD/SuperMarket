package main;

import sdk.bill.BillController;
import sdk.payment.PaymentController;
import sdk.util.ValidationException;

import java.io.IOException;

public class CashierUI {

  private final PaymentController paymentController;

  public CashierUI() {
    this.paymentController = new PaymentController();
  }

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

  private void cancelBill() throws IOException {
    System.out.println("Enter the bill Id");
    String billId = Globals.input.readLine();
    if (billId.equals("0")) return;
    try {
      new BillController().cancelBill(Integer.parseInt(billId));
    } catch (ValidationException e) {
      System.out.println(e.getMessage());
    }
  }

  private void makePayment() throws IOException {

    do {
      System.out.println("Enter the bill ID");
      String id = Globals.input.readLine();
      if (id.equals("0")) return;
      int billId;
      try {
        billId = Integer.parseInt(id);
        System.out.println(new BillController().printBill(billId));
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

  private void makePaymentWithUpi(int billId) throws IOException {
    do {
      System.out.println("Enter the upi id (0 to exit)");
      String upiId = Globals.input.readLine();
      if (upiId.equals("0")) return;
      try {
        if (paymentController.payWithUpi(upiId, billId)) {
          System.out.println("Payment Successful. Thank you");
          return;
        }
        System.out.println("Payment Failed. Try again");
      } catch (ValidationException e) {
        System.out.println(e.getMessage());
      }
    } while (true);
  }

  private void makePaymentWithCard(int billId) throws IOException {
    do {
      System.out.println("Enter the card Number (1111-1111-1111-1111): ");
      String cardNumber = Globals.input.readLine();
      if(cardNumber.equals("0"))
        return;
      System.out.println("Enter the Name on card");
      String name = Globals.input.readLine();
      if(name.equals("0"))
        return;
      System.out.println("Enter the expiry date (MM-YY):");
      String date = Globals.input.readLine();
      if(date.equals("0"))
        return;
      try {
        if (paymentController.payWitchCard(cardNumber, name, date, billId)) {
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
