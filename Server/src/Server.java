import java.io.IOException;
import java.net.ServerSocket;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import siabroPack.Connector;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
                            dbQuery("SELECT", "select * from employees");

                            //dbQuery("INSERT INTO", "insert into employees " +
                            //        "(last_name, first_name, email, department, salary)" +
                            //        " values " +
                            //        "('Wright', 'Eric', 'eric.wright@foo.com', 'HR', 33000.0)");

                            //dbQuery("UPDATE","update employees " +
                            //        "set email='john.doe@luv2code.com' " +
                            //        "where last_name='Doe' and first_name='John'");

                            //dbQuery("DELETE", "delete from employees " +
                            //        "where last_name='Doe' and first_name='John'");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        String response = (int)(Math.random() * 30 - 10) + "?"; //temperature randomizer
                        try {Thread.sleep(1000);} catch (InterruptedException e) {} //high load simulation

                        connector.writeLine(response);              consoleLog("Response sent: " + response);
                        try {connector.close(); } catch (IOException e) { }
                    }).start();

                }

        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @XmlRootElement
    public class Customer {

        String name;
        int age;
        int id;

        public String getName() {
            return name;
        }

        @XmlElement
        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        @XmlElement
        public void setAge(int age) {
            this.age = age;
        }

        public int getId() {
            return id;
        }

        @XmlAttribute
        public void setId(int id) {
            this.id = id;
        }

    }

    public static void dbQuery (String action, String query) throws SQLException {
        String dbUrlSrv = "jdbc:mysql://localhost:3306/";
        String dbName = "demo"; //test for Udemy
        String dbUrl = dbUrlSrv + dbName +
                "?characterEncoding=utf8" +
                "&useUnicode=true" +
                "&useJDBCCompliantTimezoneShift=true" +
                "&useLegacyDatetimeCode=false" +
                "&serverTimezone=UTC";
        String user = "student";
        String pass = "student";

        try {
            Connection myConn = DriverManager.getConnection(dbUrl, user, pass); consoleLog(dbUrlSrv + dbName + " connected successfully");
            Statement myStmt = myConn.createStatement();
            if (action == "SELECT") {
                ResultSet myRs = myStmt.executeQuery(query);
                while (myRs.next()){
                    consoleLog(myRs.getString("id") + ", "
                            + myRs.getString("last_name") + ", "
                            + myRs.getString("first_name") + ", "
                            + myRs.getString("email") + ", "
                            + myRs.getString("department") + ", "
                            + myRs.getString("salary"));
                }
            } else if (action == "INSERT INTO" || action == "UPDATE" || action == "DELETE") {
                int rowsAffected = myStmt.executeUpdate(query);
                consoleLog(action + " DB action rows affected=" + Integer.toString(rowsAffected));
            } else {
                consoleLog(action + ": NOT IMPLEMENTED");
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
            System.out.println(ANSI_GREEN + formatter.format(date) + ": " + ANSI_BLUE + e.getStackTrace()[1].getClassName() +
                    "." +
                    e.getStackTrace()[1].getMethodName() +
                    ": " + ANSI_RESET + text);
        }
    }
}