import java.io.IOException;
import siabroPack.Connector;

public class Client {
    public static void main(String[] args){
        try (Connector connector = new Connector("127.0.0.1", 8000)){
            System.out.println("ConnectedToServer");

            String request = "Kiev";
            System.out.println("Request sent: "+ request);
            connector.writeLine(request);

            String response = connector.readLine();
            System.out.println("Response received: "+ response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
