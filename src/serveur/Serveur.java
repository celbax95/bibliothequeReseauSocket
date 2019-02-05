package serveur;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import bibliotheque.Bibliotheque;
import service.FabriqueService;

public class Serveur implements Runnable {
	private static int cpt = 0;
	private ServerSocket socketServeur;
	private InetSocketAddress i;
	private String type;
	private FabriqueService f;
	private Bibliotheque b;

	public Serveur(int port, String type, Bibliotheque b) {
		i = new InetSocketAddress(port);
		this.type = type;
		this.b = b;
		f = new FabriqueService();
	}

	public void start() {
		new Thread(this).start();
	}

	@Override
	public void run() {
		int cpt;
		String nom = "Serveur '" + type + "'";
		try {
			socketServeur = new ServerSocket(i.getPort());
			System.out.println("-- " + nom + " : Demarrage du serveur, ecoute sur le port " + i.getPort());
		} catch (Exception e) {
			System.err.println("x- " + nom + " : Erreur a l'ouverture du port d'ecoute " + i.getPort());
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
		while (!Thread.currentThread().isInterrupted()) {
			Socket socketClient = null;
			try {
				socketClient = socketServeur.accept();
			} catch (IOException e) {
				System.err.println("x- " + nom + " : Erreur a l'acceptation d'un client");
				e.printStackTrace();
				continue;
			}
			synchronized (this.getClass()) {
				cpt = Serveur.cpt++;
				System.out.println("-- " + nom + " : Client  " + cpt + " accepte");
				f.getService(type, socketClient, cpt, b).start();
				System.out.println("-- " + nom + " : Client  " + cpt + " : creation d'un service '" + type + "'");
			}
		}
	}
}
