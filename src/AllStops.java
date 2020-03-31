import javax.swing.*;
import java.sql.SQLException;
import java.sql.Statement;

public class AllStops extends Query {
    private String line;
    private String day;
    private int direction;

    /**
     * Constructor
     *
     * @param statement
     */
    public AllStops(Statement statement, String line, String day) {
        super(statement);
        this.line = line + "-155";
        this.day = day.toLowerCase();
        direction = 0;
    }

    @Override
    public String getSQL() {
        String sql = "SELECT t.route_id, st.stop_sequence, s.stop_name " +
                "FROM trips t, calendar c, stops s, stop_times st " +
                "WHERE t.service_id = c.service_id " +
                "AND t.trip_id = st.trip_id " +
                "AND st.stop_id = s.stop_id " +
                "AND c." + day + " = 1 " +
                "AND t.route_id = \"" + line + "\" " +
                "AND t.direction_id = " + direction + " " +
                "GROUP BY s.stop_name " +
                "ORDER BY st.stop_sequence";
        return sql;
    }

    @Override
    public JTable getResults() throws SQLException {
        String[] columnNames = {"Line", "Stop Sequence", "Stop Name"};
        String[][] data = new String[100][3];
        int i = 0;
        while (rs.next()) {
            data[i][0] = rs.getString("t.route_id");
            data[i][1] = rs.getString("st.stop_sequence");
            data[i][2] = rs.getString("s.stop_name");
            i++;
        }
        JTable table = new JTable(data, columnNames);
        table.setEnabled(false);
        return table;
    }
}
