/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package titanicbookingsjava;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author foosh
 */
public class TitanicBookingsJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connection conn = JavaConnectDB.connectDB();
        
        String sqlStmt2 = "Insert into students values (2)";        
        try 
        {   
            PreparedStatement preStatement = conn.prepareStatement(sqlStmt2);
            ResultSet result = preStatement.executeQuery();      
            System.out.println("done");
        }
        catch(Exception e )
        {
            System.out.println("Error: "+e);
        }
        String sqlStmt = "Select * from students";        
        try 
        {   
            PreparedStatement preStatement = conn.prepareStatement(sqlStmt);
            ResultSet result = preStatement.executeQuery();            
            while(result.next())
            {
                 System.out.println("Student id: " +         result.getString("ID"));
            }
            System.out.println("done");
        }
        catch(Exception e )
        {
            System.out.println("Error: "+e);
        }
    }
    
}
