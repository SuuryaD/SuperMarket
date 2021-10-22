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

    public void addBill(Bill bill){
        bills.add(bill);
    }

    public List<Bill> getAllBills(){
        return bills;
    }

    public int getNoOfBillsOfCustomer(int customerId){
        int cnt = 0;
        for(Bill bill : bills){
            if(bill.getCustomerId() == customerId){
                cnt++;
            }
        }
        return cnt;
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

    public Bill getBill(int billId){
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
