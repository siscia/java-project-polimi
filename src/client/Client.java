package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;



public class Client extends Thread{

	BufferedReader inSocket;
	
	InputStream socketStream;
	InputStream systemStream;
	
	BufferedReader inStdin;
	PrintWriter outSocket;
	PrintWriter outStdout;
	Socket s;
	
	public Client() throws UnknownHostException, IOException {
		this.systemStream = System.in;
		this.inStdin = new BufferedReader(new InputStreamReader(systemStream));
		this.outStdout = new PrintWriter(new OutputStreamWriter(System.out));
		
		this.s = new Socket("localhost", 2004);
		this.socketStream = s.getInputStream();
		this.inSocket = new BufferedReader(
				new InputStreamReader(socketStream));
		this.outSocket = new PrintWriter(s.getOutputStream(), true);
		
		System.out.print("> ");
	}
	
	public void readInput() {
		String inputFromUser;
		String inputFromSocket;
		//System.out.println("Running");
		try {
			//System.out.println("PreInput");
			if (systemStream.available() >= 1) 
			{
				System.out.println("[INFO] " + "available");
				inputFromUser = inStdin.readLine();
				if (inputFromUser.trim().length() > 0){
					System.out.println("[INFO] " + "receive message");
					System.out.println("[INFO] " + inputFromUser);
					System.out.println("[INFO] " + inputFromUser.length());
					outSocket.println(inputFromUser);
					outSocket.flush();
				} else {
					System.out.println("New line ???");
				}
				System.out.print("> ");
			//System.out.println("PostInput");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			//System.out.println("PreSocket");
			if (socketStream.available() >= 1) {
				inputFromSocket = inSocket.readLine();	
				if (inputFromSocket.trim().length() > 0){
					outStdout.println(inputFromSocket);
					outStdout.flush();
					System.out.println(inputFromSocket);
				}
			//System.out.println("PostSocket");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void run(){
		while(true){
			this.readInput();
		}
	}
	
	public static void main(String args[]) throws UnknownHostException, IOException
	{
		Client client = new Client();
		client.run();
	}
	
}
