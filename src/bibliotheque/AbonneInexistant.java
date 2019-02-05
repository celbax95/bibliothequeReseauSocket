package bibliotheque;

public class AbonneInexistant extends Exception {

    private static final long serialVersionUID = 1;

    public AbonneInexistant(String message) {
        super(message);
    }
}
