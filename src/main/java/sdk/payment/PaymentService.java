package sdk.payment;

import sdk.bill.BillService;
import sdk.util.ValidationException;

public class PaymentService {
    private BillService billService;

    public PaymentService(){
        this.billService = new BillService();
    }

    public boolean payWithCard(String cardNumber, String cardName, String cardExpiry, int billId) throws ValidationException {
        billService.confirmPayment(billId);
        return true;
    }

    public boolean payWithUpi(String upiId, int billId) throws ValidationException {
        billService.confirmPayment(billId);
        return true;
    }
}
