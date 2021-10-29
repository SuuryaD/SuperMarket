package sdk.inventory.controller;

import sdk.util.ValidationException;

public class InventoryValidation {

    public void validateProductId(int productId) throws ValidationException {
        if(productId <= 0)
            throw new ValidationException("Enter a valid product Id");
    }

    public void validateQuantity(int quantity) throws ValidationException {
        if(quantity <= 0)
            throw new ValidationException("Enter a valid quantity");
    }

    public void validateProductName(String productName) throws ValidationException {
        if(productName.matches("^\\S+(?: \\S+)*$"))
            throw new ValidationException("Enter a valid Product Name");
    }

    public void validateProductPrice(Double price) throws ValidationException {
        if(price <= 0.0)
            throw new ValidationException("Enter a valid Product price");
    }

}
