package sdk.employee;

import java.util.List;

public class Biller extends Employee{

    public Biller(String name, String username, String password) {
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
