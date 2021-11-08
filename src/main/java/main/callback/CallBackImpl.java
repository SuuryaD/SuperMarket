package main.callback;

import sdk.employee.domain.Employee;
import sdk.inventory.controller.InventoryControllerImpl;

public class CallBackImpl implements Callback {

  private InventoryControllerImpl inventoryControllerImpl;

  public CallBackImpl(Employee emp) {
    inventoryControllerImpl = new InventoryControllerImpl(emp);
  }

  @Override
  public void callback() {
    System.out.println(inventoryControllerImpl.getAllProducts());
  }
}
