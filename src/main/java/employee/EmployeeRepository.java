package employee;

import util.ConnectDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    private static Connection con = ConnectDatabase.getConnection();

    public static void addEmployee( String name, String username, String pass, int admin){

        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO employees(name,username,password,admin) VALUES(?,?,?,?)");
            stmt.setString(1,name);
            stmt.setString(2, username);
            stmt.setString(3, pass);
            stmt.setInt(4, admin);
//            PreparedStatement stmt = con.prepareStatement("Insert Into employees values(1, surya, ")
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Employee getEmployee(String username, String pass){

        PreparedStatement stmt = null;
        Employee emp = null;
        try {
            stmt = con.prepareStatement("Select * from employees where username=? and password=?");

            stmt.setString(1, username);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();
            int id;
            String name;
            String username2;
            String pass2;
            int admin;
            if(rs.next()){
                id = rs.getInt(1);
                name = rs.getString(2);
                username2 = rs.getString(3);
                pass2 = rs.getString(4);
                admin = rs.getInt(5);
                emp = new Employee(id, name, username2, pass2, admin == 1? true: false);
                return emp;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emp;
    }

    public static boolean isUsernameAvailable(String username){
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("SELECT * FROM employees WHERE username=?");
            stmt.setString(1,username);
            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return false;
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean removeEmployee(int id){

        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM employees WHERE id=?");
            stmt.setInt(1, id);
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Employee> getAll(){
        List<Employee> ls = new ArrayList<>();
        try {
            PreparedStatement stmt = con.prepareStatement("Select * from employees");
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Employee emp = new Employee(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)==1?true:false);
                ls.add(emp);
            }
            return ls;
        } catch (SQLException e) {
            e.printStackTrace();
            return ls;
        }
    }
}
