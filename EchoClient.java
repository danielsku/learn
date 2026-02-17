import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class EchoClient {
    private PrintWriter out;
    private BufferedReader in;
    private Socket a;

    public void start(String ip, int port) throws Exception{
        a = new Socket(ip, port);
        out = new PrintWriter(a.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(a.getInputStream()));
    }
    public void sendMessage(String msg) throws Exception{
        out.println(msg);
    }

    public void stopConnection() throws Exception{
        in.close();
        out.close();
        a.close();
    }

    public static void main(String args[]) throws Exception{
        EchoClient client = new EchoClient();
        client.start("100.64.100.6", 4444);
        new WriteChat(client).start();
        new ReadChat(client.in).start(); //Not safe, just for practical purposes
    }
}

class ReadChat extends Thread{

    BufferedReader a;

    public ReadChat(BufferedReader b){
        a = b;
    }

    public void run(){
        String input;
        try{ 
            while ((input = a.readLine()) != null){
                System.out.println(input);
            }
        }catch(Exception e){

        }
    }
}


class WriteChat extends Thread{
    EchoClient a;

    public WriteChat(EchoClient b){
        a = b;
    }

    public void run(){
        Scanner message = new Scanner(System.in);
        String toSend;
        try{
            do{
                toSend = message.nextLine();
                a.sendMessage(toSend);

            }while(!toSend.equals("."));

            a.stopConnection();
        }catch(Exception e){

        }
    }
}