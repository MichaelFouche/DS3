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
import javax.swing.JTextField;

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
    private boolean guiCreatedTicketBool;
    private boolean filterCity;
    private String cityToFilter;
    public Flights(){
         guiCreatedBool = false;
         guiCreatedTicketBool = false;
         filterCity = false;
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
        //cityToFilter
       /* try
        {             
            out.writeObject("Send Filtered Flights");
            out.writeObject(cityToFilter);
            out.flush();
            amountFlights = ((int)in.readObject());
            
            flight = new Flight[amountFlights];
            for(int i=0;i<amountFlights;i++)
            {
                flight[i] = null;
                flight[i] = new Flight();
                flight[i] = (Flight) in.readObject(); 
            }            
           // System.out.println("SERVER: >> " + (String)in.readObject()+"");
        }
        catch (IOException ioe)
        {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
        catch (ClassNotFoundException cnfe)
        {
            System.out.println("Class not found: " + cnfe.getMessage());
        } */
    }
    public void getAllFlights()
    {
        if(!guiCreatedBool)
        {
            createFlightGui();
        }
        //request all flights
     /*   try
        {  
            out.writeObject("Send All Flights");  
            out.flush();
            amountFlights = ((int)in.readObject());
            flight = new Flight[amountFlights];
            for(int i=0;i<amountFlights;i++)
            {
                flight[i] = null;
                flight[i] = new Flight();
                flight[i] =  (Flight) in.readObject(); 
            }
            //System.out.println("SERVER: >> " + (String)in.readObject()+"");
        }
        catch (IOException ioe)
        {
            System.out.println("IO Exception: " + ioe.getMessage());
        }
        catch (ClassNotFoundException cnfe)
        {
            System.out.println("Class not found1: " + cnfe);
        }
        catch (Exception ee)
        {
            System.out.println(ee);
        }*/
        
    }
    public void displayAllFlights()
    {
        if(!filterCity)
        {
            getAllFlights();
        }
        else
        {
            displayFilteredFlights();
        }
        
        addDataPanel(amountFlights);
        //call update gui to match amount of flights
        
        //SORT
        String datei, datej = "";
        int yeari,yearj = 0;
        int monthi,monthj = 0;
        int dayi, dayj = 0;
      /*  for(int i=0;i<amountFlights-1;i++)
        {
            for(int j=i+1;j<amountFlights;j++)
            {
                datei = flight[i].getFlightDate();
                datej = flight[j].getFlightDate();
                StringTokenizer tokeni = new StringTokenizer(datei);
                StringTokenizer tokenj = new StringTokenizer(datej);
                dayi = Integer.parseInt(tokeni.nextToken("/"));
                monthi = Integer.parseInt(tokeni.nextToken("/"));
                yeari = Integer.parseInt(tokeni.nextToken("/"));
                dayj = Integer.parseInt(tokenj.nextToken("/"));
                monthj = Integer.parseInt(tokenj.nextToken("/"));
                yearj = Integer.parseInt(tokenj.nextToken("/"));
                if(yeari>yearj)
                {
                    Flight temp = flight[i];
                    flight[i] = flight[j];
                    flight[j] = temp;
                }
                else if(yeari==yearj && monthi>monthj)
                {
                    Flight temp = flight[i];
                    flight[i] = flight[j];
                    flight[j] = temp;
                }
                else if(yeari==yearj && monthi==monthj &&dayi>dayj)
                {
                    Flight temp = flight[i];
                    flight[i] = flight[j];
                    flight[j] = temp;
                }
            }*/
        }
    
    public void actionPerformed(ActionEvent e)
    {
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
        
    }
}
