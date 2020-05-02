import java.io.IOException;
import java.net.ServerSocket;
import siabroPack.Connector;

public class Server {

    public static void main (String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(8000))
        {
            ServerUtils.consoleLog("SRVR_started.");

            while (true) {

                Connector connector = new Connector(server);
                    new Thread(() -> {
                        String request = connector.readLine();      ServerUtils.consoleLog("Request received: " + request);

                        //parse XML if user create and put it to create()
                        ServerUser.create("vasia6@siabro.pp.ua", "qwerty123");


                        String response = (int)(Math.random() * 30 - 10) + "?"; //temperature randomizer
                        try {Thread.sleep(1000);} catch (InterruptedException e) {} //high load simulation

                        connector.writeLine(response);              ServerUtils.consoleLog("Response sent: " + response);
                        try {connector.close(); } catch (IOException e) { }
                    }).start();

                }

        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}