package document;

import java.util.Date;
import java.util.TimerTask;

import bibliotheque.Abonne;
import bibliotheque.Document;
import bibliotheque.PasLibreException;

public class Livre implements Document {

	private static int cptNum = 0;

	private int numero;
	private String titre;
	private String auteur;

	private Abonne titulaire;

	private TimerTask reservation;
	private Date emprunt;

	public Livre(String titre, String auteur) {
		numero = cptNum++;
		titulaire = null;
		this.titre = titre;
		this.auteur = auteur;
		reservation = null;
		emprunt = null;
	}

	@Override
	public void emprunter(Abonne ab, Date d) throws PasLibreException {
		if (emprunte()) {
			if (titulaire == ab)
				throw new PasLibreException("Vous avez deja emprunte " + titre + " de " + auteur);
			throw new PasLibreException("Le livre " + titre + " de " + auteur + " est deja emprunte");
		}
		if (reserve() && titulaire != ab)
			throw new PasLibreException("Le livre " + titre + " de " + auteur + " est deja reserve");
		if (reserve()) {
			reservation.cancel();
		}
		reservation = null;
		emprunt = d;
		titulaire = ab;
	}

	@Override
	public int numero() {
		return numero;
	}

	@Override
	public void reserver(Abonne ab) throws PasLibreException {
		if (emprunte()) {
			if (titulaire == ab)
				throw new PasLibreException("Vous avez deja emprunte " + titre + " de " + auteur);
			throw new PasLibreException("Le livre " + titre + " de " + auteur + " est deja emprunte.");
		}
		if (reserve()) {
			if (titulaire == ab)
				throw new PasLibreException("Vous avez deja reserve " + titre + " de " + auteur);
			throw new PasLibreException("Le livre " + titre + " de " + auteur + " est deja reserve.");
		}
		reservation = Reservation.schedule(Reservation.getDureeRes(), this);
		emprunt = null;
		titulaire = ab;
	}

	private boolean reserve() {
		return reservation != null;
	}

	private boolean emprunte() {
		return emprunt != null;
	}

	@Override
	public void retour() {
		if (reserve())
			reservation.cancel();
		titulaire = null;
		reservation = null;
		emprunt = null;
	}

	@Override
	public boolean enRetard() {
		return this.emprunt.before(new Date());
	}
}
