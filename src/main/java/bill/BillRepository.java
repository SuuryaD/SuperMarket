package bill;

import inventory.InventoryItem;
import inventory.InventoryRepository;
import util.ConnectDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillRepository {

    public static void addBill(Bill bill){
        Connection con = ConnectDatabase.getConnection();

        try {
            con.setAutoCommit(false);
            PreparedStatement stmt = con.prepareStatement("INSERT INTO bills(time, employee_id, employee_name, amount) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, (int)(bill.getCurrentTime() / 1000));
            stmt.setInt(2, bill.getEmployeeId());
            stmt.setString(3, bill.getEmployeeName());
            stmt.setDouble(4, bill.getAmount());
            int rowChanged = stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;
            if(rs.next())
                id = rs.getInt(1);

            if(rowChanged != 1){
                con.rollback();
                con.setAutoCommit(true);
                return;
            }
            for(BillItem item : bill.getBillItems()) {

                    PreparedStatement stmt2 = con.prepareStatement("INSERT INTO bill_items VALUES(?,?,?)");
                    stmt2.setInt(1, id);
                    stmt2.setInt(2, item.getProduct().getId());
                    stmt2.setInt(3, item.getQuantity());
                    stmt2.execute();

            }
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                con.rollback();
                con.setAutoCommit(false);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public static List<Bill> getAllBills(){

        Connection con = ConnectDatabase.getConnection();
        List<Bill> bills = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM bills");
            ResultSet rs = stmt.executeQuery();
            int id;
            int time;
            int employeeId;
            String employeeName;
            Double amount;
            Bill bill;

            while(rs.next()){
                List<BillItem> ls = new ArrayList<>();
                id = rs.getInt(1);
                PreparedStatement stmt2 = con.prepareStatement("SELECT * FROM bill_items WHERE bill_id=?");
                stmt2.setInt(1, id);
                ResultSet rs2 = stmt2.executeQuery();
                while(rs2.next()){
                    InventoryItem item = InventoryRepository.getProductById(rs2.getInt(2));
                    if(item == null)
                        System.out.println("Inventory item null");
                    BillItem bitem = new BillItem(item.getProduct(), rs2.getInt(3));
                    ls.add(bitem);
                }
                bills.add(new Bill(id,(long) rs.getInt(2) * 1000, rs.getInt(3), rs.getString(4), rs.getDouble(5), ls));
            }
            return bills;
        } catch (SQLException e) {
            e.printStackTrace();
            return bills;
        }

    }



}
