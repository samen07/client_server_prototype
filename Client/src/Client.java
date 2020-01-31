import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args){
        try (
                Socket socket = new Socket("127.0.0.1", 8000);
                BufferedWriter writer =
                        new BufferedWriter (
                                new OutputStreamWriter(
                                        socket.getOutputStream()));
                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()));
        ){
            System.out.println("ConnectedToServer");
            String request = "Kiev";
            System.out.println("Request: "+ request);

            writer.write(request);
            writer.newLine();
            writer.flush();

            String response = reader.readLine();
            System.out.println("Response: "+ response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
