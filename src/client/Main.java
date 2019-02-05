package client;

import java.util.Scanner;

public class Main {

	public static final int PORT_RETOUR = 2700;
	public static final int PORT_EMPRUNT = 2600;
	public static final int PORT_RESERVATION = 2500;
	public final static String DEST = "localhost";

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		int port = PORT_EMPRUNT;
		System.out.print("A quel service desirez vous vous connecter :\n\t- emprunt\n\t- retour\n\t- reservation\n$ ");
		boolean input = true;
		while (input) {
			String s = sc.nextLine().toLowerCase();
			switch (s) {
			case "emprunt":
				port = PORT_EMPRUNT;
				break;
			case "retour":
				port = PORT_RETOUR;
				break;
			case "reservation":
				port = PORT_RESERVATION;
				break;
			default:
				continue;
			}
			input = false;
		}
		new Client(DEST, port).start();
	}

}
