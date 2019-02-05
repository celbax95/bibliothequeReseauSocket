package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import bibliotheque.AbonneInexistant;
import bibliotheque.AbonneInterdit;
import bibliotheque.Bibliotheque;
import bibliotheque.DocumentInexistant;
import bibliotheque.PasLibreException;

public class ServiceReservation extends AService {

	public ServiceReservation(Socket socketCotéServeur, int id, Bibliotheque b) {
		super(socketCotéServeur, id, b);
	}

	// le service de reservation des livres accepte le format de requête :
	// (num doc)## (num abo) ##
	@Override
	public void routine(BufferedReader socketIn, PrintWriter socketOut) throws IOException {
		String in = null;
		while (!getSocket().isClosed()) {
			String out = "Service de reservation des documents de la bibiliotheque " + getBibli().nom()
					+ ". ('exit' pour quitter) : ";
			socketOut.println(out);
			socketOut.println("utilisation : numDoc numAbo");
			socketOut.println(""); // symbolise une fin d'envoi
			in = socketIn.readLine();
			System.out.println("-- Service reservation " + getId() + ": Reçu : " + in);
			if (in.equals("exit")) {
				socketOut.println(getIntsig());
				return;
			}
			try {
				String[] champs = in.split(" ");
				final int length = 2;
				if (champs.length != length)
					throw new NumberFormatException("Argument(s) saisis incorrects");
				getBibli().reserver(Integer.parseInt(champs[0]), Integer.parseInt(champs[1]));
				out = "Document reserve";
			} catch (PasLibreException | NumberFormatException | AbonneInexistant | DocumentInexistant | AbonneInterdit e) {
				out = e.getMessage();
			}
			socketOut.println(out);
		}
	}
}
