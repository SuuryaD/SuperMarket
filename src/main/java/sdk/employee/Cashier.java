package sdk.employee;

public class Cashier extends Employee{

    public Cashier(String name, String username, String password) {
        super(name, username, password);
    }

    @Override
    public void redirect(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String getRole() {
        return this.getClass().getSimpleName();
    }


}
