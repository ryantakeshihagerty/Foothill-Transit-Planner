import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Query {
    Statement statement;
    ResultSet rs;

    /**
     * Constructor
     * @param statement
     */
    public Query(Statement statement) {
        this.statement = statement;
    }

    /**
     * Create appropriate SQL command
     * @return
     */
    public abstract String getSQL();

    public void runQuery(String sql) throws SQLException {
        this.rs = statement.executeQuery(sql);
    }

    public abstract JTable getResults() throws SQLException;
}
