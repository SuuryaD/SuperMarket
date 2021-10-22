package sdk.payment;

import sdk.util.ValidationException;

public class PaymentController {
    private PaymentService paymentService;

    public PaymentController() {
        this.paymentService = new PaymentService();
    }

    public boolean payWitchCard(String cardNumber, String cardName, String cardExpiry, int billId) throws ValidationException {
        if (!cardNumber.matches("(^[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}$)"))
            throw new ValidationException("Enter a valid card number");
        if(!cardName.matches("^[A-Za-z\\s]+[A-Za-z\\s]*$"))
            throw new ValidationException("Enter a valid name");
        if (!cardExpiry.matches("(^(0[1-9]|1[012])-[0-9]{2})"))
            throw new ValidationException("Enter a valid Expiry date");

        return paymentService.payWithCard(cardNumber, cardName, cardExpiry, billId);
    }

    public boolean payWithUpi(String upiId, int billId) throws ValidationException {
        if (!upiId.matches("^([_a-zA-Z0-9-]+(\\\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+)$"))
            throw new ValidationException("Enter a valid UPI id.");
        if(billId < 0)
            throw new ValidationException("Enter a valid Bill Id");

        return paymentService.payWithUpi(upiId, billId);

    }
}
