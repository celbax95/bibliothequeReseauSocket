package bibliotheque;

import java.util.Date;

public interface Abonne {

    int numero();

    String nom();

    String prenom();
        
	void interdire(Date d);

	boolean estinterdit();

}
