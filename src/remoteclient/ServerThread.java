
package remoteclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread implements Runnable {
    
    ServerSocket serverSocket;
    Socket socket;
    int count;
    ArrayList<DataOutputStream> sockete;
    public ServerThread(Socket socket, ArrayList<DataOutputStream> sockete,int count){
    
        this.socket=socket;
        this.sockete=sockete;
        this.count=count;
    }
    
    public void run() {
        try{
        
            
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            System.out.println(" Connection from " + socket);
            
            while (true) {
                String message = dis.readUTF();
                System.out.println(message); 
                for(int i=0;i<sockete.size();i++)
                   if(sockete.get(i)!=dos)
                    sockete.get(i).writeUTF(message);
                
            }
        }
        catch(Exception e){
         }
    }
    
}
