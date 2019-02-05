package serveur;

import java.util.HashSet;
import java.util.Set;

import abonne.AbonneStandard;
import bibliotheque.Abonne;
import bibliotheque.Bibliotheque;
import bibliotheque.Document;
import document.Livre;
import service.FabriqueService;

public class Main {

	public static final int PORT_RETOUR = 2700;
	public static final int PORT_EMPRUNT = 2600;
	public static final int PORT_RESERVATION = 2500;

	public static void main(String[] args) {
		Set<Document> docs = new HashSet<Document>();
		Set<Abonne> abos = new HashSet<Abonne>();
		docs.add(new Livre("Moby Dick", "Herman Melville"));
		docs.add(new Livre("Les Aventures de Tom Sawyer", "Mark Twain"));
		docs.add(new Livre("La Republique", "Platon"));
		docs.add(new Livre("Call Of Cthulu", "H.P. Lovecraft"));
		abos.add(new AbonneStandard("FROMENT", "Sacha"));
		abos.add(new AbonneStandard("GROS DUBOIS", "Thomas"));
		abos.add(new AbonneStandard("MACE", "Loic"));
		abos.add(new AbonneStandard("MACRON", "Emmanuel"));
		Bibliotheque b = new Bibliotheque("François Mitterand", docs, abos);

		System.out.println("-- Demarage des serveurs");
		new Serveur(PORT_RETOUR, FabriqueService.TYPE_RETOUR, b).start();
		new Serveur(PORT_EMPRUNT, FabriqueService.TYPE_EMPRUNT, b).start();
		new Serveur(PORT_RESERVATION, FabriqueService.TYPE_RESERVATION, b).start();
	}

}
