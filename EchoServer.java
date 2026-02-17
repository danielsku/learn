import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EchoServer {

    ServerSocket serverSocket;

    public void startRun(int port) throws Exception {
        serverSocket = new ServerSocket(port);
        while(true){
            new ChatUser(serverSocket.accept(), "User #").start();
        }
    }

    public void stopServer() throws Exception{
        serverSocket.close();
    }


    private static class ChatUser extends Thread {
        
        Socket clientSocket;
        PrintWriter out;
        BufferedReader in;
        String username;
        static int usercount = 0;
        static List<PrintWriter> total_users = Collections.synchronizedList(new ArrayList<>());

        public ChatUser(Socket a, String username) throws Exception{
            this.clientSocket = a;
            this.username = username + " " + this.usercount++;
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            total_users.add(out);
            broadcast(username + " Connected");
        }

        public static void broadcast(String message) throws Exception{
            for(PrintWriter w: total_users){
                w.println(message);
            }
        }

        public void run(){
            String inputLine;
            try{ 
                while ((inputLine = in.readLine()) != null){
                broadcast(username +": "+inputLine);
                    if (".".equals(inputLine)) {
                        userDisconnect();
                        break;
                    }
                }
            }catch(Exception e){

            }
        }

        public void userDisconnect() throws Exception{
            broadcast(username +" left");
            total_users.remove(out);
            in.close();
            out.close();
            clientSocket.close();
        }
    }

    public static void main(String args[]) throws Exception{
       
        EchoServer a = new EchoServer();
        a.startRun(4444);

        //a.stopServer();
    }
}