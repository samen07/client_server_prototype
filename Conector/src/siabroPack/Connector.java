package siabroPack;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * @author Created by antonsiabro on 09.02.2020.
 */
public class Connector implements Closeable {
    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public Connector(String ip, int port){
        try {
            this.socket = new Socket(ip, port);
            this.reader = createReader();
            this.writer = creareWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Connector(ServerSocket server){
        try {
            this.socket = server.accept();
            this.reader = createReader();
            this.writer = creareWriter();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeLine(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e){
            throw new  RuntimeException(e);
        }
    }

    public String readLine(){
        try {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedReader createReader() throws IOException {
            return new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private BufferedWriter creareWriter() throws IOException {
            return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    @Override
    public void close() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }
}
