package sdk.employee;

import sdk.util.Globals;
import sdk.util.ValidationException;

public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(){
        employeeService = new EmployeeService();
    }

    public boolean addEmployee(String employeeName, String username, String pass, int employeeType) throws ValidationException {

        if(employeeType > 5 || employeeType <= 0)
            throw new ValidationException("Enter a valid employeeType of employee.");

        if (!employeeName.matches("^[a-zA-Z]+( [a-zA-Z]+)?$"))
            throw new ValidationException("Enter a valid employeeName");

        if (!username.matches("^[a-zA-Z0-9._+-/!@#$%^&*]+$"))
            throw new ValidationException("Enter a valid username");

        if (!pass.matches("(^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])).{5,20}$"))
            throw new ValidationException("The password must contain atleast one number, capital letter, special character");

        return employeeService.addEmployee(employeeName, username, pass, employeeType);
    }

    public boolean removeEmployee(int employeeId) throws ValidationException {

        if(employeeId <= 0)
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
