package main;

import util.Globals;

import java.io.IOException;

/** Class to perform different types of payment. */
public class Payment {

  public boolean menu() {

    try {
      System.out.println("Payment options");
      System.out.println("1. Card");
      System.out.println("2. UPI");
      System.out.println("4. Cancel Payment");
      String option = Globals.input.readLine();
      switch (option) {
        case "1":
          return payWithCard();
        case "2":
          return payWithUpi();
        case "4":
          return false;
        default:
          System.out.println("Enter a valid option");
      }

    } catch (IOException e) {
      System.out.println("Something went wrong try again.");
      e.printStackTrace();
    }
    return false;
  }

  /**
   * Gets the card details from user
   *
   * @return true if the card details are valid
   * @throws IOException throws exception
   */
  private boolean payWithCard() throws IOException {

    do {
      System.out.println("Enter Card Number : (1111-1111-1111-1111)");
      String cardNumber = Globals.input.readLine();
      if (cardNumber.equals("0")) return false;
      if (cardNumber.matches("(^[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}$)")) break;
      System.out.println("Enter a valid card Number");
    } while (true);

    do {
      System.out.println("Enter Name on Card");
      String cardName = Globals.input.readLine();
      if (cardName.equals("0")) return false;
      if (cardName.matches("^[A-Za-z\\s]+[A-Za-z\\s]*$")) break;
      System.out.println("Enter a valid name");
    } while (true);

    do {
      System.out.println("Enter the expiry date (MM-YY)");
      String cardExpiry = Globals.input.readLine();
      if (cardExpiry.equals("0")) return false;
      if (cardExpiry.matches("(^(0[1-9]|1[012])-[0-9]{2})")) break;
      System.out.println("Enter a valid expiry date");
    } while (true);

    return true;
  }

  private boolean payWithUpi() throws IOException {

    do {
      System.out.println("Enter your upi id");
      String upi = Globals.input.readLine();
      if (upi.equals("0")) return false;

      if (upi.matches("^([_a-zA-Z0-9-]+(\\\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+)$")) return true;

      System.out.println("Enter a valid upi id");
    } while (true);
  }
}
