package abonne;

import java.util.Date;

import bibliotheque.Abonne;

public class AbonneStandard implements Abonne {

	private static int cptNum = 0;
	private int numero;
	private String nom, prenom;
	private Date interdit;

	public AbonneStandard(String nom, String prenom) {
		numero = cptNum++;
		this.nom = nom;
		this.prenom = prenom;
		this.interdit = null;
	}

	@Override
	public int numero() {
		return numero;
	}

	public String nom() {
		return nom;
	}

	public String prenom() {
		return prenom;
	}

	@Override
	public void interdire(Date d) {
		this.interdit = d;
	}

	@Override
	public boolean estinterdit() {
		if (interdit == null)
			return false;
		else {
			if (interdit.before(new Date())) {
				this.interdit = null;
				return false;
			} else
				return true;
		}
	}

}
