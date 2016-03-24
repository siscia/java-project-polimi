package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientProxy extends Thread{

	Socket s;
	Server srv;
	
	// remote
	BufferedReader inSocket;
	PrintWriter outSocket;
	InputStream socketStream;
	
	// local
	BufferedReader inStdin;
	PrintWriter outStdout;
	InputStream systemStream;
	
	public ClientProxy(Server srv, Socket s) throws IOException {
		this.s = s;
		this.srv = srv;
		
		this.socketStream = s.getInputStream();
		this.inSocket = new BufferedReader(
				new InputStreamReader(socketStream));
		this.outSocket = new PrintWriter(s.getOutputStream(), true);
	}
	
	private void readInput(){
		String inputFromSocket;
		try {
			//System.out.println("PreSocket");
			if (socketStream.available() >= 1) {
				System.out.println("readin");
				inputFromSocket = inSocket.readLine();
				System.out.println("read");
				System.out.println(inputFromSocket);
				if (inputFromSocket.trim().length() >= 1){
					srv.sendMessage(this, inputFromSocket);
				}
			//System.out.println("PostSocket");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void receiveMessage(String str){
		outSocket.println(str);
		outSocket.flush();
	}

	public void run(){
		while(true){
			readInput();
		}
	}

}
