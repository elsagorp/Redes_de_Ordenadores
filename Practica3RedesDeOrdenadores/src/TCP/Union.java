package TCP;
import java.io.IOException;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Union extends Thread{
	private DataInputStream in;
	private DataOutputStream out;
	private Socket clientSocket;
	
	public Union(Socket clientSocket) { //Inicializamos conexion con un socket
		try {
			this.clientSocket = clientSocket;
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			System.out.println("Conexion: " + e.getMessage()); //Fallo
		}
	}
	
	
	public void run() {
		try {
			String data = in.readUTF();
			System.out.println("RECIBIDO: " + data); //mensaje de recepcion del cliente
			out.writeUTF(data); 
		} catch (IOException e) {
			System.out.println("readline: " + e.getMessage()); //manda el mensaje del cliente de vuelta al cliente 
		}finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				/*close failed*/
			}
		}
		
	}
}
