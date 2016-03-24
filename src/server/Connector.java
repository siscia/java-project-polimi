package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connector extends Thread {

	ServerSocket ss;
	Server s;
	
	public Connector(Server s) {
		System.out.println("Start Connector");
		try {
			this.s = s;
			this.ss = new ServerSocket(2004);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while(true){
			System.out.println("Connector Loop");
			try {
				System.out.println("Waiting for connection");
				Socket socket = ss.accept();
				System.out.println("Connection Accepted");
				this.s.newClient(socket);
				System.out.println("New Client");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
	}

}
