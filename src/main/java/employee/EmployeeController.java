package employee;

import inventory.ValidationException;

public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(){
        employeeService = new EmployeeService();
    }
    public boolean addEmployee(String name, String username, String pass, int admin) throws ValidationException {

        if (!name.matches("^[a-zA-Z]+( [a-zA-Z]+)?$"))
            throw new ValidationException("Enter a valid name");

        if (!username.matches("^[a-zA-Z0-9._+-/!@#$%^&*]+$"))
            throw new ValidationException("Enter a valid username");

        if (!pass.matches("(^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])).{5,20}$"))
            throw new ValidationException("The password must contain atleast on number, capital letter, special charecter");

        return employeeService.addEmployee(name, username, pass, admin);
    }

    public boolean removeEmployee(int id) throws ValidationException {
        if(id < 0)
            throw new ValidationException("Enter a valid id");

        return employeeService.removeEmployee(id);

    }

    public int authenticate(String username, String pass){
        return employeeService.authenticate(username, pass);
    }

    public String displayAllEmployees() {
        return employeeService.displayAllEmployees();
    }
}
