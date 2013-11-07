package remoteclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.net.Socket;
import java.util.ArrayList;

public class RemoteClient {
    
    private ServerSocket serverSocket;
    int port;
    int count=0;
    boolean connected=false;
    
    public ArrayList<DataOutputStream> sockete = new ArrayList<DataOutputStream>();
    
    public static void main(String[] args) throws IOException {
        new RemoteClient(1041);
    }

    public RemoteClient(int port) throws IOException {
        this.port = port;
        listen();
    }
    
    public void listen() throws IOException {
        this.serverSocket = new ServerSocket(this.port);
        System.out.println("Listening on: " + serverSocket);
        
        //
        count++;
     
        
        while (true) {
            if(connected==false){
            Socket socket = serverSocket.accept();
            sockete.add(new DataOutputStream(socket.getOutputStream()));
            
            Thread T= new Thread(new ServerThread(socket,sockete,count, connected));
            T.start();
            
            connected=true;
            }
            
        }
        //
    }
}
