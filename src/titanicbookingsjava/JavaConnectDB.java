/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package titanicbookingsjava;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author foosh
 */
public class JavaConnectDB {
    public static Connection connectDB(){
        try{
            String driverName = "oracle.jdbc.OracleDriver";   
            Class.forName(driverName);
            System.out.println("Trying to connect to database");
            Connection conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYSTEM","panda");
            System.out.println("Connected");  
            return conn;
        }
        catch(Exception e){
            System.out.println("\nError: \n");
            e.printStackTrace();
            System.out.println("\n---------------\nError connecting to database\n"+e);
        }
        return null;
                
    } 
}
