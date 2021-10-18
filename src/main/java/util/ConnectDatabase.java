package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDatabase {


        private static Connection con = null;

        public static Connection getConnection(){
            if(con != null)
                return con;

            String url = "jdbc:sqlite:C://sqlite/test.db";

            return getConnection(url);
        }
        private static Connection getConnection(String url){
            try{
                con = DriverManager.getConnection(url);
            }catch (Exception e){
                System.out.println("Connection to database failed");
            }
            return con;
        }
}
