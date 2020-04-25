import java.io.IOException;
import java.net.ServerSocket;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import siabroPack.Connector;

public class Server {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static void main (String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(8000))
        {
            consoleLog("SRVR_started.");

            while (true) {

                Connector connector = new Connector(server);
                    new Thread(() -> {
                        String request = connector.readLine();      consoleLog("Request received: " + request);

                        try {
                            dbConnector();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        String response = (int)(Math.random() * 30 - 10) + "";
                        try {Thread.sleep(1000);} catch (InterruptedException e) {}

                        connector.writeLine(response);              consoleLog("Response sent: " + response);
                        try {connector.close(); } catch (IOException e) { }
                    }).start();

                }

        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static void dbConnector () throws SQLException {
        String dbUrl = "jdbc:mysql://localhost:3306/demo" +
                "?characterEncoding=utf8" +
                "&useUnicode=true" +
                "&useJDBCCompliantTimezoneShift=true" +
                "&useLegacyDatetimeCode=false" +
                "&serverTimezone=UTC";
        String user = "student";
        String pass = "student";

        try {
            Connection myConn = DriverManager.getConnection(dbUrl, user, pass);
            consoleLog(">>> DB connection successfull.");
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("select * from employees");
            while (myRs.next()){
                consoleLog( myRs.getString("last_name") + ", " + myRs.getString("first_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void consoleLog (String text){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        try
        {
            throw new Exception("Unknown caller!");
        }
        catch( Exception e )
        {
            System.out.println(ANSI_GREEN + ": " + formatter.format(date) + ANSI_BLUE + e.getStackTrace()[1].getClassName() +
                    "." +
                    e.getStackTrace()[1].getMethodName() +
                    ": " + ANSI_RESET + text);
        }
    }
}
