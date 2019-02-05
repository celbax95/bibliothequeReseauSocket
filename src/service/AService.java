package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import bibliotheque.Bibliotheque;
import serveur.Service;

public abstract class AService implements Service {
	private final static String INTSIG = "INT";
	private Socket client;
	private int id;
	private Bibliotheque b;

	public AService(Socket socketClient, int id, Bibliotheque b) {
		this.client = socketClient;
		this.id = id;
		this.b = b;
	}

	@Override
	public void start() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		try {
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter socketOut = new PrintWriter(client.getOutputStream(), true);
			routine(socketIn, socketOut);
		} catch (Exception e) {
			System.err.println("x- Exception , fermeture du service " + id);
			e.printStackTrace();
		}
		try {
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("-- Communication service/client " + id + " termine");
		Thread.currentThread().interrupt();
	}

	@Override
	protected void finalize() throws Throwable {
		System.out.println("-- Service " + id + " ferme");
	}

	public static String getIntsig() {
		return INTSIG;
	}

	public Bibliotheque getBibli() {
		return b;
	}

	public int getId() {
		return id;
	}
	
	public Socket getSocket() {
		return client;
	}
}
