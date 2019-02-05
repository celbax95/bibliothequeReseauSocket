package bibliotheque;

public class DocumentInexistant extends Exception {

	private static final long serialVersionUID = 1;

	public DocumentInexistant(String message) {
		super(message);
	}
}
