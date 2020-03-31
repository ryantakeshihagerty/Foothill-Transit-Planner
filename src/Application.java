import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

public class Application {
    private static JFrame frame;
    private JPanel cards;

    private JPanel mainMenu;
    private JButton listTripsButton, allStopsButton, allLinesButton, exitButton;

    private JPanel listTrips;
    private JTextField startStop, endStop;
    private JComboBox dayLT;
    private JFormattedTextField depTime, arrTime;
    private JButton searchButtonLT, backButtonLT;

    private JPanel allStops;
    private JTextField lineAS;
    private JComboBox dayAS;
    private JButton searchButtonAS, backButtonAS;

    private JPanel output;
    private JButton backButtonOut;
    private JScrollPane outScrollPane;

    public Application(Connect connect, Statement statement) throws ParseException {
        initComponents();
        loadMenu();

        listTripsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) cards.getLayout()).show(cards, "listTrips");
            }
        });
        allStopsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) cards.getLayout()).show(cards, "allStops");
            }
        });
        allLinesButton.addActionListener(new ActionListener() {
            @Override // display all lines
            public void actionPerformed(ActionEvent e) {
                AllLines allLines = new AllLines(statement);
                try {
                    allLines.runQuery(allLines.getSQL());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                try {
                    outScrollPane.getViewport().add(allLines.getResults());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                ((CardLayout) cards.getLayout()).show(cards, "output");
            }
        });
        backButtonOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMenu();
            }
        });
        backButtonAS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMenu();
            }
        });
        backButtonLT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMenu();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                try {
                    connect.closeConnection();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });
        searchButtonAS.addActionListener(new ActionListener() {
            @Override // search all stops
            public void actionPerformed(ActionEvent e) {
                AllStops allStops = new AllStops(statement, lineAS.getText(), (String) dayAS.getSelectedItem());

                // Run the query
                try {
                    allStops.runQuery(allStops.getSQL());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                ((CardLayout) cards.getLayout()).show(cards, "output");
                try {
                    outScrollPane.getViewport().add(allStops.getResults());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        searchButtonLT.addActionListener(new ActionListener() {
            @Override // plan trip
            public void actionPerformed(ActionEvent e) {
                ListTrips listTrips = new ListTrips(statement, startStop.getText(), endStop.getText(), (String) dayLT.getSelectedItem(), depTime.getText(), arrTime.getText());

                // Run actual query
                try {
                    listTrips.runQuery(listTrips.getSQL());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                ((CardLayout) cards.getLayout()).show(cards, "output");
                try {
                    outScrollPane.getViewport().add(listTrips.getResults());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void loadMenu() {
        ((CardLayout) cards.getLayout()).show(cards, "mainMenu");
    }

    private void initComponents() throws ParseException {
        cards.add("mainMenu", mainMenu);
        cards.add("listTrips", listTrips);
        cards.add("allStops", allStops);
        cards.add("output", output);

        dayAS.addItem("Monday");
        dayAS.addItem("Tuesday");
        dayAS.addItem("Wednesday");
        dayAS.addItem("Thursday");
        dayAS.addItem("Friday");
        dayAS.addItem("Saturday");
        dayAS.addItem("Sunday");

        dayLT.addItem("Monday");
        dayLT.addItem("Tuesday");
        dayLT.addItem("Wednesday");
        dayLT.addItem("Thursday");
        dayLT.addItem("Friday");
        dayLT.addItem("Saturday");
        dayLT.addItem("Sunday");
    }

    public static void main(String[] args) throws SQLException, ParseException {
        // Connect to MySQL server
        Connect connect = new Connect();
        Statement statement = connect.login();

        // Create window
        frame = new JFrame("Foothill Transit Planner");
        frame.setContentPane(new Application(connect, statement).cards);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
