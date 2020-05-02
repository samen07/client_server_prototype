import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerUtils {
    public static String ANSI_RESET = "\u001B[0m";
    public static String ANSI_BLACK = "\u001B[30m";
    public static String ANSI_RED = "\u001B[31m";
    public static String ANSI_GREEN = "\u001B[32m";
    public static String ANSI_YELLOW = "\u001B[33m";
    public static String ANSI_BLUE = "\u001B[34m";

    public static void consoleLog (String text){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        try
        {
            throw new Exception("Unknown caller!");
        }
        catch( Exception e )
        {
            System.out.println(ANSI_GREEN + formatter.format(date) + ": " + ANSI_BLUE + e.getStackTrace()[1].getClassName() +
                    "." +
                    e.getStackTrace()[1].getMethodName() +
                    ": " + ANSI_RESET + text);
        }
    }

    public static void consoleErr (String text){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        try
        {
            throw new Exception("Unknown caller!");
        }
        catch( Exception e )
        {
            System.out.println(ANSI_YELLOW + formatter.format(date) + ": " + e.getStackTrace()[1].getClassName() +
                    "." +
                    e.getStackTrace()[1].getMethodName() +
                    ": " + ANSI_RED + text);
        }
    }

    public static void dbWork (String dataBase, String action, String email, String password) throws SQLException {
        String dbUrlSrv = "jdbc:mysql://localhost:3306/";
        String dbName = dataBase;
        String dbUrl = dbUrlSrv + dbName +
                "?characterEncoding=utf8" +
                "&useUnicode=true" +
                "&useJDBCCompliantTimezoneShift=true" +
                "&useLegacyDatetimeCode=false" +
                "&serverTimezone=UTC";
        String user = "user_full_power";
        String pass = "07071987aA";


        //dbQuery("UPDATE","update employees " +
        //        "set email='john.doe@luv2code.com' " +
        //        "where last_name='Doe' and first_name='John'");

        //dbQuery("DELETE", "   delete from employees " +
        //        "where last_name='Doe' and first_name='John'");


        try {
            Connection myConn = DriverManager.getConnection(dbUrl, user, pass); ServerUtils.consoleLog(dbUrlSrv + dbName + " connected successfully");
            Statement myStmt = myConn.createStatement();
            if (action == "CREATE_USER") {
                String query = "INSERT INTO players (email, password, balance) VALUES('" + email + "', '" + password + "', '100')";
                int rowsAffected = myStmt.executeUpdate(query);
                    ServerUtils.consoleLog(action + " performed. Rows affected=" + Integer.toString(rowsAffected));
                ResultSet myRs = myStmt.executeQuery("SELECT * FROM players");
                while (myRs.next()){
                    ServerUtils.consoleLog(myRs.getString("id") + ", "
                            + myRs.getString("email") + ", "
                            + myRs.getString("password") + ", "
                            + myRs.getString("balance"));
                }
            } else if (action == "INSERT INTO" || action == "UPDATE" || action == "DELETE") {
                int rowsAffected = myStmt.executeUpdate("");
                ServerUtils.consoleLog(action + " DB action rows affected= " + Integer.toString(rowsAffected));
            } else {
                ServerUtils.consoleLog(action + ": NOT IMPLEMENTED");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
