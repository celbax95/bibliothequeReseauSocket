package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import bibliotheque.Bibliotheque;
import bibliotheque.DocumentInexistant;
import bibliotheque.RetourInutile;

public class ServiceRetour extends AService {

	public ServiceRetour(Socket socketCotéServeur, int id, Bibliotheque b) {
		super(socketCotéServeur, id, b);
	}

	// le service d'emprunt des livres accepte le format de requête :
	// (num doc)##
	@Override
	public void routine(BufferedReader socketIn, PrintWriter socketOut) throws IOException {
		String in = null;
		while (!getSocket().isClosed()) {
			String out = "Service de retour des documents de la bibiliotheque " + getBibli().nom()
					+ ". ('exit' pour quitter) : ";
			socketOut.println(out);
			socketOut.println("utilisation : numDoc bonEtat(1 ou 0)");
			socketOut.println(""); // symbolise une fin d'envoi
			in = socketIn.readLine();
			System.out.println("-- Service retour " + getId() + ": Reçu : " + in);
			if (in.equals("exit")) {
				socketOut.println(getIntsig());
				return;
			}
			try {
				String[] champs = in.split(" ");
				final int length = 2;
				if (champs.length != length)
					throw new NumberFormatException("Argument(s) saisis incorrects.");
				Boolean etat = parseBoolean(champs[1]);
				getBibli().retourner(Integer.parseInt(champs[0]), etat);
				out = "Document retourne";
			} catch (NumberFormatException | DocumentInexistant | RetourInutile e) {
				out = e.getMessage();
			}
			socketOut.println(out);
		}
	}

	private static boolean parseBoolean(String s) {
		if (s.equals("1"))
			return true;
		else if (s.equals("0"))
			return false;
		else
			throw new NumberFormatException("Mauvais format de l'etat du doc.");
	}
}
