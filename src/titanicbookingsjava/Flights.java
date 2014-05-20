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
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import oracle.jdbc.OracleTypes;

/**
 *
 * @author foosh
 */
public class Flights implements ActionListener
{
    private JMenuItem showAll,fromCt,fromDbn,fromPe,fromG,fromS,fromL,fromNy,fromPa,fromA,fromJ;
    private JMenu helpmenu;
    private JMenuItem exitAction;
    private JMenuItem bthelp;    
    
     //FLIGHT GUI
    private JFrame jf;
    private JPanel topPanel, centerPanel, bottomPanel;
    //FLIGHT TOP PANEL
    private JLabel flightLbl, dateLbl, fromLbl, toLbl, seatsLbl, openLbl;
    public JTextField[][] dataTxt;
    private JButton[] openBtn;
    private int rows;
    private JScrollPane scroll;
    private Double priceForFlight;
    
    //FLIGHT CENTER PANEL
    private String[] yearCboItems, monthCboItems, fromCboItems, toCboItems;
    private JComboBox<String> yearCbo, monthCbo, dayCbo, fromCbo, toCbo;   
    private JButton addFlight;
    private JLabel yearLbl, monthLbl, dayLbl, fromLbl2, toLbl2, addLbl;
    private boolean yearSelected, monthSelected, daySelected, fromSelected, toSelected;
    
    
    //BOTTOM PANEL
    private JButton backBtn, saveBtn;
    
    //FLIGHT VARIABLES
    int amountFlights;
    private boolean guiCreatedBool;
    private boolean filterCity;
    private String cityToFilter;    
    private int currentActiveflightNumber;
    
    //TICKET VARIABLES
    private boolean ticketQuantitySelected;    
    private boolean guiCreatedTicketBool;
    
    //-----
    private boolean debuggingFlag; 
    
    public Flights(){
         guiCreatedBool = false;
         guiCreatedTicketBool = false;
         filterCity = false;
         debuggingFlag = true; 
    }
    
    public void createFlightGui()
    {
        
        jf = new JFrame("Flight Management");
        JMenuBar menuBar = new JMenuBar();    
        jf.setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Select Departing Location");
        menuBar.add(fileMenu);
        JMenu helpMenu = new JMenu("File");
        menuBar.add(helpMenu);
        //fromCt, fromDbn,fromPe,fromG,fromS,fromL,fromNy,fromPa,fromA,fromJ;
       //"Cape Town", "Durban", "Port Elizabeth", "George", "Sydney", "London", "New York", "Paris", "Amsterdam"
        fromCt = new JMenuItem("Cape Town");
        fromCt.addActionListener(this);
        fromDbn = new JMenuItem("Durban");
        fromDbn.addActionListener(this);
        fromPe = new JMenuItem("Port Elizabeth");
        fromPe.addActionListener(this);
        fromG = new JMenuItem("George");
        fromG.addActionListener(this);
        fromS = new JMenuItem("Sydney");
        fromS.addActionListener(this);
        fromL = new JMenuItem("London");
        fromL.addActionListener(this);
        fromNy = new JMenuItem("New York");
        fromNy.addActionListener(this);
        fromPa = new JMenuItem("Paris");
        fromPa.addActionListener(this);
        fromA = new JMenuItem("Amsterdam");
        fromA.addActionListener(this);
        fromJ = new JMenuItem("Johannesburg");
        fromJ.addActionListener(this);
        showAll = new JMenuItem("Show All Flights");
        showAll.addActionListener(this);
        exitAction =  new JMenuItem("Exit");
        exitAction.addActionListener(this);
        bthelp = new JMenuItem("Help");
        bthelp.addActionListener(this);
       
        fileMenu.add(showAll);
        fileMenu.addSeparator();
        fileMenu.add(fromCt);
        fileMenu.add(fromDbn);
        fileMenu.add(fromPe);
        fileMenu.add(fromG);
        fileMenu.add(fromS);
        fileMenu.add(fromL);
        fileMenu.add(fromNy);
        fileMenu.add(fromPa);
        fileMenu.add(fromA);
        fileMenu.add(fromJ);
        helpMenu.add(bthelp);
        helpMenu.addSeparator();
        helpMenu.add(exitAction);
        
        guiCreatedBool = true;
        
        
        topPanel = new JPanel(new GridLayout(rows+1, 6)); 
        
        centerPanel = new JPanel(new GridLayout(3,6));
        bottomPanel = new JPanel(new GridLayout(1,2));
        //TOP
        //addDataPanel(2);
        
        String[] yearCboItems = {"Select Year", "2013", "2014", "2015"};        
        String[] monthCboItems = { "Select Year"};    
        String[] dayCboItems = {"Select Month"};        
        String[] fromCboItems = {"Select Departing", "Cape Town", "Johannesburg", "Durban", "Port Elizabeth", "George", "Sydney", "London", "New York", "Paris", "Amsterdam"};
        String[] toCboItems = {"Select departing first"};
        
        //CENTER
        yearCbo = new JComboBox<>(yearCboItems);
        yearCbo.addActionListener(this);
        monthCbo = new JComboBox<>(monthCboItems);
        monthCbo.addActionListener(this);   
        dayCbo = new JComboBox<>(dayCboItems);
        dayCbo.addActionListener(this);   
        fromCbo = new JComboBox<>(fromCboItems);
        fromCbo.addActionListener(this);
        toCbo = new JComboBox<>(toCboItems);
        toCbo.addActionListener(this);
        
        addFlight = new JButton("Add Flight");
        addFlight.addActionListener(this);
        
        yearLbl = new JLabel("Year");
        monthLbl = new JLabel("Month");
        dayLbl = new JLabel("Day");
        fromLbl2 = new JLabel("Departing From");
        toLbl2 = new JLabel("Arriving At");
        addLbl = new JLabel("");
        
        for(int i=0;i<6;i++)
        {
            JLabel l1 = new JLabel("");
            l1.setOpaque(true);
            l1.setBackground(Color.GRAY);
            centerPanel.add(l1);
        }
        
        centerPanel.add(yearLbl);
        centerPanel.add(monthLbl);
        centerPanel.add(dayLbl);
        centerPanel.add(fromLbl2);
        centerPanel.add(toLbl2);
        centerPanel.add(addLbl);
        
        centerPanel.add(yearCbo);
        centerPanel.add(monthCbo);
        centerPanel.add(dayCbo);
        centerPanel.add(fromCbo);
        centerPanel.add(toCbo);
        centerPanel.add(addFlight);
        
        //BOTTOM
        backBtn = new JButton("Back");
        backBtn.addActionListener(this);
        saveBtn = new JButton("Save & Exit");
        saveBtn.addActionListener(this);
        
        bottomPanel.add(backBtn);
        bottomPanel.add(saveBtn);
        
        scroll = new JScrollPane(topPanel);
        scroll.setPreferredSize(new Dimension(800, 350));
        scroll.setVerticalScrollBarPolicy(scroll.VERTICAL_SCROLLBAR_ALWAYS);
        jf.add(scroll, BorderLayout.NORTH);;
        jf.add(centerPanel, BorderLayout.CENTER);
        jf.add(bottomPanel, BorderLayout.SOUTH);
        jf.setLocation(100,100);
        
        jf.setSize(800,510);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //DISPOSE_ON_CLOSE,  DISPOSE_ON_CLOSE 
        jf.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                int result = JOptionPane.showConfirmDialog(jf, "Are you sure you would like to exit?");
                if( result==JOptionPane.OK_OPTION)
                {
                    // NOW we change it to dispose on close..
                    jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
                    jf.setVisible(false);
                    jf.dispose();
                    guiCreatedTicketBool = false;
                }
                jf.setVisible(true);
            }
        });
    }
    
    //This method is called by the networkClient class
    public void addDataPanel(int r)
    {
        if(!guiCreatedBool)
        {
            createFlightGui();
        }
        rows = r;
        monthSelected = false;
        jf.remove(scroll);
        int difference = 10-rows;
        if(difference>0)
        {
            topPanel = new JPanel(new GridLayout(rows+1+difference, 6)); 
        }
        else
        {
            topPanel = new JPanel(new GridLayout(rows+1, 6)); 
        }
        
        flightLbl = new JLabel("Flight Number");
        dateLbl = new JLabel("Date");
        fromLbl = new JLabel("Departing From");
        toLbl = new JLabel("Arriving At");
        seatsLbl = new JLabel("Seats Available / Sold");
        openLbl = new JLabel("");
        
        topPanel.add(flightLbl);
        topPanel.add(dateLbl);
        topPanel.add(fromLbl);
        topPanel.add(toLbl);
        topPanel.add(seatsLbl);
        topPanel.add(openLbl);
                
        dataTxt = new JTextField[rows][5];
        openBtn = new JButton[rows];
        
        for(int i=0;i<rows;i++)
        {            
            for(int j=0;j<5;j++)
            {
                dataTxt[i][j] = null;
                dataTxt[i][j] = new JTextField(10);
                dataTxt[i][j].setEditable(false);
                topPanel.add(dataTxt[i][j]); 
            }
            openBtn[i] = null;
            openBtn[i] = new JButton("Open Flight");
            openBtn[i].addActionListener(this);
            topPanel.add(openBtn[i]);
        }
        
        for(int i=0;i<difference;i++)
        {
            for(int j=0;j<6;j++)
            {
                topPanel.add(new JLabel("")); //txtEmpty
            }
        }
        scroll = new JScrollPane(topPanel);
        scroll.setPreferredSize(new Dimension(800, 350));
        scroll.setVerticalScrollBarPolicy(scroll.VERTICAL_SCROLLBAR_ALWAYS);
        jf.add(scroll, BorderLayout.NORTH);
        
        jf.validate();
        jf.repaint();
    }
    
    public void addCenterPanel()
    {
        if(!guiCreatedBool)
        {
            createFlightGui();
        }
            jf.remove(centerPanel);
            centerPanel = new JPanel(new GridLayout(3,6));
            
            for(int i=0;i<6;i++)
            {
                JLabel l1 = new JLabel("");
                l1.setOpaque(true);
                l1.setBackground(Color.GRAY);
                centerPanel.add(l1);
            }
            
            centerPanel.add(yearLbl);
            centerPanel.add(monthLbl);
            centerPanel.add(dayLbl);
            centerPanel.add(fromLbl2);
            centerPanel.add(toLbl2);
            centerPanel.add(addLbl);
            
            centerPanel.add(yearCbo);
            centerPanel.add(monthCbo);
            centerPanel.add(dayCbo);
            centerPanel.add(fromCbo);
            centerPanel.add(toCbo);
            centerPanel.add(addFlight);
            
            jf.add(centerPanel, BorderLayout.CENTER);
            jf.validate();
            jf.repaint(); 
    }
    public void displayFilteredFlights()
    {
       if(!guiCreatedBool)
        {
            createFlightGui();
        }
       
        //GET THE FLIGHTS AND PUT IT IN THE GUI
        
       String select_Flight_Table_stmt="{call RETRIEVE_SELECTED_FLIGHTS(?,?,?,?,?,?,?,?,?,?)}"+"";
       
       CallableStatement callableStatement;
       ResultSet result;
       Connection conn;
        try 
        {
            conn = JavaConnectDB.connectDB();
            try 
            {   
                callableStatement = conn.prepareCall(select_Flight_Table_stmt);
                callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
                callableStatement.setString(2, cityToFilter);
                callableStatement.registerOutParameter(3, Types.INTEGER);
                callableStatement.registerOutParameter(4, Types.DATE);
                callableStatement.registerOutParameter(5, Types.VARCHAR);
                callableStatement.registerOutParameter(6, Types.VARCHAR);
                callableStatement.registerOutParameter(7, Types.INTEGER);
                callableStatement.registerOutParameter(8, Types.INTEGER);
                callableStatement.registerOutParameter(9, Types.DECIMAL);
                callableStatement.registerOutParameter(10, Types.INTEGER);
                callableStatement.execute();
                result =  (ResultSet)callableStatement.getObject(1);        
                int countRecords = 0;    
               // dataTxt = new String[amountFlights][5];
                
                while(result.next())
                {                    
                    dataTxt[countRecords][0].setText(result.getInt("flightNumber")+"");
                    dataTxt[countRecords][1].setText(result.getDate("flightDate")+"");
                    dataTxt[countRecords][2].setText(result.getString("departCity"));
                    dataTxt[countRecords][3].setText(result.getString("arriveCity"));
                    dataTxt[countRecords][4].setText(result.getInt("seatsAvailable")+" / "+result.getInt("seatsSold"));

                    if(result.getInt("cancelled") ==-1) //-1 is cancelled, 0 is not cancelled
                    {
                        for(int j=0;j<5;j++)
                        {
                            dataTxt[countRecords][j].setOpaque(true);
                            dataTxt[countRecords][j].setBackground(Color.ORANGE);
                        }                
                    }
                    countRecords++;
                }
            }
            catch(Exception e )
            {
                System.out.println("Error: "+e);
                e.printStackTrace();
            }
            finally{
            try {
                conn.close();
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
            
        }
        catch (Exception err) 
        {
            System.out.println("SEND SELECTED FLIGHTS: " + err);
        }  
    }
    public void getAmountOfFilteredFlights(String departCity){
        try
        {
            Connection conn = JavaConnectDB.connectDB();
            String count_flights_stmt = "SELECT COUNT (*) FROM FLIGHT WHERE departCity = '"+departCity+"'";
            
            PreparedStatement preStatement = conn.prepareStatement(count_flights_stmt);
            ResultSet result = preStatement.executeQuery();   
            while(result.next()){
                amountFlights = result.getInt(1);
                if(debuggingFlag){System.out.println("Amount of flights: "+amountFlights);}
            }
            
            conn.close();
        }
        catch(Exception count_e)
        {
             System.out.println("Error at count flight from database: \n"+count_e);
        }
    }
    public void getAmountOfFlights(){
        //GET AMOUNT OF FLIGHTS
        try
        {
            Connection conn = JavaConnectDB.connectDB();
            String count_flights_stmt = "SELECT COUNT (*) FROM FLIGHT";
            
            PreparedStatement preStatement = conn.prepareStatement(count_flights_stmt);
            ResultSet result = preStatement.executeQuery();   
            while(result.next()){
                amountFlights = result.getInt(1);
                if(debuggingFlag){System.out.println("Amount of flights: "+amountFlights);}
            }
            
            conn.close();
        }
        catch(Exception count_e)
        {
             System.out.println("Error at count flight from database: \n"+count_e);
        }
    }
    public void getAllFlights()
    {
        if(!guiCreatedBool)
        {
            createFlightGui();
        }
       
        //GET THE FLIGHTS AND PUT IT IN THE GUI
        
       String select_Flight_Table_stmt="{call RETRIEVE_ALL_FLIGHTS(?,?,?,?,?,?,?,?,?)}"+"";
       
       CallableStatement callableStatement;
       ResultSet result;
       Connection conn;
        try 
        {
            conn = JavaConnectDB.connectDB();
            try 
            {   
                callableStatement = conn.prepareCall(select_Flight_Table_stmt);
                callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
                callableStatement.registerOutParameter(2, Types.INTEGER);
                callableStatement.registerOutParameter(3, Types.DATE);
                callableStatement.registerOutParameter(4, Types.VARCHAR);
                callableStatement.registerOutParameter(5, Types.VARCHAR);
                callableStatement.registerOutParameter(6, Types.INTEGER);
                callableStatement.registerOutParameter(7, Types.INTEGER);
                callableStatement.registerOutParameter(8, Types.DECIMAL);
                callableStatement.registerOutParameter(9, Types.INTEGER);
                callableStatement.execute();
                result =  (ResultSet)callableStatement.getObject(1);        
                int countRecords = 0;    
               // dataTxt = new String[amountFlights][5];
                
                while(result.next())
                {
                    //if(debuggingFlag){System.out.println("Amount of records: "+countRecords);}
                    dataTxt[countRecords][0].setText(result.getInt("flightNumber")+"");
                    dataTxt[countRecords][1].setText(result.getDate("flightDate")+"");
                    dataTxt[countRecords][2].setText(result.getString("departCity"));
                    dataTxt[countRecords][3].setText(result.getString("arriveCity"));
                    dataTxt[countRecords][4].setText(result.getInt("seatsAvailable")+" / "+result.getInt("seatsSold"));

                    if(result.getInt("cancelled") ==-1) //-1 is cancelled, 0 is not cancelled
                    {
                        for(int j=0;j<5;j++)
                        {
                            dataTxt[countRecords][j].setOpaque(true);
                            dataTxt[countRecords][j].setBackground(Color.ORANGE);
                        }                
                    }
                    countRecords++;
                }
                System.out.println("done" + countRecords);
            }
            catch(Exception e )
            {
                System.out.println("Error: "+e);
                e.printStackTrace();
            }
            finally{
            try {
                conn.close();
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
            
        }
        catch (Exception err) 
        {
            System.out.println("SEND ALL FLIGHTS: " + err);
        }  
        
    }
    public void displayAllFlights()
    {
        if(!filterCity)
        {
            getAmountOfFlights();
            addDataPanel(amountFlights);
            getAllFlights();
        }
        else
        {
            getAmountOfFilteredFlights(cityToFilter);            
            addDataPanel(amountFlights);
            displayFilteredFlights();
        }        
       
        
        
        
        }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()== yearCbo)
        {
            
            switch((String)yearCbo.getSelectedItem())
            {//"Cape Town", "Johannesburg", "Durban", "Port Elizabeth", "George", "Sydney", "London", "New York", "Paris", "Amsterdam"
                case "Select Year":
                {
                    monthSelected = false;
                    String[] monthCboItems = {"Select Year"};   
                    monthCbo = new JComboBox<>(monthCboItems);
                    monthCbo.addActionListener(this); 
                    String[] dayCboItems = {"Select Month"};
                    dayCbo = new JComboBox<>(dayCboItems);
                    dayCbo.addActionListener(this);
                    yearSelected = false;
                    monthSelected = false;
                    daySelected = false;
                    break;                    
                }
                case "2013":
                case "2014":                
                case"2015":
                {
                    monthSelected = true;
                    String[] monthCboItems = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};   
                    monthCbo = new JComboBox<>(monthCboItems);
                    monthCbo.addActionListener(this);
                    yearSelected = true;
                    break;                   
                }
            }
            addCenterPanel();
        }
        if(e.getSource()== monthCbo && monthSelected)
        {
            switch((String)monthCbo.getSelectedItem())
            {
                case "January":
                case "March":
                case "May":
                case "July":
                case "August":
                case "October":
                case "December":
                {
                    String[] dayCboItems = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
                    dayCbo = new JComboBox<>(dayCboItems);
                    dayCbo.addActionListener(this);
                    monthSelected = true;break;
                }
                case "February":
                {
                    String[] dayCboItems = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28"};
                    dayCbo = new JComboBox<>(dayCboItems);
                    dayCbo.addActionListener(this);
                    monthSelected = true;break;
                }                
                case "April":                
                case "June":                
                case "September":                
                case "November":
                {
                    String[] dayCboItems = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
                    dayCbo = new JComboBox<>(dayCboItems);
                    dayCbo.addActionListener(this);
                    monthSelected = true;break;
                }
                
            }
            addCenterPanel();
        }
        if(e.getSource()== dayCbo && monthSelected)
        {            
            if(!((String)dayCbo.getSelectedItem()).equals("Select Month"))
            {
                daySelected = true;
            }
           
        }
        if(e.getSource()== toCbo)
        {
            switch((String)toCbo.getSelectedItem())
            {
                case"Cape Town": case "Johannesburg": case"Durban": case "Port Elizabeth": case"George": case"Sydney": case"London": case"New York": case"Paris": case"Amsterdam":
                {
                    toSelected = true;                    
                }                
            } 
            if(((String)toCbo.getSelectedItem()).equals("Sydney") ||((String)toCbo.getSelectedItem()).equals("London") ||((String)toCbo.getSelectedItem()).equals("New York") ||((String)toCbo.getSelectedItem()).equals("Paris") ||((String)toCbo.getSelectedItem()).equals("Amsterdam") )
            {
                if(priceForFlight==500)
                {
                    priceForFlight = 1000.0;
                }
            }
        }
        if(e.getSource()== fromCbo)
        {
            switch((String)fromCbo.getSelectedItem())
            {//"Cape Town", "Johannesburg", "Durban", "Port Elizabeth", "George", "Sydney", "London", "New York", "Paris", "Amsterdam"
                case "Select Departing":
                { 
                    String[] toCboItems = {"Select departing first"};
                    toCbo = new JComboBox<>(toCboItems);
                    toCbo.addActionListener(this);
                    fromSelected = false;
                    break;
                }               
                case "Cape Town":
                {                    
                    String[] toCboItems = {"Johannesburg", "Durban", "Port Elizabeth", "George", "Sydney", "London", "New York", "Paris", "Amsterdam"};
                    toCbo = new JComboBox<>(toCboItems);
                    toCbo.addActionListener(this);
                    fromSelected = true;
                    priceForFlight = 500.0;
                    break;
                }
                case "Johannesburg": 
                {
                    String[] toCboItems = {"Cape Town", "Durban", "Port Elizabeth", "George", "Sydney", "London", "New York", "Paris", "Amsterdam"};
                    toCbo = new JComboBox<>(toCboItems);
                    toCbo.addActionListener(this);
                    fromSelected = true;
                    priceForFlight = 500.0;
                    break;
                }
                case "Durban": 
                {
                    String[] toCboItems = {"Cape Town", "Johannesburg", "Port Elizabeth", "George"};
                    toCbo = new JComboBox<>(toCboItems);
                    toCbo.addActionListener(this);
                    fromSelected = true;
                    priceForFlight = 500.0;
                    break;
                }
                case "Port Elizabeth": 
                {
                    String[] toCboItems = {"Cape Town", "Johannesburg", "Durban", "George"};
                    toCbo = new JComboBox<>(toCboItems);
                    toCbo.addActionListener(this);
                    fromSelected = true;
                    priceForFlight = 500.0;
                    break;
                }
                case "George": 
                {
                    String[] toCboItems = {"Cape Town", "Johannesburg", "Durban", "Port Elizabeth"};
                    toCbo = new JComboBox<>(toCboItems);
                    toCbo.addActionListener(this);
                    fromSelected = true;
                    priceForFlight = 500.0;
                    break;
                }
                case "Sydney": 
                {
                    String[] toCboItems = {"Cape Town", "Johannesburg", "London", "New York", "Paris", "Amsterdam"};
                    toCbo = new JComboBox<>(toCboItems);
                    toCbo.addActionListener(this);
                    fromSelected = true;
                    priceForFlight = 1000.0;
                    break;
                }
                case "London":
                {
                    String[] toCboItems = {"Cape Town", "Johannesburg", "Sydney", "New York", "Paris", "Amsterdam"};
                    toCbo = new JComboBox<>(toCboItems);
                    toCbo.addActionListener(this);
                    fromSelected = true;
                    priceForFlight = 1000.0;
                    break;
                }
                case "New York": 
                {
                    String[] toCboItems = {"Cape Town", "Johannesburg", "Sydney", "London", "Paris", "Amsterdam"};
                    toCbo = new JComboBox<>(toCboItems);
                    toCbo.addActionListener(this);
                    fromSelected = true;
                    priceForFlight = 1000.0;
                    break;
                }
                case "Paris": 
                {
                    String[] toCboItems = {"Cape Town", "Johannesburg", "Sydney", "London", "New York", "Amsterdam"};
                    toCbo = new JComboBox<>(toCboItems);
                    toCbo.addActionListener(this);
                    fromSelected = true;
                    priceForFlight = 1000.0;
                    break;
                }
                case "Amsterdam": 
                {
                    String[] toCboItems = {"Cape Town", "Johannesburg", "Sydney", "London", "New York", "Paris"};
                    toCbo = new JComboBox<>(toCboItems);
                    toCbo.addActionListener(this);
                    fromSelected = true;
                    priceForFlight = 1000.0;
                    break;
                }
            } 
            addCenterPanel();
        }
        
        if(e.getSource()== addFlight)
        {
            if(yearSelected&& monthSelected&& daySelected&& fromSelected&& toSelected)
            {
                //JOptionPane.showMessageDialog(null, "Item may be added");
               /* try
                {              
                    out.writeObject("Add Flight");  
                    int dateVal=0;
                    if(((String)monthCbo.getSelectedItem()).equals("January"))
                    {
                        dateVal = 1;
                    }
                    else if(((String)monthCbo.getSelectedItem()).equals("February"))
                    {
                        dateVal = 2;
                    }
                    else if(((String)monthCbo.getSelectedItem()).equals("March"))
                    {
                        dateVal = 3;
                    }else if(((String)monthCbo.getSelectedItem()).equals("April"))
                    {
                        dateVal = 4;
                    }else if(((String)monthCbo.getSelectedItem()).equals("May"))
                    {
                        dateVal = 5;
                    }else if(((String)monthCbo.getSelectedItem()).equals("June"))
                    {
                        dateVal = 6;
                    }else if(((String)monthCbo.getSelectedItem()).equals("July"))
                    {
                        dateVal = 7;
                    }else if(((String)monthCbo.getSelectedItem()).equals("August"))
                    {
                        dateVal = 8;
                    }else if(((String)monthCbo.getSelectedItem()).equals("September"))
                    {
                        dateVal = 9;
                    }else if(((String)monthCbo.getSelectedItem()).equals("October"))
                    {
                        dateVal = 10;
                    }else if(((String)monthCbo.getSelectedItem()).equals("November"))
                    {
                        dateVal = 11;
                    }else if(((String)monthCbo.getSelectedItem()).equals("December"))
                    {
                        dateVal = 12;
                    }
                    String dateString = (String)dayCbo.getSelectedItem()+"/"+dateVal+"/"+(String)yearCbo.getSelectedItem();
                    out.writeObject(dateString+"");  
                    out.writeObject((String)fromCbo.getSelectedItem());
                    out.writeObject((String)toCbo.getSelectedItem());
                    out.writeObject(priceForFlight+"");
                    out.flush();
                    //System.out.println("SERVER: >> "+(String)in.readObject()); 
                    
                    //retrieve all flights
                    
                    
                }
                catch(Exception ee)
                {
                   System.out.println("Request Flight and write to GUI1: " + ee);
                }
                displayAllFlights();*/
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Please select all the dropdown boxes to add a valid item");
            }
        }
        for(int j=0;j<rows;j++)
        {
            if(e.getSource()==openBtn[j])
            {
                if(guiCreatedTicketBool == true)
                {
                    JOptionPane.showMessageDialog(null, "Please close all open flights, to open another.");
                }
                else
                {
                    currentActiveflightNumber = Integer.parseInt(dataTxt[j][0].getText());
                    //------------------------------------------------------------------------------------------------------------------------------
                    //CALL TICKET CLASS
                    //retrieveAllTickets(currentActiveflightNumber);
                    ticketQuantitySelected = false;
                }
                //not send all flights, get all flights... 
                
            }
        }
        if(e.getSource()==showAll)
        {
            filterCity = false;
            displayAllFlights();
            //showAll,fromCt, fromDbn,fromPe,fromG,fromS,fromL,fromNy,fromPa,fromA,fromJ;
            //"Cape Town", "Durban", "Port Elizabeth", "George", "Sydney", "London", "New York", "Paris", "Amsterdam"
        }
        if(e.getSource()==fromCt)
        {
            filterCity = true;
            cityToFilter = "Cape Town";
            displayAllFlights();
        }
        if(e.getSource()==fromDbn)
        {
            filterCity = true;
            cityToFilter = "Durban";
            displayAllFlights();
        }
        if(e.getSource()==fromPe)
        {
            filterCity = true;
            cityToFilter = "Port Elizabeth";
            displayAllFlights();
        }
        if(e.getSource()==fromG)
        {
            filterCity = true;            
            cityToFilter = "George";
            displayAllFlights();
        }
        if(e.getSource()==fromS)
        {
            filterCity = true;
            cityToFilter = "Sydney";
            displayAllFlights();
        }
        if(e.getSource()==fromL)
        {
            filterCity = true;
            cityToFilter = "London";
            displayAllFlights();
        }
        if(e.getSource()==fromNy)
        {
            filterCity = true;
            cityToFilter = "New York";
            displayAllFlights();
        }
        if(e.getSource()==fromPa)
        {
            filterCity = true;
            cityToFilter = "Paris";
            displayAllFlights();
        }
        if(e.getSource()==fromA)
        {
            filterCity = true;
            cityToFilter = "Amsterdam";
            displayAllFlights();
        }
        if(e.getSource()==fromJ)
        {
            filterCity = true;
            cityToFilter = "Johannesburg";
            displayAllFlights();
        }
        if(e.getSource() == exitAction)
        {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to exit?","Warning",dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
        }
        if(e.getSource() == bthelp)
        {
            JFrame jfhelp = new JFrame("Help");
            JTextArea txt = new JTextArea(30,15);
            txt.setWrapStyleWord(true); 
            txt.setText("~~~~~~~~~ TITANIC BOOKINGS HELP FILE ~~~~~~~~~\n");
            txt.append("FLIGHTS - A user can view, create, delete and cancel flights.\n");
            txt.append("-ADD FLIGHT: To add a flight, select the dropdown menus at the bottom\n");
            txt.append("of the main window, once all have been selected click on 'Add Flight'\n\n");
            txt.append("-DELETE FLIGHT: To delete a flight, click on the 'Open Flight' button next\n");
            txt.append("to the flight. Once the manage flight window has opened, click on \n");
            txt.append("'Delete Flight & Tickets' to delete the flight with it's accompanying tickets\n\n");
            txt.append("-CANCEL FLIGHT: To cancel a flight, click on the 'Open Flight' button next\n");
            txt.append("to the flight. Once the manage flight window has opened, click on\n");
            txt.append("'Cancel/Enable flight' to cancel or re-enable the flight with it's \n");
            txt.append("accompanying tickets. (A Flight and it's tickets will have a orange\n");
            txt.append("background when it has been cancelled)\n\n");
            txt.append("-----------------------------------------------------------------------------\n");
            txt.append("TICKETS: A user can view, add, or delete tickets for a specific flight\n");
            txt.append("or delete all the tickets from a flight.\n\n");
            txt.append("-ADD TICKET: To add a ticket, open a specific flight by clicking on the\n");
            txt.append("'Open Flight' button next to the flight. once the manage flight window\n");
            txt.append("has opened, fill-in the textboxes at the bottom of the window.\n");
            txt.append("Once completed click on 'Add Ticket'\n\n");
            txt.append("-DELETE TICKET: To delete a ticket open a specific flight by clicking on\n");
            txt.append("the 'Open Flight' button next to the flight. once the manage flight window\n");
            txt.append("has opened, Click on the 'Delete Ticket' Button.\n\n");
            txt.append("DELETE ALL TICKETS: To delete all the tickets from a specific flight,\n");
            txt.append("open a specific flight by clicking on the 'Open Flight' button next to the\n");
            txt.append("flight. once the manage flight window has opened, Click on the\n");
            txt.append("'Delete all Tickets' Button to delete all the tickets.\n\n");
            txt.append("");
            jfhelp.setResizable(false);
            txt.setEditable(false);
            jfhelp.add(txt);
            jfhelp.setLocation(100,100);        
            jfhelp.setSize(420,580);
            jfhelp.setVisible(true);
            jfhelp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }
}
