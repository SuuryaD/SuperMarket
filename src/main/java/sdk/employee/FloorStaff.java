package sdk.employee;

public class FloorStaff extends Employee{

    public FloorStaff(String name, String username, String password) {
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
