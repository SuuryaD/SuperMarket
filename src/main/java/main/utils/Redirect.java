package main.utils;

import main.main.*;
import sdk.employee.Visitor;
import sdk.employee.domain.*;

/** Redirects Employees bases on the type of the object. */
public class Redirect implements Visitor {

  @Override
  public void visit(Manager manager) {
    new ManagerUI(manager).menu();
  }

  @Override
  public void visit(Biller biller) {
    new BillerUI(biller).menu();
  }

  @Override
  public void visit(Delivery delivery) {
    new DeliveryUI(delivery).menu();
  }

  @Override
  public void visit(Cashier cashier) {
    new CashierUI(cashier).menu();
  }

  @Override
  public void visit(FloorStaff floorStaff) {
    new FloorStaffUI(floorStaff).menu();
  }
}
