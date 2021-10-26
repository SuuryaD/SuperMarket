package sdk.bill;

import java.util.ArrayList;
import java.util.List;

public class BillRepository {

    private final List<Bill> bills;
    private static BillRepository instance;

    public static BillRepository getInstance(){
        if(instance == null)
            instance = new BillRepository();
        return instance;
    }

    public BillRepository() {
        this.bills = new ArrayList<>();
    }

    public List<Bill> getAllBills(){
        return bills;
    }

    public List<Bill> getAllBillsOfCustomer(int customerId) {
        List<Bill> ls = new ArrayList<>();
        for(Bill bill : bills){
            if(bill.getCustomerId() == customerId){
                ls.add(bill);
            }
        }
        return ls;
    }

    public Bill getBillById(int billId){
        for(Bill bill : bills){
            if(bill.getId() == billId)
                return bill;
        }
        return null;
    }

    public void addNewBill(Bill bill) {
        bills.add(bill);
    }

    public boolean removeBill(int billId) {
        return bills.removeIf(bill -> bill.getId() == billId);
    }
}
