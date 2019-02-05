package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public interface Service extends Runnable {

	void start();
	
	void routine(BufferedReader socketIn, PrintWriter socketOut) throws IOException;
}
