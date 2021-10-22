package sdk.employee;

import sdk.util.Globals;
import sdk.util.ValidationException;

public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(){
        employeeService = new EmployeeService();
    }
    public boolean addEmployee(String name, String username, String pass, int type) throws ValidationException {

        if(type > 5 || type < 0)
            throw new ValidationException("Enter a valid type of employee.");

        if (!name.matches("^[a-zA-Z]+( [a-zA-Z]+)?$"))
            throw new ValidationException("Enter a valid name");

        if (!username.matches("^[a-zA-Z0-9._+-/!@#$%^&*]+$"))
            throw new ValidationException("Enter a valid username");

        if (!pass.matches("(^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])).{5,20}$"))
            throw new ValidationException("The password must contain atleast on number, capital letter, special charecter");

        return employeeService.addEmployee(name, username, pass, type);
    }

    public boolean removeEmployee(int employeeId) throws ValidationException {
        if(employeeId < 0)
            throw new ValidationException("Enter a valid id");

        return employeeService.removeEmployee(employeeId);

    }

    public Employee authenticate(String username, String pass) throws IllegalArgumentException {
        return employeeService.authenticate(username, pass);
    }

    public String printAllEmployees() {
        return employeeService.displayAllEmployees();
    }

    public void logout(){
        Globals.currentEmployee = null;
    }
}
