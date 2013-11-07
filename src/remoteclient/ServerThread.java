
package remoteclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class ServerThread implements Runnable {
    
    ServerSocket serverSocket;
    Socket socket;
    int count;
    boolean connected;
    ArrayList<DataOutputStream> sockete;
    public ServerThread(Socket socket, ArrayList<DataOutputStream> sockete,int count, boolean connected){
    
        this.socket=socket;
        this.sockete=sockete;
        this.count=count;
        this.connected = connected;
    }
    
    public void run() {
        try{
        
            
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            System.out.println(" Connection from " + socket);
            
            while (true) {
				if(socket.isClosed()){
					connected = false;
					System.out.println("Connection ended.");
				}
								
                String message = dis.readUTF();
                System.out.println(message); 
                
                try {
					String[] command = new String[]{"/bin/bash", "-c", message};
					String output = runProcess(command);
					System.out.println(output);
					dos.writeUTF(output);
				}catch(Exception e) {
					System.out.println("Cacat");
				}
                
            }
        }
        catch(Exception e){
         }
    }
    
    public String runProcess(String[] cmd) {
		StringBuffer theRun = null;
		try {
			Process process = Runtime.getRuntime().exec(cmd);

			BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));
			int read;
			char[] buffer = new char[4096];
			StringBuffer output = new StringBuffer();
			while ((read = reader.read(buffer)) > 0) {
				theRun = output.append(buffer, 0, read);
			}
			reader.close();
			process.waitFor();

		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
			return theRun.toString().trim();
	}
    
}
