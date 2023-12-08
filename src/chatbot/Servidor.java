package chatbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor implements Runnable {

	private Socket clientSocket;

	public Servidor(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public static void main(String[] args) {
		try {

			ServerSocket serverSocket = new ServerSocket(5001);
			System.out.println("Servidor de chat iniciado");

			int i = 0;

			while (true) {

				Socket clientSocket2 = serverSocket.accept();
				System.out.println("Cliente conectado desde " + clientSocket2.getInetAddress());
				i++;
				new Thread(new Servidor(clientSocket2), "Cliente" + i).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		String inputLine;

		try {
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out.println("Bienvenido al servidor");
			out.println("Elija entre las siguientes opciones: ");
			out.println("1. Hola, ¿cómo estás?");
			out.println("2. ¿Qué tiempo crees que hará hoy?");
			out.println("3. Dime tu comida favorita.");
			out.println("4. ¿Qué planes tienes para hoy?");
			out.println("5. ¿Tienes amigos?");

			while ((inputLine = in.readLine()) != null) {
				int eleccion = Integer.parseInt(inputLine);
				switch (eleccion) {
				case 1:
					System.out.println("Me encuentro bien, gracias por preguntar :) .");
					break;
				case 2:
					System.out.println("Por desgracia no puedo decirlo con certeza, pero seguramente haga frío.");
					break;
				case 3:
					System.out.println(
							"Aunque no puedo reconocer sabores ni comer, siempre he sentido curiosidad por la pasta");
					break;
				case 4:
					System.out.println("Atender al resto de usuarios que se conecten.");
					break;
				case 5:
					System.out.println("Todos los usuarios que os conectais os considero como tales.");
					break;
				default:
					System.out.println("Por favor, elija un número de los indicados en pantalla.");
					break;
				}

				if (inputLine.equals("q")) {
					clientSocket.close();
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
