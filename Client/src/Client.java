import java.io.IOException;
import siabroPack.Connector;

public class Client {
    public static void main(String[] args){
        try (Connector connector = new Connector("127.0.0.1", 8000)){
            System.out.println("ConnectedToServer");

            //String request = "<?xml version = '1.0'?><class>" +
            //        "<user email = 'asyabro@gmail.com'>" +
            //        "<password>qwerty123</password>" +
            //        "<action>roll_dice</action>" +
            //        "</user></class>";
            String request = "<?xml version = '1.0'?><class>" +
                    "<customer id='100'><age>29</age><name>mkyong</name>" +
                    "</customer></class>";

            System.out.println("Request sent: " + request);
            connector.writeLine(request);

            String response = connector.readLine();
            System.out.println("Response received: "+ response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
