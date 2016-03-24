package server;

import java.io.IOException;
import java.net.Socket;
import java.util.HashSet;
import java.util.LinkedList;

import static java.lang.System.out;

public class Server {

	Connector connector;
	HashSet<ClientProxy> clientProxies;
	LinkedList<String> mssgs;
	
	public Server() {
		this.connector = new Connector(this);
		this.clientProxies = new HashSet<ClientProxy>();
		this.mssgs = new LinkedList<String>();
		new Thread(connector).start();
	}

	public void newClient(Socket s) throws IOException {
		System.out.println("New Client");
		ClientProxy cp = new ClientProxy(this, s);
		this.clientProxies.add(cp);
		new Thread(cp).start();
	}
	
	public void sendMessage(ClientProxy s, String mssg){
		if(clientProxies.contains(s)){
			processMessage(mssg);
		}
	}
	
	private void processMessage(String mssg){
		System.out.println(mssg);
		mssgs.add(mssg);
		for (ClientProxy cp: clientProxies){
			cp.receiveMessage(mssg);
		}
	}
	
	public void output(){
		System.out.println("I do exists!");
	}
	
	public static void main(String args[])
	{
		/*
		System.out.println("Starting Server");
		Server s = new Server();
		s.output();
		*/
		new Server();
	}
	
}
