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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author foosh
 */
public class Ticket  implements ActionListener{
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

//TICKET GUI
    private JFrame jfT;
    private JPanel topPanelT,centerPanelT, bottomPanelT;
    private int flightNumber;
    private int amountTickets;
    private int flightNumberT;
    boolean flightCancelled;
    private int rowsT;
    private boolean guiCreatedTicketBool;
    private int currentActiveflightNumber;
    private int countSeatsTaken;
    private boolean filterCity;
    private String cityToFilter;
    private boolean[][] ticketDetails;
    //TOP PANEL
    private JLabel ticketNumTLbl, passNameTLbl, passSurnTLbl, ticketQuantityTLbl,  totalTLbl, actionTLbl, paymentTLbl;
    private JTextField[][] dataTTxt;
    private JButton[] deleteTBtn;
    private JButton[] paymentTBtn;
    private JScrollPane scrollT;
    
    //CENTER PANEL
    private String[] ticketsLeftTCbo;
    private JLabel ticketNumTLbl2, nameTLbl, surnameTLbl, ticketQuantity2TLbl, totalT2Lbl;
    //private JComboBox<String> quantityTicTCbo;
    public static JTextField ticketNumTTxt, nameTTxt, surnameTTxt, amountTTxt;
    private JButton btnAddTicket, selectClientBtn,selectQuantityBtn;
    private JTextField ticketsQuantityTTxt;
    //BOTTOM PANEL    
    private JButton saveChangesTBtn, cancelFlightTBtn, deleteAllTicketsTBtn, deleteFlightTBtn;
    
    
    //CLIENT
    public String clientName, clientSurname, clientId;
    
    
    public Ticket(){
        //CLIENT
        currentClientDetails = new String[4];
        //TICKET
        guiCreatedTicketBool = false;
        rowsT = 0;
        flightCancelled = false;
        guiCreatedClientBool = false;
        nameTTxt = new JTextField(10);
        surnameTTxt = new JTextField(10);
        clientName = "";
        clientSurname = "";
        //ticketQuantitySelected = false;
    }
    public void updateClient()
    {
        //addCenterPanelTicket();
        nameTTxt.setText(clientName);
        surnameTTxt.setText(clientSurname);
    }
    
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
            String count_Client_stmt = "SELECT COUNT (*) FROM Passenger";
            
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
    
     public void createTicketGui(int flightnum)
    {
        
        flightNumberT = flightnum;
        
        
        jfT = new JFrame("Manage Tickets - Flight: "+flightNumberT);        
        topPanelT = new JPanel(new GridLayout(rowsT+1, 7));         
        centerPanelT = new JPanel(new GridLayout(3,5));
        bottomPanelT = new JPanel();
        
        //addTopPanel();
        
        
        saveChangesTBtn = new JButton("Close Flight Management");
        saveChangesTBtn.addActionListener(this);
        cancelFlightTBtn = new JButton("Cancel/Enable Flight");
        cancelFlightTBtn.addActionListener(this);
        deleteAllTicketsTBtn = new JButton("Delete all Tickets");
        deleteAllTicketsTBtn.addActionListener(this);
        deleteFlightTBtn = new JButton("Delete Flight & Tickets");
        deleteFlightTBtn.addActionListener(this);         
            
        bottomPanelT.add(saveChangesTBtn);
        bottomPanelT.add(cancelFlightTBtn);
        bottomPanelT.add(deleteAllTicketsTBtn);
        bottomPanelT.add(deleteFlightTBtn);        
        
        scrollT = new JScrollPane(topPanelT);
        scrollT.setPreferredSize(new Dimension(870, 350));
        scrollT.setVerticalScrollBarPolicy(scrollT.VERTICAL_SCROLLBAR_ALWAYS);
        jfT.add(scrollT, BorderLayout.NORTH);
        jfT.add(centerPanelT, BorderLayout.CENTER);
        jfT.add(bottomPanelT, BorderLayout.SOUTH);
        jfT.setLocation(100,100);        
        jfT.setSize(870,500);
        jfT.setVisible(true);
        jfT.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //DISPOSE_ON_CLOSE,  DISPOSE_ON_CLOSE 
        jfT.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                int result = JOptionPane.showConfirmDialog(jfT, "Are you sure you would like to close the window?");
                if( result==JOptionPane.OK_OPTION)
                {
                    // NOW we change it to dispose on close..
                    jfT.setDefaultCloseOperation(jfT.DISPOSE_ON_CLOSE);
                    jfT.setVisible(false);
                    jfT.dispose();
                    guiCreatedTicketBool = false;
                }
                jfT.setVisible(true);
            }
        });
    }
     
    
    
    public void addCenterPanelTicket()
    {
        //Center panel
        jfT.remove(centerPanelT);
        centerPanelT = new JPanel(new GridLayout(3,5));
        selectClientBtn = new JButton("Select Client");
        selectClientBtn.addActionListener(this);
        selectQuantityBtn = new JButton("Select Quantity");
        selectQuantityBtn.addActionListener(this);
        centerPanelT.add(selectClientBtn);
        centerPanelT.add(selectQuantityBtn);
        for(int i=0;i<3;i++)
        {
            JLabel l1 = new JLabel("");
            l1.setOpaque(true);
            l1.setBackground(Color.GRAY);
            centerPanelT.add(l1);
        }
        
        ticketNumTLbl2 = new JLabel("Ticket Number");
        nameTLbl = new JLabel("Name");
        surnameTLbl = new JLabel("Surname");
        ticketQuantity2TLbl = new JLabel("Ticket Quantity");
        totalT2Lbl = new JLabel("Total Amount");
        
      //  centerPanelT.add(ticketNumTLbl2);
        centerPanelT.add(nameTLbl);    
        centerPanelT.add(surnameTLbl);    
        centerPanelT.add(ticketQuantity2TLbl);    
        centerPanelT.add(totalT2Lbl);    
        centerPanelT.add(new JLabel());  
        
       // ticketNumTTxt = new JTextField(10);        
        amountTTxt = new JTextField(10);
        btnAddTicket = new JButton("Add Ticket");
        btnAddTicket.addActionListener(this);
        
        //centerPanelT.add(ticketNumTTxt);
        centerPanelT.add(nameTTxt);    
        centerPanelT.add(surnameTTxt);  
        nameTTxt.setText(clientName);
        nameTTxt.setEditable(false);
        surnameTTxt.setText(clientSurname);
        surnameTTxt.setEditable(false);

        
        
        
        
        //center
        ticketsQuantityTTxt = new JTextField(10);
        ticketsQuantityTTxt.setEditable(false);
        centerPanelT.add(ticketsQuantityTTxt);
        amountTTxt.setEditable(false);
        centerPanelT.add(amountTTxt);           
        centerPanelT.add(btnAddTicket); 

        jfT.add(centerPanelT, BorderLayout.CENTER);
        jfT.validate();
        jfT.repaint();
        //end center
    }
    public void addTopPanel()
    {
        jfT.remove(scrollT);
        int difference = 10-rowsT;
        if(difference>0)
        {
            topPanelT = new JPanel(new GridLayout(rowsT+1+difference, 7)); 
        }
        else
        {
            topPanelT = new JPanel(new GridLayout(rowsT+1, 7)); 
        }
              
        //end of center panel
        
        ticketNumTLbl = new JLabel("Ticket Number");
        passNameTLbl = new JLabel("Name");
        passSurnTLbl = new JLabel("Surname");
        ticketQuantityTLbl = new JLabel("Quantity");
        totalTLbl = new JLabel("Total Amount");
        actionTLbl = new JLabel("Delete Ticket");
        paymentTLbl = new JLabel("Payment");
        
        topPanelT.add(ticketNumTLbl);
        topPanelT.add(passNameTLbl);
        topPanelT.add(passSurnTLbl);
        topPanelT.add(ticketQuantityTLbl);
        topPanelT.add(totalTLbl);
        topPanelT.add(actionTLbl);
        topPanelT.add(paymentTLbl);
        
        dataTTxt = new JTextField[rowsT][5];
        deleteTBtn = new JButton[rowsT];
        paymentTBtn = new JButton[rowsT];
        
        
        for(int i=0;i<rowsT;i++)
        {            
            for(int j=0;j<5;j++)
            {
                dataTTxt[i][j] = new JTextField(10);
                dataTTxt[i][j].setEditable(false);
                topPanelT.add(dataTTxt[i][j]); 
            }
            deleteTBtn[i] = new JButton("Delete Ticket");
            deleteTBtn[i].addActionListener(this);
            topPanelT.add(deleteTBtn[i]);
            paymentTBtn[i] = new JButton("Make Payment");
            paymentTBtn[i].addActionListener(this);
            topPanelT.add(paymentTBtn[i]);            
        }
        
        for(int i=0;i<difference;i++)
        {
            for(int j=0;j<6;j++)
            {
                topPanelT.add(new JLabel("")); 
            }
        }
       
        scrollT = new JScrollPane(topPanelT);        
        scrollT.setPreferredSize(new Dimension(870, 350));
        scrollT.setVerticalScrollBarPolicy(scrollT.VERTICAL_SCROLLBAR_ALWAYS);
        jfT.add(scrollT, BorderLayout.NORTH);
        jfT.validate();
        jfT.repaint();
    }
    public void getAmountOfTicketsOnFlight(){
        
        //GET AMOUNT OF FLIGHTS
        try
        {
            Connection conn = JavaConnectDB.connectDB();
            String count_tickets_stmt = "SELECT COUNT (*) FROM TICKET WHERE flightNumber = '"+flightNumber+ "'";
            
            PreparedStatement preStatement = conn.prepareStatement(count_tickets_stmt);
            ResultSet result = preStatement.executeQuery();   
            while(result.next()){
                amountTickets = result.getInt(1);
                
            }
            rowsT = amountTickets;
            System.out.println("Amount of Tickets: "+ amountTickets);
            conn.close();
        }
        catch(Exception count_e)
        {
             System.out.println("Error at count flight from database: \n"+count_e);
        }
    }
    public void getCancelled(){
        
        //GET AMOUNT OF FLIGHTS
        try
        {
            Connection conn = JavaConnectDB.connectDB();
            String count_tickets_stmt = "select cancelled from flight where flightnumber = '"+flightNumber+ "' ";
            
            PreparedStatement preStatement = conn.prepareStatement(count_tickets_stmt);
            ResultSet result = preStatement.executeQuery();   
            int fCancelled=0;
            while(result.next()){
                fCancelled = result.getInt(1);                
            }
            if(fCancelled == 0){
                flightCancelled = false;
            }
            else
            {
                flightCancelled = true;
            }  
            System.out.println("Amount of Tickets: "+ amountTickets);
            conn.close();
        }
        catch(Exception count_e)
        {
             System.out.println("Error at count flight from database: \n"+count_e);
        }
    }
    
    public void retrieveAllTickets(int currentActiveflightNumber)
    {
        //--------------------------------------------
        String select_Ticket_Table_stmt="{call RETRIEVE_TICKET(?,?,?,?,?,?,?)}"+"";
       
        

        CallableStatement callableStatement;
        ResultSet result;
        Connection conn;
        flightNumber = currentActiveflightNumber;
        
        try 
        {
            getAmountOfTicketsOnFlight();
            System.out.println("Amount of Tickets2: "+ amountTickets);
            if(guiCreatedTicketBool == false)
            {
                createTicketGui(flightNumber);  
                guiCreatedTicketBool = true;
            }        
            addTopPanel(); 
            conn = JavaConnectDB.connectDB();
            try 
            {   
                callableStatement = conn.prepareCall(select_Ticket_Table_stmt);
                callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
                callableStatement.setInt(2, flightNumber);
                callableStatement.registerOutParameter(3, Types.INTEGER);
                callableStatement.registerOutParameter(4, Types.VARCHAR);
                callableStatement.registerOutParameter(5, Types.VARCHAR);
                callableStatement.registerOutParameter(6, Types.INTEGER);
                callableStatement.registerOutParameter(7, Types.DECIMAL);
                callableStatement.execute();
                result =  (ResultSet)callableStatement.getObject(1);        
                int countRecords = 0;    
               // dataTxt = new String[amountFlights][5];
                
                while(result.next())
                {
                    dataTTxt[countRecords][0].setText(result.getInt("ticketnumber")+"");
                    dataTTxt[countRecords][1].setText(result.getString("pName"));
                    dataTTxt[countRecords][2].setText(result.getString("pSurname"));
                    dataTTxt[countRecords][3].setText(result.getInt("seatsBooked")+"");
                    dataTTxt[countRecords][4].setText(result.getDouble("amountPaid")+""); 
                                         
                    
                    countSeatsTaken = countSeatsTaken+( Integer.parseInt( dataTTxt[countRecords][3].getText()+""));
                    countRecords++;
                }
                if(flightCancelled)
                {
                    for(int j=0;j<5;j++)
                    {
                        dataTTxt[countRecords][j].setOpaque(true);
                        dataTTxt[countRecords][j].setBackground(Color.ORANGE);
                    }
                }
                addCenterPanelTicket();
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
            System.out.println("SEND ALL FLIGHTS: " + err);
        } 
        
        
           
        
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == selectClientBtn)
        {
            if(!guiCreatedClientBool){
                guiCreatedClientBool = true;
                
                getAllClients();
            }
            else{
                JOptionPane.showMessageDialog(null, "Only one instance of the select client window may be open");
            }
        }
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
                System.out.println("Client name"+clientName);
                guiCreatedClientBool = false;
                jfC.dispose();
                this.updateClient();
                
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
