package sdk.employee.controller;

import sdk.employee.service.EmployeeService;
import sdk.util.Factory;
import sdk.util.ValidationException;

public class EmployeeValidationImpl implements EmployeeValidation {

    private final EmployeeService employeeService;

    public EmployeeValidationImpl() {
        employeeService = Factory.createEmployeeService();
    }


    @Override
    public void validateEmployeeName(String employeeName) throws ValidationException {

        if (!employeeName.matches("^[a-zA-Z]+( [a-zA-Z]+)?$"))
            throw new ValidationException("Enter a valid employeeName");
    }

    @Override
    public void validateEmployeeUsername(String username) throws ValidationException {

        if (!username.matches("^[a-zA-Z0-9._+-/!@#$%^&*]+$"))
            throw new ValidationException("Enter a valid username");

        if(employeeService.isEmployeeAvailable(username))
           throw new ValidationException("Username already taken. Try different username");
    }

    @Override
    public void validatePassword(String pass) throws ValidationException {

        if (!pass.matches("(^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])).{5,20}$"))
            throw new ValidationException(
                    "The password must contain atleast one number, capital letter, special character");
    }

    @Override
    public void validateEmployeeId(int employeeId) throws ValidationException {
        if(!employeeService.isEmployeeAvailable(employeeId))
            throw new ValidationException("Enter a valid Employee Id");
    }
}
