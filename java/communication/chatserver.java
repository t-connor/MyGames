package socket;

import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.io.IOException;  
import java.net.ServerSocket;  
import java.net.Socket;  
import java.util.Scanner;  

public class chatserver {
	private int port = 8189;

	public chatserver() {
	}

	public chatserver(int port) {
		this.port = port;
	}

	public void service() {
		try {
			ServerSocket service = new ServerSocket(port);

			Socket socket = service.accept();
			try {
				DataInputStream in = new DataInputStream(socket.getInputStream());
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());

				Scanner scanner = new Scanner(System.in);
				while(true) {
					String accpet = in.readUTF();
					System.out.println(accpet);
					String send = scanner.nextLine();
					//System.out.println("service: " + send);
					out.writeUTF("service: " + send);
				}
			}finally {
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new chatserver().service();
	}
}
