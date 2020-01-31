import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main (String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(8000))
        {
            System.out.println("SRVR_started.");

            while (true)
                try (
                        Socket socket = server.accept();
                        BufferedWriter writer =
                                new BufferedWriter(
                                        new OutputStreamWriter(
                                                socket.getOutputStream()));
                        BufferedReader reader =
                                new BufferedReader(
                                        new InputStreamReader(
                                                socket.getInputStream()));
                        ){
                    String request = reader.readLine();
                    System.out.println("Request: " + request);
                    String response = (int)(Math.random() * 30 - 10) + "";
                    System.out.println("Response: " + response);
                    writer.write(response);
                    writer.newLine();
                    writer.flush();
                } catch (IOException e){
                    throw new RuntimeException(e);
                }
        }
    }

}
