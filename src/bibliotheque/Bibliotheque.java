package bibliotheque;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Bibliotheque {

	private String nom;
	private Map<Integer, Document> docs;
	private Map<Integer, Abonne> abonnes;
	private Map<Integer, Integer> indisponible;
	private static final int NB_JOURS_EMPRUNT = 14;
	private static final int NB_JOURS_INTERDIT = 30;

	public Bibliotheque(String nom, Set<Document> docs, Set<Abonne> abonnes) {
		this.nom = nom;
		this.docs = initDocs(docs);
		this.abonnes = initAbonnes(abonnes);
		this.indisponible = new HashMap<>();
	}

	public void emprunter(int numDoc, int numAb)
			throws PasLibreException, AbonneInexistant, DocumentInexistant, AbonneInterdit {
		if (!docs.containsKey(numDoc))
			throw new DocumentInexistant("Le numero specifie ne correspond a aucun document existant.");
		if (!abonnes.containsKey(numAb))
			throw new AbonneInexistant("Le numero specifie ne correspond a aucun abonne existant.");
		Document d = docs.get(numDoc);
		synchronized (d) {
			if (abonnes.get(numAb).estinterdit())
				throw new AbonneInterdit("Abonne interdit d'emprunt.");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_YEAR, NB_JOURS_EMPRUNT);
			Date date = calendar.getTime();
			d.emprunter(abonnes.get(numAb), date);
			if (!indisponible.containsKey(numDoc))
				indisponible.put(numDoc, numAb);
		}
	}

	public void reserver(int numDoc, int numAb)
			throws PasLibreException, AbonneInexistant, DocumentInexistant, AbonneInterdit {
		if (!docs.containsKey(numDoc))
			throw new DocumentInexistant("Le numero specifie ne correspond a aucun document existant.");
		if (!abonnes.containsKey(numAb))
			throw new AbonneInexistant("Le numero specifie ne correspond a aucun abonne existant.");
		Document d = docs.get(numDoc);
		synchronized (d) {
			if (abonnes.get(numAb).estinterdit())
				throw new AbonneInterdit("Abonne interdit de reservation.");
			d.reserver(abonnes.get(numAb));
			if (!indisponible.containsKey(numDoc))
				indisponible.put(numDoc, numAb);
		}
	}

	public void retourner(int numDoc, boolean bonEtat) throws DocumentInexistant, RetourInutile {
		if (!abonnes.containsKey(numDoc))
			throw new DocumentInexistant("Le numero specifie ne correspond a aucun document existant.");
		if (!indisponible.containsKey(numDoc))
			throw new RetourInutile("Le document n'est ni reserve ni emprunte.");
		Document d = docs.get(numDoc);
		synchronized (d) {
			if (!bonEtat || d.enRetard()) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.DAY_OF_YEAR, NB_JOURS_INTERDIT);
				Date date = calendar.getTime();
				abonnes.get(indisponible.get(numDoc)).interdire(date);
			}
			d.retour();
			if (indisponible.containsKey(numDoc))
				indisponible.remove(numDoc);
		}
	}

	private Map<Integer, Abonne> initAbonnes(Set<Abonne> ab) {
		HashMap<Integer, Abonne> m = new HashMap<>();
		for (Abonne a : ab)
			m.put(a.numero(), a);
		return m;
	}

	private Map<Integer, Document> initDocs(Set<Document> docs) {
		HashMap<Integer, Document> m = new HashMap<>();
		for (Document d : docs)
			m.put(d.numero(), d);
		return m;
	}

	public String nom() {
		return nom;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Bibliotheque " + nom() + "\nAbonnes : \n");
		for (Abonne a : abonnes.values())
			sb.append("Abonne " + a.numero() + " : " + a.nom() + " " + a.prenom() + "\n");
		sb.append("Nombre de documents : " + docs.size());
		return sb.toString();
	}

}
