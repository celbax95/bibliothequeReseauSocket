package document;

import java.util.Timer;
import java.util.TimerTask;

import bibliotheque.Document;

public class Reservation {

//	private static final long DUREE_RES = 7200000; // 2h en millisecondes
	private static final long DUREE_RES = 5000;

	private static Timer timer = new Timer();

	public static TimerTask schedule(long length, Document d) {
		TimerTask t = new TimerReservation(d);
		timer.schedule(t, length);
		return t;
	}

	public static void cancel() {
		timer.cancel();
	}

	public static long getDureeRes() {
		return DUREE_RES;
	}

}
