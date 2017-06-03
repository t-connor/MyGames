package socket;
  
import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.io.IOException;  
import java.net.Socket;  
import java.util.Scanner;  
  
 
public class chatclient {  
    private String host = "localhost";
    private int port = 8189;
  
    public chatclient() {  
  
    }  
  

    public chatclient(String host, int port) {  
        this.host = host;  
        this.port = port;  
    }  
  
    public void chat() {  
        try {  

            Socket socket = new Socket(host, port);  
  
            try {  

                DataInputStream in = new DataInputStream(socket.getInputStream());  

                DataOutputStream out = new DataOutputStream(socket.getOutputStream());  

                Scanner scanner = new Scanner(System.in);  
  
                while (true) {  
                    String send = scanner.nextLine();  
                    //System.out.println("client:" + send);  
    
                    out.writeUTF("client:" + send);  
 
                    String accpet = in.readUTF();  
                    System.out.println(accpet);  
                }  
  
            } finally {  
                socket.close();  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    public static void main(String[] args) {  
        new chatclient().chat();  
    }  
}  
