package inventory;

import util.ConnectDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryRepository {

    private static Connection con = ConnectDatabase.getConnection();
    public  static boolean addProduct(int id, String name, Double price, int quantity){

        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO inventory VALUES(?,?,?,?)");

            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setDouble(3, price);
            stmt.setInt(4, quantity);
            return stmt.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static void reduceStock(int id, int quantity){

        try {
            PreparedStatement stmt = con.prepareStatement("UPDATE inventory SET quantity=? WHERE id=?");
            stmt.setInt(1, getQuantity(id) - quantity);
            stmt.setInt(2, id);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean updateStock(int id, int quantity){

        try {
            PreparedStatement stmt = con.prepareStatement("UPDATE inventory SET quantity=? WHERE id=?");
            stmt.setInt(1, quantity);
            stmt.setInt(2, id);
            return stmt.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static int getQuantity(int id){
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT quantity FROM inventory WHERE id=?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static InventoryItem getProductById(int id){
        InventoryItem item = null;
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM inventory WHERE id=?");
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                item = new InventoryItem(new Product(rs.getInt(1),rs.getString(2),rs.getDouble(3)),rs.getInt(4));
            }
            return item;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isProductAvailable(int id){
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM inventory WHERE id=?");
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<InventoryItem> getAllProducts(){
        List<InventoryItem> ls = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT * FROM inventory");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                ls.add(new InventoryItem(new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3)), rs.getInt(4)));
            }
            return ls;
        } catch (SQLException e) {
            e.printStackTrace();
            return ls;
        }

    }


}
