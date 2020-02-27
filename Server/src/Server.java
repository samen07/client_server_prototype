import java.io.IOException;
import java.net.ServerSocket;

import siabroPack.Connector;

public class Server {
    public static void main (String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(8000))
        {
            System.out.println("SRVR_started.");

            while (true) {

                Connector connector = new Connector(server);
                    new Thread(() -> {
                        String request = connector.readLine();      System.out.println("Request received: " + request);
                        String response = (int)(Math.random() * 30 - 10) + "";
                        try {Thread.sleep(4000);} catch (InterruptedException e) {}
                        connector.writeLine(response);              System.out.println("Response sent: " + response);
                        try {connector.close(); } catch (IOException e) { }
                    }).start();

                }

        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
