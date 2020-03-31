import javax.swing.*;
import java.sql.SQLException;
import java.sql.Statement;

public class ListTrips extends Query {
    private String start;
    private String end;
    private String day;
    private String earliestStartTime;
    private String latestArrivalTime;

    /**
     * Constructor
     *
     * @param statement
     */
    public ListTrips(Statement statement, String start, String end, String day, String depTime, String arrTime) {
        super(statement);
        this.start = start;
        this.end = end;
        this.day = day.toLowerCase();
        earliestStartTime = depTime;
        latestArrivalTime = arrTime;
    }

    @Override
    public String getSQL() {
        String sql = "SELECT r.route_id AS Line, " +
                "t.trip_headsign AS Headsign, " +
                "s_start.stop_name AS Departure_Stop, " +
                "st_start.departure_time, " +
                "s_end.stop_name AS Destination_Stop, " +
                "st_end.arrival_time " +
                "FROM trips t INNER JOIN calendar c ON t.service_id = c.service_id " +
                "INNER JOIN routes r ON t.route_id = r.route_id " +
                "INNER JOIN stop_times st_start ON t.trip_id = st_start.trip_id " +
                "INNER JOIN stops s_start ON s_start.stop_id = st_start.stop_id " +
                "INNER JOIN stop_times st_end ON t.trip_id = st_end.trip_id " +
                "INNER JOIN stops s_end ON s_end.stop_id = st_end.stop_id " +
                "WHERE c." + day + " = 1 " +
                "AND st_start.departure_time > \"" + earliestStartTime + "\" " +
                "AND st_end.arrival_time < \"" + latestArrivalTime + "\" " +
                "AND s_start.stop_name LIKE '%" + start + "%' " +
                "AND s_end.stop_name LIKE '%" + end + "%'";
        return sql;
    }

    @Override
    public JTable getResults() throws SQLException {
        String[] columnNames = {"Line", "Headsign", "Departure Stop", "Departure Time", "Arrival Stop", "Arrival Time"};
        String[][] data = new String[100][6];
        int i = 0;
        while (rs.next()) {
            data[i][0] = rs.getString("Line");
            data[i][1] = rs.getString("Headsign");
            data[i][2] = rs.getString("Departure_Stop");
            data[i][3] = rs.getString("st_start.departure_time");
            data[i][4] = rs.getString("Destination_Stop");
            data[i][5] = rs.getString("st_end.arrival_time");
            i++;
        }
        JTable table = new JTable(data, columnNames);
        table.setEnabled(false);
        return table;
    }
}
