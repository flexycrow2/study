package babylion.day07.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private String nickname;
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    public Client(String nickname, BufferedReader in, PrintWriter out, Socket socket) {
        this.nickname = nickname;
        this.in = in;
        this.out = out;
        this.socket = socket;
    }

    public String getNickname() {
        return nickname;
    }

    public BufferedReader getIn() {
        return in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public Socket getSocket() {
        return socket;
    }

    public void close(){
        try{
            in.close();
            out.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
