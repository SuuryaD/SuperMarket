package main;

import sdk.employee.*;

public class Redirect implements Visitor {

    @Override
    public void visit(Manager manager) {
        new ManagerUI().menu();
    }

    @Override
    public void visit(Biller biller) {
        new BillerUI().menu();
    }

    @Override
    public void visit(Delivery delivery) {
        new DeliveryUI().menu();
    }

    @Override
    public void visit(Cashier cashier){
        new CashierUI().menu();
    }

    @Override
    public void visit(FloorStaff floorStaff){
        new FloorStaffUI().menu();
    }
}