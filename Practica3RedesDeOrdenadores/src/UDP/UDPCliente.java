package UDP;
import java.util.Vector;
import java.io.*;
import java.net.*;

public class UDPCliente{
	public static String datos(Vector<Double> times) {
		double desv = 0;
		double sum = 0;
	    double media = 0;
		String s = "\n";
		for(int i=0; i < times.size(); i++) {
			sum = sum + times.elementAt(i);
		}
		media = sum/times.size();
		s = s +("La media de tiempos de " + times.size() +" request es: " + media + "\n");
		//Calculamos primero la media ya que sin ella no podemos calcular la desviacion
		for(int i=0; i < times.size(); i++) {
			desv = desv + (times.elementAt(i)-media)*(times.elementAt(i)-media);
		}
		desv = Math.sqrt(desv/times.size());
		s = s +("La desviación tipica de tiempos de " + times.size() +" request es: " + desv);
		return s;
	}
	public static void main(String args[]) throws FileNotFoundException{
	
		DatagramSocket aSocket = null;
		Vector<Double> times = new Vector<Double> ();
		String idFiche= "LatenciaUDP.txt";
		PrintWriter fichSal = new PrintWriter(idFiche);
		String dat = "Hola que tal";
		try{
			aSocket = new DatagramSocket();    
			byte [] m = dat.getBytes();
			InetAddress aHost = InetAddress.getLocalHost();
			int serverPort = 6789;		  
			long inicio = System.currentTimeMillis();
			
			for(int i = 0; i < 1010; i++) {
				DatagramPacket request = new DatagramPacket(m,  dat.length(), aHost, serverPort);
				long ini = System.nanoTime();
				aSocket.send(request);			                        
				byte[] buffer = new byte[1000];
				DatagramPacket reply = new DatagramPacket(buffer, buffer.length);	
				aSocket.receive(reply);
				long fin = System.nanoTime();
				double latencia = (double) (fin - ini);
				times.addElement(latencia);
				fichSal.println("Recibido: "+ dat + " en " + latencia + " ns"); 

			}
			long fin = System.currentTimeMillis();
			long time = fin-inicio;
			fichSal.println();
			fichSal.print("Tarda " + time + "ms en realizar 1010 request ");
			fichSal.print(datos(times));

			fichSal.close();
			  

		}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
		}catch (IOException e){System.out.println("IO: " + e.getMessage());
		}finally {if(aSocket != null) aSocket.close();		

	}
}
}
