package service;

import java.net.Socket;

import bibliotheque.Bibliotheque;
import serveur.Service;

public class FabriqueService {
	public static final String TYPE_EMPRUNT = "emprunt";
	public static final String TYPE_RESERVATION = "reservation";
	public static final String TYPE_RETOUR = "retour";

	public Service getService(String type, Socket s, int id, Bibliotheque b) {
		Service service = null;
		switch (type.toLowerCase()) {
		case TYPE_EMPRUNT:
			service = new ServiceEmprunt(s, id, b);
			break;
		case TYPE_RESERVATION:
			service = new ServiceReservation(s, id, b);
			break;
		case TYPE_RETOUR:
			service = new ServiceRetour(s, id, b);
			break;
		default:
			throw new IllegalArgumentException("Type de service inconnu");
		}
		return service;
	}

}
