package bibliotheque;

import java.util.Date;

public interface Document {
	void emprunter(Abonne ab, Date lim) throws PasLibreException;

	int numero();

	void reserver(Abonne ab) throws PasLibreException;

	void retour(); // document rendu ou annulation réservation
	
	boolean enRetard();
}
