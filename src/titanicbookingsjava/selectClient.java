/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package titanicbookingsjava;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author foosh
 */


public class selectClient extends Ticket implements ActionListener{
    
//CLIENT GUI
    private JFrame jfC;
    private JPanel centerPanelC;
    private JLabel idLbl, nameLbl, surnameLbl, contactLbl;
    private JTextField[][] dataCTxt;
    private JButton[] updateCBtn;
    private JButton[] deleteCBtn;    
    private JButton[] selectCBtn;
    private JButton createCbtn, cancelCBtn;
    private JScrollPane scrollC;
    //END OF CLIENT GUI
    //CLIENT VARIABLES
    private boolean guiCreatedClientBool;
    private String[] currentClientDetails;
    private int amountClients;
    //END OF CLIENT VARIABLES
    
     public void createClientGui(){
        guiCreatedClientBool = true;
        jfC = new JFrame("Client");
        
        centerPanelC = new JPanel(new GridLayout(amountClients+3,5));
        dataCTxt = new JTextField[amountClients+1][4];
        updateCBtn = new JButton[amountClients];
        deleteCBtn = new JButton[amountClients];
        selectCBtn = new JButton[amountClients];
        idLbl = new JLabel("Client ID");
        nameLbl = new JLabel("Name");
        surnameLbl = new JLabel("Surname");
        contactLbl = new JLabel("Contact");
        centerPanelC.add(idLbl);
        centerPanelC.add(nameLbl);
        centerPanelC.add(surnameLbl);
        centerPanelC.add(contactLbl);
        centerPanelC.add(new JLabel() );
       // centerPanelC.add(new JLabel());
       // centerPanelC.add(new JLabel());
        
        for(int i=0;i<amountClients;i++){
            
            for(int j=0;j<4;j++){
                dataCTxt[i][j] = null;
                dataCTxt[i][j] = new JTextField(10);
                centerPanelC.add(dataCTxt[i][j]);
                dataCTxt[i][j].setEditable(false);
            }
            
            
            selectCBtn[i] = null;
            selectCBtn[i] =  new JButton("Select");
            selectCBtn[i].addActionListener(this);
            updateCBtn[i] = null;
            updateCBtn[i] =  new JButton("Update");
            updateCBtn[i].addActionListener(this);
            deleteCBtn[i] = null;
            deleteCBtn[i] = new JButton("Delete");
            deleteCBtn[i].addActionListener(this);
            centerPanelC.add(selectCBtn[i]);
           // centerPanelC.add(new JLabel());
           // centerPanelC.add(new JLabel());
          //  centerPanelC.add(updateCBtn[i]);
          //  centerPanelC.add(deleteCBtn[i]);
        }
        for(int i=0;i<4;i++){
            dataCTxt[amountClients][i] = null;
            dataCTxt[amountClients][i] = new JTextField(10);
            centerPanelC.add(dataCTxt[amountClients][i]);
        }
        createCbtn = new JButton("Create");
        createCbtn.addActionListener(this);
        createCbtn.setBackground(Color.ORANGE);
        cancelCBtn = new JButton("Cancel");
        cancelCBtn.addActionListener(this);
        cancelCBtn.setBackground(Color.ORANGE);
        centerPanelC.add(createCbtn);
        //centerPanelC.add(new JLabel());
        centerPanelC.add(cancelCBtn);
        //amountClients
        
        scrollC = new JScrollPane(centerPanelC);
        scrollC.setPreferredSize(new Dimension(900, 450));
        scrollC.setVerticalScrollBarPolicy(scrollC.VERTICAL_SCROLLBAR_ALWAYS);
        jfC.add(scrollC, BorderLayout.CENTER);
        jfC.setLocation(100,100);
        
        jfC.setSize(900,610);
        jfC.setVisible(true);
        jfC.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //DISPOSE_ON_CLOSE,  DISPOSE_ON_CLOSE 
        jfC.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                int result = JOptionPane.showConfirmDialog(jfC, "Are you sure you would like to exit?");
                if( result==JOptionPane.OK_OPTION)
                {
                    // NOW we change it to dispose on close..
                    jfC.setDefaultCloseOperation(jfC.DISPOSE_ON_CLOSE);
                    jfC.setVisible(false);
                    jfC.dispose();
                    guiCreatedClientBool = false;
                }
            }
        });
    }
     public void getAmountOfClientsOnFlight(){
        
        //GET AMOUNT OF FLIGHTS
        try
        {
            Connection conn = JavaConnectDB.connectDB();
            String count_Client_stmt = "SELECT COUNT (*) FROM Client";
            
            PreparedStatement preStatement = conn.prepareStatement(count_Client_stmt);
            ResultSet result = preStatement.executeQuery();   
            while(result.next()){
                amountClients = result.getInt(1);
                
            }
             
            System.out.println("Amount of Clients: "+ amountClients);
            conn.close();
        }
        catch(Exception count_e)
        {
             System.out.println("Error at count flight from database: \n"+count_e);
        }
    }
    public void getAllClients(){//Passengers
       
        String select_Client_Table_stmt="{call RETRIEVE_PASSENGERs(?,?,?,?,?)}"+"";
        CallableStatement callableStatement;
        ResultSet result;
        Connection conn;
        try 
        {
            getAmountOfClientsOnFlight();
            createClientGui();
            conn = JavaConnectDB.connectDB();
            try 
            {   
                callableStatement = conn.prepareCall(select_Client_Table_stmt);
                callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
                callableStatement.registerOutParameter(2, Types.INTEGER);
                callableStatement.registerOutParameter(3, Types.VARCHAR);
                callableStatement.registerOutParameter(4, Types.VARCHAR);
                callableStatement.registerOutParameter(5, Types.VARCHAR);
                callableStatement.execute();
                result =  (ResultSet)callableStatement.getObject(1);        
                int countRecords = 0;    
               // dataTxt = new String[amountFlights][5];
                
                while(result.next())
                {
                    dataCTxt[countRecords][0].setText(result.getInt("PASSENGERID")+"");
                    dataCTxt[countRecords][1].setText(result.getString("PNAME"));
                    dataCTxt[countRecords][2].setText(result.getString("PSURNAME"));
                    dataCTxt[countRecords][3].setText(result.getString("CONTACTNUMBER")+"");
                                         
                    
                    countRecords++;
                }
            
            }            
            catch(Exception e )
            {
                System.out.println("Error: "+e);
                e.printStackTrace();
            }
            finally
            {
                try 
                {
                    conn.close();

                } 
                catch (SQLException ee) 
                {
                    ee.printStackTrace();
                }
            }
        }
        catch (Exception err) 
        {
            System.out.println("SEND ALL Clients: " + err);
        }
       
   }
    public void actionPerformed(ActionEvent e)
    {
        for(int i=0;i<amountClients;i++)
        {
            if(e.getSource() == selectCBtn[i])
            {
                for(int j=0;j<4;j++)
                {
                    currentClientDetails[j] = dataCTxt[i][j].getText();
                }
                clientId = currentClientDetails[0];
                clientName = currentClientDetails[1];
                clientSurname = currentClientDetails[2];
                guiCreatedClientBool = false;
                jfC.dispose();
            }   
        }
        if(e.getSource() == cancelCBtn)
        {
            int result = JOptionPane.showConfirmDialog(jfC, "Are you sure you would like to close this window?");
            if( result==JOptionPane.OK_OPTION)
            {
                // NOW we change it to dispose on close..
                jfC.setDefaultCloseOperation(jfC.DISPOSE_ON_CLOSE);
                jfC.setVisible(false);
                jfC.dispose();
                guiCreatedClientBool = false;
            }
        }
        if(e.getSource() == createCbtn)
        {
            
        }
    }
}
