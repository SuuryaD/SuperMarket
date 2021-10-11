package bill;


import util.Globals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Bill Class contains all the information related to a bill.
 * Holds list of all items added.
 */
public class Bill {
    private static int NUMBER = 1;
    private final int id;
    private final Date currentDate;
    private final List<BillItem> billItems;
    private double amount;
    private final int  employeeId;
    private final String employeeName;

    /**
     * Initializes all the properties of the bill.
     * @param employeeId
     * @param name
     */
    public Bill(int employeeId, String name){
        id = NUMBER;
        NUMBER++;
        currentDate = new Date();
        billItems = new ArrayList<>();
        amount = 0.0;
        this.employeeId = employeeId;
        this.employeeName = name;
    }

    /**
     *
     * @return List of Bill items.
     */
    public List<BillItem> getBillItems() {
        return billItems;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        DateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
        builder.append("Bill ID: ").append(id).append("\t Time: ").append(sdf.format(currentDate)).append("\n");
        builder.append("Employee Id: ").append(employeeId).append("\t Employee Name: ").append(employeeName).append("\n");

        ArrayList<String> headers = new ArrayList<>();
        headers.add("Item No");
        headers.add("Name");
        headers.add("Quantity");
        headers.add("MRP");
        headers.add("Price");

        ArrayList<ArrayList<String>> content = new ArrayList<>();
        int no = 0;
        for (BillItem billItem : billItems) {
            no++;
            content.add(new ArrayList<>(Arrays.asList(String.valueOf(no),
                    billItem.getProduct().getName(),
                    String.valueOf(billItem.getQuantity()),
                    String.valueOf(billItem.getProduct().getPrice()),
                    String.valueOf(billItem.getPrice())
            )));
        }
        builder.append(Globals.printTable(headers, content)).append("\n");
        builder.append("Total Amount: ").append(Math.round(amount * 100.0) / 100.0).append("\n");

        return builder.toString();
    }



    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
