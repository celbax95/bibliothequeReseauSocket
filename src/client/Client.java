package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable {

	private final static String INTSIG = "INT";
	private Socket s;
	private String host;
	private int port;
	private Scanner sc;

	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void start() {
		new Thread(this).start();
	}

	public static String INTSIG() {
		return INTSIG;
	}

	@Override
	public void run() {
		try {
			sc = new Scanner(System.in);
			s = new Socket(host, port);
			System.out.println("-- Connexion etablie avec " + s.getInetAddress() + ":" + s.getPort());
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(s.getInputStream()));
			PrintWriter socketOut = new PrintWriter(s.getOutputStream(), true);
			StringBuilder sb = null;
			String in = null, tmp = null;
			while (true) {
				sb = new StringBuilder();
				while (true) {
					tmp = socketIn.readLine();
					if (tmp.equals(INTSIG) || tmp.equals(""))
						break;
					sb.append(tmp+"\n");
				}
				if (tmp.equals(INTSIG))
					break;
				in = sb.toString();
				System.out.print(in);
				System.out.print("$ ");
				String out = sc.nextLine();
				socketOut.println(out);
			}
			s.close();
			sc.close();
			System.out.println("x- Client termine");
		} catch (Exception e) {
			System.err.println("x- Exception, fermeture du client");
			e.printStackTrace();
		}
		System.exit(-1);
	}
}
