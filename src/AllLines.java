import javax.swing.*;
import java.sql.SQLException;
import java.sql.Statement;

public class AllLines extends Query {
    /**
     * Constructor
     *
     * @param statement
     */
    public AllLines(Statement statement) {
        super(statement);
    }

    @Override
    public String getSQL() {
        String sql = "SELECT route_id, route_short_name, route_long_name " +
                "FROM routes";
        return sql;
    }

    @Override
    public JTable getResults() throws SQLException {
        String[] columnNames = {"Line", "Name"};
        String[][] data = new String[50][2];
        int i = 0;
        while (rs.next()) {
            data[i][0] = rs.getString("route_short_name");
            data[i][1] = rs.getString("route_long_name");
            i++;
        }
        JTable table = new JTable(data, columnNames);
        table.setEnabled(false);
        return table;
    }
}
