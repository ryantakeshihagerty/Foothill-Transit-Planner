import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Connect {
    Connection connection = null;
    private String user;
    private String password;

    /**
     * Connects to database
     * @return statement object connected to database
     * @throws SQLException
     */
    public Statement login() throws SQLException {
        // Get user and password
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter user: ");
        this.user = sc.nextLine();
        System.out.println("Enter password: ");
        this.password = sc.nextLine();

        // Connect to database
        connection = DriverManager.getConnection("jdbc:mysql://localhost/transitdata?" + "user=" + user + "&password=" + password);
        return connection.createStatement();
    }

    /**
     * Closes connection with database
     * @throws SQLException
     */
    public void closeConnection() throws SQLException {
        connection.close();
    }
}
