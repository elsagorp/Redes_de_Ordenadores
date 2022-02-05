package TCP;

import java.net.*;
import java.io.*;
import java.util.Vector;

public class TCPCliente {
	public static String datos(Vector<Double> times) {
		double sum = 0, media = 0, desv = 0;
		String s = "\n";
		for(int i=0; i < times.size(); i++) {
			sum = sum + times.elementAt(i);
		}
		media = sum/times.size();
		s = s +("La media de tiempos de " + times.size() +" request es: " + media + "\n");
		for(int i=0; i < times.size(); i++) {
			desv = desv + (times.elementAt(i)-media)*(times.elementAt(i)-media);
		}
		desv = Math.sqrt(desv/times.size());
		s = s +("La desviación tipica de tiempos de " + times.size() +" request es: " + desv);
		return s;
	}
	public static void main (String args[]) throws FileNotFoundException{
		Socket s = null;
		Vector<Double> times = new Vector<Double> ();
		String idFiche= "LatenciaTCP.txt";
		PrintWriter ficheroSal = new PrintWriter(idFiche);
		try{
			int serverPort = 7896;   
			long inicio = System.currentTimeMillis();
			for(int i = 0; i < 1010; i++) {
				s = new Socket("localhost", serverPort); 
				DataInputStream in = new DataInputStream( s.getInputStream());
				DataOutputStream out =new DataOutputStream( s.getOutputStream());
				long ini = System.nanoTime();
				out.writeUTF("Hola que tal");      	
				String data = in.readUTF();	
				long fin = System.nanoTime();
				double latencia = (double) (fin - ini);
				times.addElement(latencia);
				ficheroSal.println("Recibido: "+ data + " en " + latencia + " ns"); 

			}
			long fin = System.currentTimeMillis();
			long time = fin-inicio;
			ficheroSal.println();
			ficheroSal.print("Tarda " +time + "ms en realizar 1010 request ");
			ficheroSal.print(datos(times));

			ficheroSal.close();

		}catch (UnknownHostException e){System.out.println("Socket:"+e.getMessage());
		}catch (EOFException e){System.out.println("EOF:"+e.getMessage());
		}catch (IOException e){System.out.println("readline:"+e.getMessage());
		}finally {if(s!=null) try {s.close();}catch (IOException e){System.out.println("close:"+e.getMessage());}}
	}

}
