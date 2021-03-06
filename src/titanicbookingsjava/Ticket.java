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
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
    
    //QUANTITY GUI
    private JFrame jfQ;
    private JPanel centerPanelQ, northPanelQ, southPanelQ;
    private JLabel ticketQuantityLbl, vegLbl, chicLbl, beefLbl, BeverageLbl;
    private JCheckBox[] beverageQCB;
    private JRadioButton[] vegetarianRB;
    private JRadioButton[] chickenRB;
    private JRadioButton[] beefRB;
    private JScrollPane scrollQ;
    private String[] quantityCboItems;
    private JComboBox<String> quantityCbo;
    private boolean[] comboQSelected;
    ButtonGroup bgroupQ[];
    private int quantityAmount;
    //private boolean[] bgQSelected
    private JButton cancelQBtn, confirmQBtn ;
    //END OF QUANTITY GUI
    //QUANTITY VARIABLES
    private boolean guiSelectQuantityBool;
    private double ticketPrice;
    private int[][] mealSelection;
    //END OF QUANTITY VARIABLES
    
    //PAYMENT
    private JFrame jfP;
    private JPanel northPanelP, centerPanelP, southPanelP;
    private JLabel flightPLbl, fromPLbl, toPLbl, clientPLbl, contactPLbl, amountTicketsPLbl, priceptPLbl, paidPLbl, duePLbl, amountPLbl,
    flightPLbl2, fromPLbl2, toPLbl2, clientPLbl2, contactPLbl2, amountTicketsPLbl2, priceptPLbl2, paidPLbl2, duePLbl2, amountPLbl2;
    private JTextField amountPTxt;
    private JButton cancelPBtn, payPBtn;
    private boolean guiMakePaymentBool;
    private String clientID, ticketId;
    private double totalDue;
    //END OF PAYMENT
    
    private boolean ticketQuantitySelected;  
    int amountReceipts;
    int amountMeals;    
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
        clientId = "";
        clientName = "";
        clientSurname = "";
        //ticketQuantitySelected = false;
        //Quantity & Meal
        ticketQuantitySelected = false;
        quantityAmount = 0;
        amountReceipts = 0;
        amountMeals = 0;
        
        
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
        
        centerPanelC = new JPanel(new GridLayout(amountClients+3,6));
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
        centerPanelC.add(new JLabel());
        
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
            centerPanelC.add(updateCBtn[i]);
            //centerPanelC.add(deleteCBtn[i]);
        }
        for(int i=0;i<4;i++){
            dataCTxt[amountClients][i] = null;
            dataCTxt[amountClients][i] = new JTextField(10);
            centerPanelC.add(dataCTxt[amountClients][i]);
        }
        dataCTxt[amountClients][0].setEditable(false);
        
        createCbtn = new JButton("Create");
        createCbtn.addActionListener(this);
        createCbtn.setBackground(Color.ORANGE);
        cancelCBtn = new JButton("Cancel");
        cancelCBtn.addActionListener(this);
        cancelCBtn.setBackground(Color.ORANGE);
        centerPanelC.add(createCbtn);
        centerPanelC.add(new JLabel());
        centerPanelC.add(new JLabel());
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
             System.out.println("Error at getAmountOfTicketsOnFlight \n"+count_e);
        }
    }
    public void getTicketPrice(){
        
        try
        {
            Connection conn = JavaConnectDB.connectDB();
            String get_price_stmt = "SELECT seatprice FROM FLIGHT WHERE flightNumber = '"+flightNumber+ "'";
            
            PreparedStatement preStatement = conn.prepareStatement(get_price_stmt);
            ResultSet result = preStatement.executeQuery();   
            while(result.next()){
                ticketPrice = result.getInt(1);
                
            }
            System.out.println("Ticket Price: "+ ticketPrice);
            conn.close();
        }
        catch(Exception count_e)
        {
             System.out.println("Error at getTicketPrice: \n"+count_e);
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
            //System.out.println("Amount of Tickets: "+ amountTickets);
            conn.close();
        }
        catch(Exception count_e)
        {
             System.out.println("Error at get cancelled from database: \n"+count_e);
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
            getCancelled();
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
                    if(flightCancelled)
                    {
                        for(int j=0;j<5;j++)
                        {
                            dataTTxt[countRecords][j].setOpaque(true);
                            dataTTxt[countRecords][j].setBackground(Color.ORANGE);
                        }
                    }
                    countRecords++;
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
     public void selectQuantityGui()
    {
        guiSelectQuantityBool = true;
        jfQ = new JFrame("Quantity & Meals");
        centerPanelQ = new JPanel(new GridLayout(11,4));
        northPanelQ = new JPanel(new GridLayout(1,2));
        southPanelQ = new JPanel(new GridLayout(1,2));
        
        
        ticketQuantityLbl = new JLabel("Select Ticket Quantity:");
        northPanelQ.add(ticketQuantityLbl);
        
        int seatsSold = 0;
        try
        {
            Connection conn = JavaConnectDB.connectDB();
            String retrieve_seats_stmt = "SELECT seatsSold FROM Flight WHERE flightNumber = '"+flightNumber+"'";
            PreparedStatement preStatement = conn.prepareStatement(retrieve_seats_stmt);
            ResultSet result = preStatement.executeQuery();   
            while(result.next()){
                seatsSold = result.getInt(1);
            }
            conn.close();
        }
        catch(Exception count_e)
        {
             System.out.println("Error at adding ticket, update seats\n"+count_e+"\n");
             count_e.printStackTrace();
        }
        countSeatsTaken = seatsSold;
        //String[] quantityCboItems =  {"",""};
        
        switch(countSeatsTaken)
        {
            case 10:
            {
                String[] quantityCboItems = {"Flight Full"};
                quantityCbo = new JComboBox<>(quantityCboItems);
                quantityCbo.addActionListener(this);
                centerPanelQ.add(quantityCbo);
            }break;
            case 9:{String[] quantityCboItems = {"1"};quantityCbo = new JComboBox<>(quantityCboItems);quantityCbo.addActionListener(this);northPanelQ.add(quantityCbo);}break;
            case 8:{String[] quantityCboItems = {"1", "2"};quantityCbo = new JComboBox<>(quantityCboItems);quantityCbo.addActionListener(this);northPanelQ.add(quantityCbo);}break;
            case 7:{String[] quantityCboItems = {"1", "2", "3"};quantityCbo = new JComboBox<>(quantityCboItems);quantityCbo.addActionListener(this);northPanelQ.add(quantityCbo);}break;
            case 6:{String[] quantityCboItems = {"1", "2", "3", "4"};quantityCbo = new JComboBox<>(quantityCboItems);quantityCbo.addActionListener(this);northPanelQ.add(quantityCbo);}break;
            case 5:{String[] quantityCboItems = {"1", "2", "3", "4", "5"};quantityCbo = new JComboBox<>(quantityCboItems);quantityCbo.addActionListener(this);northPanelQ.add(quantityCbo);}break;
            case 4:{String[] quantityCboItems = {"1", "2", "3", "4", "5","6"};quantityCbo = new JComboBox<>(quantityCboItems);quantityCbo.addActionListener(this);northPanelQ.add(quantityCbo);}break;
            case 3:{String[] quantityCboItems = {"1", "2", "3", "4", "5","6","7"};quantityCbo = new JComboBox<>(quantityCboItems);quantityCbo.addActionListener(this);northPanelQ.add(quantityCbo);}break;
            case 2:{String[] quantityCboItems = {"1", "2", "3", "4", "5","6","7","8"};quantityCbo = new JComboBox<>(quantityCboItems);quantityCbo.addActionListener(this);northPanelQ.add(quantityCbo);}break;
            case 1:{String[] quantityCboItems = {"1", "2", "3", "4", "5","6","7","8","9"};quantityCbo = new JComboBox<>(quantityCboItems);quantityCbo.addActionListener(this);northPanelQ.add(quantityCbo);}break;
            case 0:{String[] quantityCboItems = {"1", "2", "3", "4", "5","6","7","8","9","10"};quantityCbo = new JComboBox<>(quantityCboItems);quantityCbo.addActionListener(this);northPanelQ.add(quantityCbo);}break;

        } 
        scrollQ = new JScrollPane(centerPanelQ);
        scrollQ.setPreferredSize(new Dimension(400, 350));
        scrollQ.setVerticalScrollBarPolicy(scrollQ.VERTICAL_SCROLLBAR_ALWAYS);
        jfQ.add(scrollQ);
        addCenterPanelQuantity(1);
       // quantityCbo = new JComboBox<>(quantityCboItems);
        cancelQBtn = new JButton("Cancel");
        cancelQBtn.addActionListener(this);
        confirmQBtn = new JButton("Submit Selection");
        confirmQBtn.addActionListener(this);

        southPanelQ.add(cancelQBtn);
        southPanelQ.add(confirmQBtn);

        jfQ.add(northPanelQ, BorderLayout.NORTH);
        jfQ.add(southPanelQ, BorderLayout.SOUTH);
        jfQ.setLocation(100,100);        
        jfQ.setSize(400,370);
        jfQ.setVisible(true);
        jfQ.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //DISPOSE_ON_CLOSE,  DISPOSE_ON_CLOSE 
        jfQ.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                int result = JOptionPane.showConfirmDialog(jfQ, "Are you sure you would like to exit?");
                if( result==JOptionPane.OK_OPTION)
                {
                    // NOW we change it to dispose on close..
                    jfQ.setDefaultCloseOperation(jfT.DISPOSE_ON_CLOSE);
                    jfQ.setVisible(false);
                    jfQ.dispose();
                    guiSelectQuantityBool = false;
                }
            }
        });
        
    }
    public void addCenterPanelQuantity(int quantity)
    {        
        jfQ.remove(scrollQ);
        centerPanelQ = new JPanel(new GridLayout(quantity+1,4));
        scrollQ = new JScrollPane(centerPanelQ);
        scrollQ.setPreferredSize(new Dimension(400, 350));
        scrollQ.setVerticalScrollBarPolicy(scrollQ.VERTICAL_SCROLLBAR_ALWAYS);
        vegLbl = new JLabel("Vegetarian");
        chicLbl = new JLabel("Chicken");
        beefLbl = new JLabel("Beef");
        BeverageLbl = new JLabel("Beverage");
        comboQSelected = new boolean[quantity];
        ticketDetails = new boolean[quantity][4];
        centerPanelQ.add(vegLbl);
        centerPanelQ.add(chicLbl);
        centerPanelQ.add(beefLbl);
        centerPanelQ.add(BeverageLbl);
        
        vegetarianRB = new JRadioButton[quantity];
        chickenRB = new JRadioButton[quantity];
        beefRB = new JRadioButton[quantity];
        bgroupQ = new ButtonGroup[quantity];
        beverageQCB = new JCheckBox[quantity];
        for(int i=0;i<quantity;i++)
        {
            vegetarianRB[i] = null;
            vegetarianRB[i] = new JRadioButton();
            vegetarianRB[i].addActionListener(this);
            chickenRB[i] = null;
            chickenRB[i] = new JRadioButton();
            chickenRB[i].addActionListener(this);
            beefRB[i] = null;
            beefRB[i] = new JRadioButton();
            beefRB[i].addActionListener(this);
            bgroupQ[i] = null;
            bgroupQ[i] = new ButtonGroup();
            bgroupQ[i].add(vegetarianRB[i]);
            bgroupQ[i].add(chickenRB[i]);
            bgroupQ[i].add(beefRB[i]);
            beverageQCB[i] = null;
            beverageQCB[i] = new JCheckBox();
            beverageQCB[i].addActionListener(this);
            centerPanelQ.add(vegetarianRB[i]);
            centerPanelQ.add(chickenRB[i]);
            centerPanelQ.add(beefRB[i]);
            centerPanelQ.add(beverageQCB[i]);
            
            
        }
        jfQ.add(scrollQ, BorderLayout.CENTER);
        jfQ.validate();
        jfQ.repaint();
    }
    
    public void makePaymentGui(int clientIndex)
    {
        guiMakePaymentBool = true;
        /*
         *  private JFrame jfP;
    private JPanel northPanelP, centerPanelP, southPanelP;
    private JLabel flightPLbl, fromPLbl, toPLbl, clientPLbl, contactPLbl, amountTicketsPLbl, priceptPLbl, paidPLbl, duePLbl, amountPLbl,
    flightPLbl2, fromPLbl2, toPLbl2, clientPLbl2, contactPLbl2, amountTicketsPLbl2, priceptPLbl2, paidPLbl2, duePLbl2, amountPLbl2;
    private JTextField amountPTxt;
    private JButton cancelPBtn, payPBtn;
         */
        jfP = new JFrame("Make Payment");
        //northPanelP = new JPanel(new GridLayout(9,2));
        centerPanelP = new JPanel(new GridLayout(8,2));
        southPanelP = new JPanel(new GridLayout(1,2));
        flightPLbl = new JLabel("Flight");
        //fromPLbl = new JLabel("");
        //toPLbl = new JLabel("");
        clientPLbl = new JLabel("Client: ");
        contactPLbl = new JLabel("Contact: ");
        amountTicketsPLbl = new JLabel("Amount of Tickets: ");
        priceptPLbl = new JLabel("Price per Ticket:");
        paidPLbl = new JLabel("Total Paid: ");
        duePLbl = new JLabel("Total Due: ");
        amountPLbl = new JLabel("Payment Amount: ");
        
        String clientName = dataTTxt[clientIndex][1].getText();
        String clientSurname = dataTTxt[clientIndex][2].getText();
        String clientContact = "";//get contact from sql
        ticketId = dataTTxt[clientIndex][0].getText();
        
        
        double pricePerTicket = 0.0; //get from sql
        double totalPaid = 0.0;
        totalDue = 0.0;
        
        try
        {
            Connection conn = JavaConnectDB.connectDB();
            String retrieve_seats_stmt = "SELECT contactNumber, Passenger.passengerid FROM Passenger JOIN Ticket on ticket.passengerid = passenger.passengerID WHERE ticket.flightNumber = '"+flightNumber+"' AND TicketNumber = '"+ticketId+"'";
            PreparedStatement preStatement = conn.prepareStatement(retrieve_seats_stmt);
            ResultSet result = preStatement.executeQuery();   
            while(result.next()){
                clientContact = result.getString(1); 
                clientID = result.getString(2); 
            }
            conn.close();
        }
        catch(Exception count_e)
        {
             System.out.println("Error at get client contactnumber at make payment\n"+count_e+"\n");
             count_e.printStackTrace();
        }
        try
        {
            Connection conn = JavaConnectDB.connectDB();
            String retrieve_seats_stmt = "SELECT seatPrice FROM Flight WHERE flightNumber = '"+flightNumber+"'";
            PreparedStatement preStatement = conn.prepareStatement(retrieve_seats_stmt);
            ResultSet result = preStatement.executeQuery();   
            while(result.next()){
                pricePerTicket = result.getDouble(1);                
            }
            conn.close();
        }
        catch(Exception count_e)
        {
             System.out.println("Error at get seatPrice contactnumber at make payment\n"+count_e+"\n");
             count_e.printStackTrace();
        }
        
        try
        {
            Connection conn = JavaConnectDB.connectDB();
            String retrieve_seats_stmt = "SELECT amountPaid FROM receipt WHERE TicketNumber = '"+ticketId+"'";
            PreparedStatement preStatement = conn.prepareStatement(retrieve_seats_stmt);
            ResultSet result = preStatement.executeQuery();   
            while(result.next()){
                totalPaid =totalPaid+ result.getDouble(1);                
            }
            conn.close();
        }
        catch(Exception count_e)
        {
             System.out.println("Error at get totalpaid at make payment\n"+count_e+"\n");
             count_e.printStackTrace();
        }
        totalDue = (pricePerTicket*Double.parseDouble(dataTTxt[clientIndex][3].getText()))-totalPaid; 
        
        flightPLbl2 = new JLabel("" +flightNumberT);
        clientPLbl2 = new JLabel("" + clientName + " "+clientSurname);
        contactPLbl2 = new JLabel("" + clientContact);
        amountTicketsPLbl2 = new JLabel("" + dataTTxt[clientIndex][3].getText());
        priceptPLbl2 = new JLabel("" + "R"+pricePerTicket);
        paidPLbl2 = new JLabel("" + "R"+totalPaid);
        duePLbl2 = new JLabel(""+ "R"+totalDue);
        
        //getAllReceipts();
        //getAllMeals();
        
        
        /*  flightnumber = 
            client
            contact
            amount of tickets
            price per ticket
            total paid
            total due
        
            enter payment amount
        */
       /* for(int j=0;j<amountFlights;j++)
        {
            if(flight[j].getFlightNumber() ==currentActiveflightNumber)
            {
                flightPLbl2.setText(""+currentActiveflightNumber+" From: "+ flight[j].getDepartCity()+" To: "+ flight[j].getArriveCity());
                ticketPrice=  flight[j].getSeatPrice();
                priceptPLbl2.setText("R"+ticketPrice+"");
            }                    
        }
        for(int k=0;k<rowsT;k++)
        {
           if(ticket[k].getFlightNumber()==(currentActiveflightNumber))
           {
               amountTicketsPLbl2.setText(ticket[k].getSeatsBooked()+"");
               passengerIDTemp= ticket[k].getPassengerID();
               totalPaid = ticket[k].getAmountPaid();
               paidPLbl2.setText("R"+totalPaid+"");
               amountTicketsBooked = ticket[k].getSeatsBooked();
           }
               
        }
        for(int m=0;m<amountClients;m++)
        {
            if(passenger[m].getPassengerID()== passengerIDTemp)
            {
                clientPLbl2.setText(passenger[m].getpName() + " " +passenger[m].getpSurname());                        
                contactPLbl2.setText(passenger[m].getContactNumber());
            }
        }
       duePLbl2.setText((ticketPrice*amountTicketsBooked)-totalPaid+"");
*/
        amountPTxt = new JTextField(10);
        
        cancelPBtn = new JButton("Cancel");
        cancelPBtn.addActionListener(this);
        payPBtn = new JButton("Make Payment");
        payPBtn.addActionListener(this);
        
        centerPanelP.add(flightPLbl);
        centerPanelP.add(flightPLbl2);
        centerPanelP.add(clientPLbl);
        centerPanelP.add(clientPLbl2);
        centerPanelP.add(contactPLbl);
        centerPanelP.add(contactPLbl2);
        centerPanelP.add(amountTicketsPLbl);
        centerPanelP.add(amountTicketsPLbl2);
        centerPanelP.add(priceptPLbl);
        centerPanelP.add(priceptPLbl2);
        centerPanelP.add(paidPLbl);
        centerPanelP.add(paidPLbl2);
        centerPanelP.add(duePLbl);
        centerPanelP.add(duePLbl2);
        centerPanelP.add(amountPLbl);
        centerPanelP.add(amountPTxt);
        
        southPanelP.add(cancelPBtn);
        southPanelP.add(payPBtn);
        jfP.add(centerPanelP, BorderLayout.CENTER);
        jfP.add(southPanelP, BorderLayout.SOUTH);
        jfP.setLocation(100,100);        
        jfP.setSize(400,370);
        jfP.setVisible(true);
        jfP.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //DISPOSE_ON_CLOSE,  DISPOSE_ON_CLOSE 
        jfP.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                int result = JOptionPane.showConfirmDialog(jfP, "Are you sure you would like to exit?");
                if( result==JOptionPane.OK_OPTION)
                {
                    // NOW we change it to dispose on close..
                    jfP.setDefaultCloseOperation(jfP.DISPOSE_ON_CLOSE);
                    jfP.setVisible(false);
                    jfP.dispose();
                    guiMakePaymentBool = false;
                }
            }
        });
        
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == selectClientBtn)
        {//DONE
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
        
        if(e.getSource() == selectQuantityBtn)
        {//DONE
            int seatsSold = 0;
            try
            {
                Connection conn = JavaConnectDB.connectDB();
                String retrieve_seats_stmt = "SELECT seatsSold FROM Flight WHERE flightNumber = '"+flightNumber+"'";
                PreparedStatement preStatement = conn.prepareStatement(retrieve_seats_stmt);
                ResultSet result = preStatement.executeQuery();   
                while(result.next()){
                    seatsSold = result.getInt(1);
                }
                conn.close();
            }
            catch(Exception count_e)
            {
                 System.out.println("Error at adding ticket, update seats\n"+count_e+"\n");
                 count_e.printStackTrace();
            }
            
            if(seatsSold>=10)
            {
                JOptionPane.showMessageDialog(null, "The Flight is full");
            }
            else if(!guiSelectQuantityBool){
                selectQuantityGui();
            }
            else{
                JOptionPane.showMessageDialog(null, "Only one instance of the select client window may be open");
            }
        }
        if(e.getSource()==quantityCbo)
        {//DONE
            addCenterPanelQuantity(Integer.parseInt((String)quantityCbo.getSelectedItem()));     
            
        }
        if(e.getSource() == confirmQBtn)
        {//DONE
            quantityAmount = Integer.parseInt((String)quantityCbo.getSelectedItem());
            boolean[] flag = new boolean[quantityAmount];
            boolean finalFlag = false;
            mealSelection = new int[quantityAmount][4];
            for(int i=0;i<quantityAmount;i++)
            {
                if((vegetarianRB[i].isSelected() || chickenRB[i].isSelected() || beefRB[i].isSelected()) )
                {
                    flag[i] = true;
                }
            }
            for(int i=0;i<quantityAmount;i++)
            {
                if(!flag[i])
                {
                    finalFlag = true;
                }
            }
            if(finalFlag){
                JOptionPane.showMessageDialog(null, "You need to select a meal option for each ticket");
            }
            else
            {
                //save the details to db
                jfQ.dispose();
                guiSelectQuantityBool = false;
                ticketQuantitySelected = true;
                double flightPrice = 0.00;
                getTicketPrice();
                flightPrice = ticketPrice;
              
                ticketsQuantityTTxt.setText((quantityAmount+"").toString());
                amountTTxt.setText((flightPrice*(Integer.parseInt(ticketsQuantityTTxt.getText())))+"" );
                for(int m=0;m<quantityAmount;m++)
                {
                    if(vegetarianRB[m].isSelected()){mealSelection[m][0] = 0;}else{mealSelection[m][0] = -1;}
                    if(chickenRB[m].isSelected()){mealSelection[m][1] = 0;}else{mealSelection[m][1] = -1;}
                    if(beefRB[m].isSelected()){mealSelection[m][2] = 0;}else{mealSelection[m][2] = -1;}
                    if(beverageQCB[m].isSelected()){mealSelection[m][3] = 0;}else{mealSelection[m][3] = -1;}
                }
            }
        }
        if(e.getSource() == cancelQBtn)
        {//DONE
            int result = JOptionPane.showConfirmDialog(jfC, "Are you sure you would like to close this window?");
            if( result==JOptionPane.OK_OPTION)
            {
                // NOW we change it to dispose on close..
                jfQ.setDefaultCloseOperation(jfQ.DISPOSE_ON_CLOSE);
                jfQ.setVisible(false);
                jfQ.dispose();
                guiSelectQuantityBool = false;
            }
        }
        
        if(e.getSource() == btnAddTicket)
        {
            if(!nameTTxt.getText().equals("") &&!surnameTTxt.getText().equals("") &&!amountTTxt.getText().equals("")&&ticketQuantitySelected==true)
            { 
                int ticketNumber = 0;
                try
                { 
                    String add_Ticket_Table_stmt="{call ADD_TICKET(?,?,?,?)}"+"";
                    CallableStatement callableStatement;
                    ResultSet result;
                    Connection conn;
                    conn = JavaConnectDB.connectDB();
                    try 
                    {   
                        callableStatement = conn.prepareCall(add_Ticket_Table_stmt);
                        callableStatement.registerOutParameter(1, Types.INTEGER);
                        callableStatement.setString(3, clientId);
                        callableStatement.setInt(4, quantityAmount);
                        callableStatement.setInt(2, flightNumber);
                        //callableStatement.execute(); 
                        callableStatement.executeUpdate();
                        ticketNumber = callableStatement.getInt(1);
                        
                    }
                    catch(Exception ee )
                    {
                        System.out.println("Error: "+ee);
                        ee.printStackTrace();
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
                //END OF ADD TICKET
                //ADD MEALS
                for(int i=0;i<quantityAmount;i++)
                {
                    try
                    { //mealSelection
                        String add_Meal_Table_stmt="{call ADD_MEALS(?,?,?,?,?)}"+"";
                        CallableStatement callableStatement;
                        ResultSet result;
                        Connection conn;
                        conn = JavaConnectDB.connectDB();
                        try 
                        {   
                            callableStatement = conn.prepareCall(add_Meal_Table_stmt);
                            callableStatement.setInt(1, ticketNumber);
                            callableStatement.setInt(2, mealSelection[i][0]);
                            callableStatement.setInt(3, mealSelection[i][1]);
                            callableStatement.setInt(4, mealSelection[i][2]);
                            callableStatement.setInt(5, mealSelection[i][3]);                            
                            callableStatement.execute();  
                        }
                        catch(Exception ee )
                        {
                            System.out.println("Error: "+ee);
                            ee.printStackTrace();
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
                //Update seats available
                int seatsSold = 0;
                int seatsAvail = 0;
                try
                {
                    Connection conn = JavaConnectDB.connectDB();
                    String retrieve_seats_stmt = "SELECT seatsSold, seatsAvailable FROM Flight WHERE flightNumber = '"+flightNumber+"'";
                    PreparedStatement preStatement = conn.prepareStatement(retrieve_seats_stmt);
                    ResultSet result = preStatement.executeQuery();   
                    while(result.next()){
                        seatsSold = result.getInt(1);
                        seatsAvail = result.getInt(2);
                    }
                    conn.close();
                }
                catch(Exception count_e)
                {
                     System.out.println("Error at adding ticket, update seats\n"+count_e+"\n");
                     count_e.printStackTrace();
                }
                try
                {
                    System.out.println("Amount seats sold: "+ seatsSold+ ", and adding "+quantityAmount);
                    Connection conn = JavaConnectDB.connectDB();
                    
                    seatsSold = seatsSold + quantityAmount;
                    seatsAvail = seatsAvail - quantityAmount;
                    countSeatsTaken = seatsSold;
                    //"UPDATE Flight"+" SET seatSold = "+seatsBooked+",seatsAvailable = "+seatsAvail+ " WHERE flightNumber = "+flightNum+
                    String update_seats_stmt = "UPDATE Flight set seatsSold = '"+ seatsSold +"', seatsavailable = '" + seatsAvail +"'  WHERE flightNumber = '"+flightNumber+ "'   ";

                    CallableStatement callableStatement =  conn.prepareCall(update_seats_stmt);
                    callableStatement.execute();   
                    conn.close();
                }
                catch(Exception count_e)
                {
                     System.out.println("Error at adding ticket, update seats\n"+count_e+"\n");
                     count_e.printStackTrace();
                }
                //Refresh Ticket Gui
                System.out.println("Ticket Added - Refreshed Ticket GUI");
                retrieveAllTickets(flightNumber);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Please fill in all the fields");
            }  
        }
        for(int i=0;i<rowsT;i++)
        {
            if(e.getSource() == deleteTBtn[i])
            {
                //create procedure to delete (specific ticket)
                
                int guiResult = JOptionPane.showConfirmDialog(jfC, "Are you sure you would like to delete this ticket?\nAll the meals associated to this ticket will also be deleted!");
                if( guiResult==JOptionPane.OK_OPTION)
                {
                    // NOW we change it to dispose on close..
                    
                
                
                    //Update amount of available tickets
                    //Update amount of sold tickets
                    //Update Flight gui
                    int seatsSold = 0;
                    int seatsAvail = 0;
                    try
                    {
                        Connection conn = JavaConnectDB.connectDB();
                        String retrieve_seats_stmt = "SELECT seatsSold, seatsAvailable FROM Flight WHERE flightNumber = '"+flightNumber+"'";
                        PreparedStatement preStatement = conn.prepareStatement(retrieve_seats_stmt);
                        ResultSet result = preStatement.executeQuery();   
                        while(result.next()){
                            seatsSold = result.getInt(1);
                            seatsAvail = result.getInt(2);
                        }
                        conn.close();
                    }
                    catch(Exception count_e)
                    {
                         System.out.println("Error at deleting ticket, update seats\n"+count_e+"\n");
                         count_e.printStackTrace();
                    }
                    try
                    {

                        Connection conn = JavaConnectDB.connectDB();

                        seatsSold = seatsSold - Integer.parseInt(dataTTxt[i][3].getText());
                        seatsAvail = seatsAvail + Integer.parseInt(dataTTxt[i][3].getText());
                        System.out.println("Delete seats, now available: "+seatsAvail+" and the sold amount is: "+seatsSold);
                        //"UPDATE Flight"+" SET seatSold = "+seatsBooked+",seatsAvailable = "+seatsAvail+ " WHERE flightNumber = "+flightNum+
                        String update_seats_stmt = "UPDATE Flight set seatsSold = '"+ seatsSold +"', seatsavailable = '" + seatsAvail +"'  WHERE flightNumber = '"+flightNumber+ "'   ";
                        countSeatsTaken = seatsSold;
                        CallableStatement callableStatement =  conn.prepareCall(update_seats_stmt);
                        callableStatement.execute();   
                        conn.close();
                    }
                    catch(Exception count_e)
                    {
                         System.out.println("Error delete ticket, update seats\n"+count_e+"\n");
                         count_e.printStackTrace();
                    }
                    //Refresh Ticket Gui
                    System.out.println("Ticket Deleter - Refreshed Ticket GUI");

                    try
                    {

                        Connection conn4 = JavaConnectDB.connectDB(); 
                        //"UPDATE Flight"+" SET seatSold = "+seatsBooked+",seatsAvailable = "+seatsAvail+ " WHERE flightNumber = "+flightNum+
                        String delete_receipt_stmt = "DELETE FROM RECEIPT WHERE ticketNumber = '"+ dataTTxt[i][0].getText() +"'  ";

                        CallableStatement callableStatement =  conn4.prepareCall(delete_receipt_stmt);
                        callableStatement.execute();   
                        conn4.close();
                    }
                    catch(Exception count_e)
                            {
                                 System.out.println("Error at deleting  meal\n"+count_e+"\n");
                                 count_e.printStackTrace();
                            }
                    //DELETE MEAL
                    try
                    {

                        Connection conn = JavaConnectDB.connectDB(); 
                        //"UPDATE Flight"+" SET seatSold = "+seatsBooked+",seatsAvailable = "+seatsAvail+ " WHERE flightNumber = "+flightNum+
                        String delete_meal_stmt = "DELETE FROM MEAL WHERE ticketNumber = '"+dataTTxt[i][0].getText() +"'  ";

                        CallableStatement callableStatement =  conn.prepareCall(delete_meal_stmt);
                        callableStatement.execute();   
                        conn.close();
                    }
                    catch(Exception count_e)
                    {
                         System.out.println("Error at deleting  meal\n"+count_e+"\n");
                         count_e.printStackTrace();
                    }
                    try
                    {

                        Connection conn = JavaConnectDB.connectDB(); 
                        //"UPDATE Flight"+" SET seatSold = "+seatsBooked+",seatsAvailable = "+seatsAvail+ " WHERE flightNumber = "+flightNum+
                        String delete_ticket_stmt = "DELETE FROM TICKET  WHERE flightNumber = '"+flightNumber+ "' AND ticketNumber = '"+dataTTxt[i][0].getText() +"'  ";

                        CallableStatement callableStatement =  conn.prepareCall(delete_ticket_stmt);
                        callableStatement.execute();   
                        conn.close();
                    }
                    catch(Exception count_e)
                    {
                         System.out.println("Error at deleting ticket, update seats\n"+count_e+"\n");
                         count_e.printStackTrace();
                    }
                    //what happens with any receipts?
                    retrieveAllTickets(flightNumber);
                }
            }
        }
        
        for(int i=0;i<rowsT;i++)
        {
            if(e.getSource() == paymentTBtn[i])
            {
                //Create gui for receipt
            }
        }
        //saveChangesTBtn close flight management
        if(e.getSource() == saveChangesTBtn)
        {
            int result = JOptionPane.showConfirmDialog(jfT, "Are you sure you would like to close this window?");
            if( result==JOptionPane.OK_OPTION)
            {
                // NOW we change it to dispose on close..
                jfT.setDefaultCloseOperation(jfT.DISPOSE_ON_CLOSE);
                jfT.setVisible(false);
                jfT.dispose();
                guiCreatedClientBool = false;
            }
        }
        //cancelFlightTBtn,
        if(e.getSource() == cancelFlightTBtn)
        {
            //Get current status
            getCancelled();
            
            int cancelledNum =0;
            if(flightCancelled)
            {
                cancelledNum = 0;
            }
            else
            {
                cancelledNum = -1;
            }
            //Update cancelled to the other
            try
                {
                    
                    Connection conn = JavaConnectDB.connectDB();
                    
                    //"UPDATE Flight"+" SET seatSold = "+seatsBooked+",seatsAvailable = "+seatsAvail+ " WHERE flightNumber = "+flightNum+
                    String update_cancelled_stmt = "UPDATE Flight set cancelled = '"+ cancelledNum +"'  WHERE flightNumber = '"+flightNumber+ "'   ";

                    CallableStatement callableStatement =  conn.prepareCall(update_cancelled_stmt);
                    callableStatement.execute();   
                    conn.close();
                }
                catch(Exception count_e)
                {
                     System.out.println("Error at changing status\n"+count_e+"\n");
                     count_e.printStackTrace();
                }
            //refresh Ticket
            retrieveAllTickets(flightNumber);
        }
        
        //deleteAllTicketsTBtn
        if(e.getSource() == deleteAllTicketsTBtn)
        {
            int guiResult = JOptionPane.showConfirmDialog(jfC, "Are you sure you would like to delete All the tickets in this flight?\nAll the meals associated to these tickets will also be deleted!");
            if( guiResult==JOptionPane.OK_OPTION)
            {
                try
                { 
                    String retrieve_ticket_with_flightnum_Table_stmt="SELECT ticketNumber from TICKET WHERE flightNumber = '"+ flightNumber+"' "+"";

                    Connection conn;
                    conn = JavaConnectDB.connectDB();
                    try 
                    {  
                        PreparedStatement preStatement = conn.prepareCall(retrieve_ticket_with_flightnum_Table_stmt);
                        ResultSet result = preStatement.executeQuery();   
                        while(result.next())
                        {
                            int currTicketNumber = result.getInt(1);
                            try
                            {

                                Connection conn4 = JavaConnectDB.connectDB(); 
                                //"UPDATE Flight"+" SET seatSold = "+seatsBooked+",seatsAvailable = "+seatsAvail+ " WHERE flightNumber = "+flightNum+
                                String delete_receipt_stmt = "DELETE FROM RECEIPT WHERE ticketNumber = '"+ currTicketNumber +"'  ";

                                CallableStatement callableStatement =  conn4.prepareCall(delete_receipt_stmt);
                                callableStatement.execute();   
                                conn4.close();
                            }
                            catch(Exception count_e)
                            {
                                 System.out.println("Error at deleting  meal\n"+count_e+"\n");
                                 count_e.printStackTrace();
                            }
                            try
                            {

                                Connection conn2 = JavaConnectDB.connectDB(); 
                                //"UPDATE Flight"+" SET seatSold = "+seatsBooked+",seatsAvailable = "+seatsAvail+ " WHERE flightNumber = "+flightNum+
                                String delete_meal_stmt = "DELETE FROM MEAL WHERE ticketNumber = '"+ currTicketNumber +"'  ";

                                CallableStatement callableStatement =  conn2.prepareCall(delete_meal_stmt);
                                callableStatement.execute();   
                                conn2.close();
                            }
                            catch(Exception count_e)
                            {
                                 System.out.println("Error at deleting  meal\n"+count_e+"\n");
                                 count_e.printStackTrace();
                            }
                            try
                            {
                                Connection conn3 = JavaConnectDB.connectDB(); 
                                //"UPDATE Flight"+" SET seatSold = "+seatsBooked+",seatsAvailable = "+seatsAvail+ " WHERE flightNumber = "+flightNum+
                                String delete_ticket_stmt = "DELETE FROM TICKET  WHERE flightNumber = '"+flightNumber+ "' AND ticketNumber = '"+currTicketNumber+"'  ";

                                CallableStatement callableStatement =  conn3.prepareCall(delete_ticket_stmt);
                                callableStatement.execute();   
                                conn3.close();
                            }
                            catch(Exception count_e)
                            {
                                 System.out.println("Error at deleting ticket, update seats\n"+count_e+"\n");
                                 count_e.printStackTrace();
                            }
                        }

                    }
                    catch(Exception ee )
                    {
                        System.out.println("Error delete all tickets: "+ee);
                        ee.printStackTrace();
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
                    System.out.println("DELETE ALL TICKETS: " + err);
                }
                //select all tickets in flight
                        //delete all meals with that ticket number
                        //delete that ticket    

                //Reset amount of available tickets
                //Reset amount of sold tickets

                //Update Ticket gui       
                try
                {
                    Connection conn = JavaConnectDB.connectDB();
                    int seatsSold = 0;
                    int seatsAvail = 10;
                    //"UPDATE Flight"+" SET seatSold = "+seatsBooked+",seatsAvailable = "+seatsAvail+ " WHERE flightNumber = "+flightNum+
                    String update_seats_stmt = "UPDATE Flight set seatsSold = '"+ seatsSold +"', seatsavailable = '" + seatsAvail +"'  WHERE flightNumber = '"+flightNumber+ "'   ";

                    CallableStatement callableStatement =  conn.prepareCall(update_seats_stmt);
                    callableStatement.execute();   
                    conn.close();
                }
                catch(Exception count_e)
                {
                     System.out.println("Error delete ticket, update seats\n"+count_e+"\n");
                     count_e.printStackTrace();
                }
                retrieveAllTickets(flightNumber);
            }
        }
        //deleteFlightTBtn
        if(e.getSource() == deleteFlightTBtn)
        {
            //delete flight and all tickets
            int guiResult = JOptionPane.showConfirmDialog(jfC, "Are you sure you would like to delete this flight, \nall the tickets in the flight, \nand the meal preferences associated to the tickets?");
            if( guiResult==JOptionPane.OK_OPTION)
            {
                try
                { 
                    String retrieve_ticket_with_flightnum_Table_stmt="SELECT ticketNumber from TICKET WHERE flightNumber = '"+ flightNumber+"' "+"";

                    Connection conn;
                    conn = JavaConnectDB.connectDB();
                    try 
                    {  
                        PreparedStatement preStatement = conn.prepareCall(retrieve_ticket_with_flightnum_Table_stmt);
                        ResultSet result = preStatement.executeQuery();   
                        while(result.next())
                        {
                            int currTicketNumber = result.getInt(1);
                            try
                            {

                                Connection conn4 = JavaConnectDB.connectDB(); 
                                //"UPDATE Flight"+" SET seatSold = "+seatsBooked+",seatsAvailable = "+seatsAvail+ " WHERE flightNumber = "+flightNum+
                                String delete_receipt_stmt = "DELETE FROM RECEIPT WHERE ticketNumber = '"+ currTicketNumber +"'  ";

                                CallableStatement callableStatement =  conn4.prepareCall(delete_receipt_stmt);
                                callableStatement.execute();   
                                conn4.close();
                            }
                            catch(Exception count_e)
                            {
                                 System.out.println("Error at deleting  receipt\n"+count_e+"\n");
                                 count_e.printStackTrace();
                            }
                            try
                            {

                                Connection conn2 = JavaConnectDB.connectDB(); 
                                //"UPDATE Flight"+" SET seatSold = "+seatsBooked+",seatsAvailable = "+seatsAvail+ " WHERE flightNumber = "+flightNum+
                                String delete_meal_stmt = "DELETE FROM MEAL WHERE ticketNumber = '"+ currTicketNumber +"'  ";

                                CallableStatement callableStatement =  conn2.prepareCall(delete_meal_stmt);
                                callableStatement.execute();   
                                conn2.close();
                            }
                            catch(Exception count_e)
                            {
                                 System.out.println("Error at deleting  meal\n"+count_e+"\n");
                                 count_e.printStackTrace();
                            }
                            try
                            {

                                Connection conn3 = JavaConnectDB.connectDB(); 
                                //"UPDATE Flight"+" SET seatSold = "+seatsBooked+",seatsAvailable = "+seatsAvail+ " WHERE flightNumber = "+flightNum+
                                String delete_ticket_stmt = "DELETE FROM TICKET  WHERE flightNumber = '"+flightNumber+ "' AND ticketNumber = '"+currTicketNumber+"'  ";

                                CallableStatement callableStatement =  conn3.prepareCall(delete_ticket_stmt);
                                callableStatement.execute();   
                                conn3.close();
                            }
                            catch(Exception count_e)
                            {
                                 System.out.println("Error at deleting ticket, update seats\n"+count_e+"\n");
                                 count_e.printStackTrace();
                            }
                        }
                        try
                        {

                            Connection conn3 = JavaConnectDB.connectDB(); 
                            //"UPDATE Flight"+" SET seatSold = "+seatsBooked+",seatsAvailable = "+seatsAvail+ " WHERE flightNumber = "+flightNum+
                            String delete_flight_stmt = "DELETE FROM FLIGHT WHERE flightNumber = '"+flightNumber+ "'  ";

                            CallableStatement callableStatement =  conn3.prepareCall(delete_flight_stmt);
                            callableStatement.execute();   
                            conn3.close();
                        }
                        catch(Exception count_e)
                        {
                             System.out.println("Error at deleting flight\n"+count_e+"\n");
                             count_e.printStackTrace();
                        }

                    }
                    catch(Exception ee )
                    {
                        System.out.println("Error delete flight: "+ee);
                        ee.printStackTrace();
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
                    System.out.println("DELETE FLIGHT: " + err);
                }
                jfT.dispose();
            //close ticket
            }
        }
        for(int i=0;i<rowsT;i++)
        {            
            if(e.getSource()== paymentTBtn[i])
            {
                if(guiMakePaymentBool)
                {
                    JOptionPane.showMessageDialog(null, "Please close all the payment windows before opening another");
                }
                else{
                    makePaymentGui(i);
                }
                
                
            }
        }
        if(e.getSource() == cancelPBtn)
        {
            int result = JOptionPane.showConfirmDialog(jfP, "Are you sure you would like to close this window?");
            if( result==JOptionPane.OK_OPTION)
            {
                // NOW we change it to dispose on close..
                jfP.setDefaultCloseOperation(jfP.DISPOSE_ON_CLOSE);
                jfP.setVisible(false);
                jfP.dispose();
                //guiSelectQuantityBool = false;
                guiMakePaymentBool = false;
            }
        }
        if(e.getSource() == payPBtn)
        {
            try
            {
                double paymentAmount = Double.parseDouble(amountPTxt.getText());
                if((totalDue-paymentAmount)<0) 
                {
                    JOptionPane.showMessageDialog(null, "Please enter a value less than the total due");
                }
                else
                {
                   int resultQuestion = JOptionPane.showConfirmDialog(jfP, "Are you sure you would like to make a payment of R"+paymentAmount+"?");
            
                    if(resultQuestion==JOptionPane.OK_OPTION)
                    {
                       String add_Receipt_Table_stmt="{call ADD_RECEIPT(?,?,?,?)}"+"";
                        CallableStatement callableStatement;
                        ResultSet result;
                        Connection conn;
                        conn = JavaConnectDB.connectDB();
                        try 
                        {   
                            callableStatement = conn.prepareCall(add_Receipt_Table_stmt);

                            callableStatement.setString(1, "");
                            callableStatement.setDouble(2, paymentAmount);
                            callableStatement.setInt(3, Integer.parseInt(ticketId));
                            callableStatement.setInt(4, Integer.parseInt(clientID));
                            //callableStatement.execute(); 
                            callableStatement.executeUpdate();
                            //ticketNumber = callableStatement.getInt(1);

                        }
                        catch(Exception ee )
                        {
                            System.out.println("Error: "+ee);
                            ee.printStackTrace();
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

                        JOptionPane.showMessageDialog(null, "Payment successfully processed"  );
                        guiMakePaymentBool = false;
                        jfP.dispose(); 
                    } 
                }
                
            }
            catch(NumberFormatException numE)
            {
                JOptionPane.showMessageDialog(null, "Please enter a numeric value for the payment amount");
            }
            catch(Exception ee)
            {
                JOptionPane.showMessageDialog(null, "Please check the amount entered.");
            }
            
            //update amount payed
            //create invoice
            //OptionPane.showMessageDialog(null, "The payment will go through when there is coding to allow it to go through. nuff said");
            //amount paid, date of payment
        }
        if(e.getSource() == createCbtn)
        {
            //ADD_Passenger
          //CREATE CLIENT  
            //dataCTxt[amountClients][i]
            String newClientName = dataCTxt[amountClients][1].getText();
            String newClientSurname =  dataCTxt[amountClients][2].getText();
            String newClientContact = dataCTxt[amountClients][3].getText();
            if(newClientName !=null && newClientSurname !=null && newClientContact !=null)
            {
                int resultQuestion = JOptionPane.showConfirmDialog(jfC, "Are you sure you would like to add "+newClientName+ " " +newClientSurname+ " with contact number "+newClientContact + " to the database?");
            
                if(resultQuestion==JOptionPane.OK_OPTION)
                {
                    try
                    { 
                        String add_Ticket_Table_stmt="{call ADD_Passenger(?,?,?)}"+"";
                        CallableStatement callableStatement;
                        ResultSet result;
                        Connection conn;
                        conn = JavaConnectDB.connectDB();
                        try 
                        {   
                            callableStatement = conn.prepareCall(add_Ticket_Table_stmt);
                            callableStatement.setString(1, newClientName);
                            callableStatement.setString(2, newClientSurname);
                            callableStatement.setString(3, newClientContact);
                            callableStatement.executeUpdate();                        
                        }
                        catch(Exception ee )
                        {
                            System.out.println("Error: "+ee);
                            ee.printStackTrace();
                        }
                        finally
                        {
                            try 
                            {
                                conn.close();
                                JOptionPane.showMessageDialog(null, "Client added.");
                                jfC.dispose();
                                getAllClients();
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
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Please enter valid information in all the required textboxes.");
            }
            
          //UPDATE CLIENT LIST
        }
        for(int i=0;i<amountClients;i++){
            if(e.getSource()==updateCBtn[i])
            {
                if(updateCBtn[i].getText().equals("Save"))
                {
                    if(dataCTxt[i][1].getText()!=null &&dataCTxt[i][2].getText()!=null && dataCTxt[i][3].getText()!=null)
                    {
                        try
                        {
                            Connection conn = JavaConnectDB.connectDB();
                            //"UPDATE Flight"+" SET seatSold = "+seatsBooked+",seatsAvailable = "+seatsAvail+ " WHERE flightNumber = "+flightNum+
                            String update_seats_stmt = "UPDATE Passenger set pname = '"+ dataCTxt[i][1].getText() +"', psurname = '" + dataCTxt[i][2].getText() +"', contactNumber = '"+dataCTxt[i][3].getText()+"'  WHERE passengerID = '"+dataCTxt[i][0].getText()+ "'   ";

                            CallableStatement callableStatement =  conn.prepareCall(update_seats_stmt);
                            callableStatement.execute();   
                            conn.close();
                            JOptionPane.showMessageDialog(null, "Updated passenger successfully");
                        }
                        catch(Exception count_e)
                        {
                            JOptionPane.showMessageDialog(null, "A error occured");
                             System.out.println("Error updating client\n"+count_e+"\n");
                             count_e.printStackTrace();
                        }

                        dataCTxt[i][1].setEditable(false);
                        dataCTxt[i][2].setEditable(false);
                        dataCTxt[i][3].setEditable(false);
                        updateCBtn[i].setText("Update");
                        jfC.validate();                
                        jfC.repaint();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Please fill in valid values");
                    }
                    
                }
                else
                {
                    dataCTxt[i][1].setEditable(true);
                    dataCTxt[i][2].setEditable(true);
                    dataCTxt[i][3].setEditable(true);
                    updateCBtn[i].setText("Save");
                    jfC.validate();                
                    jfC.repaint();
                }
            }
            
        }
        for(int i=0;i<amountClients;i++)
        {//scrapping this function because needs to update each flight quantities
            /*
            if(e.getSource()==deleteCBtn[i])
            {
                String clientid = dataCTxt[i][0].getText();
                //delete all meals with client
                //delete all receipts with client
                //delete all tickets with client
                int resultQ = JOptionPane.showConfirmDialog(jfC, "Are you sure you want to delete this client?");
                if( resultQ==JOptionPane.OK_OPTION)
                {
                    try
                    { 
                        String retrieve_ticket_with_clientid_Table_stmt="SELECT ticketNumber from TICKET WHERE passengerid = '"+ clientid+"' "+"";

                        Connection conn;
                        conn = JavaConnectDB.connectDB();
                        try 
                        {  
                            PreparedStatement preStatement = conn.prepareCall(retrieve_ticket_with_clientid_Table_stmt);
                            ResultSet result = preStatement.executeQuery();   
                            while(result.next())
                            {
                                int tickeNum = result.getInt(1);
                                try
                                {

                                    Connection conn4 = JavaConnectDB.connectDB(); 
                                    //"UPDATE Flight"+" SET seatSold = "+seatsBooked+",seatsAvailable = "+seatsAvail+ " WHERE flightNumber = "+flightNum+
                                    String delete_receipt_stmt = "DELETE FROM RECEIPT WHERE ticketNumber = '"+ tickeNum +"'  ";

                                    CallableStatement callableStatement =  conn4.prepareCall(delete_receipt_stmt);
                                    callableStatement.execute();   
                                    conn4.close();
                                }
                                catch(Exception count_e)
                                {
                                     System.out.println("Error at deleting Client receipt\n"+count_e+"\n");
                                     count_e.printStackTrace();
                                }
                                try
                                {

                                    Connection conn2 = JavaConnectDB.connectDB(); 
                                    //"UPDATE Flight"+" SET seatSold = "+seatsBooked+",seatsAvailable = "+seatsAvail+ " WHERE flightNumber = "+flightNum+
                                    String delete_meal_stmt = "DELETE FROM MEAL WHERE ticketNumber = '"+ tickeNum +"'  ";

                                    CallableStatement callableStatement =  conn2.prepareCall(delete_meal_stmt);
                                    callableStatement.execute();   
                                    conn2.close();
                                }
                                catch(Exception count_e)
                                {
                                     System.out.println("Error at deleting Client meal\n"+count_e+"\n");
                                     count_e.printStackTrace();
                                }
                            }
                            try
                            {

                                Connection conn3 = JavaConnectDB.connectDB(); 
                                //"UPDATE Flight"+" SET seatSold = "+seatsBooked+",seatsAvailable = "+seatsAvail+ " WHERE flightNumber = "+flightNum+
                                String delete_flight_stmt = "DELETE FROM Passenger WHERE passengerid = '"+clientid+ "'  ";

                                CallableStatement callableStatement =  conn3.prepareCall(delete_flight_stmt);
                                callableStatement.execute();   
                                conn3.close();
                            }
                            catch(Exception count_e)
                            {
                                 System.out.println("Error at deleting Client\n"+count_e+"\n");
                                 count_e.printStackTrace();
                            }

                        }
                        catch(Exception ee )
                        {
                            System.out.println("Error delete Client: "+ee);
                            ee.printStackTrace();
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
                        System.out.println("DELETE Client: " + err);
                    }
                }
            }
       */ }
        
    }
   
}
