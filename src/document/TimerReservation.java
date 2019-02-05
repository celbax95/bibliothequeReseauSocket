package document;

import java.util.TimerTask;

import bibliotheque.Document;

public class TimerReservation extends TimerTask {

	private Document doc;

	public TimerReservation(Document d) {
		doc = d;
	}

	@Override
	public void run() {
		doc.retour();
	}

}
