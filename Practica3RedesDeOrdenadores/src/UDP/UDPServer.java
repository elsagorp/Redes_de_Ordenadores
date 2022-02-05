package UDP;

import java.net.*;
import java.io.*;

public class UDPServer{
	public static void main(String args[]) throws Exception {
		DatagramSocket aSocket = null;
		try{
			int cont = 0;
			aSocket = new DatagramSocket(6789);
			byte[] buffer = new byte[1000];
			while(true){
				cont ++;
				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
				aSocket.receive(request);     
				DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
				aSocket.send(reply);
				if(cont == buffer.length) break;
			}
		}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
		}catch (IOException e) {System.out.println("IO: " + e.getMessage());
		}finally {if(aSocket != null) aSocket.close();}
	}
}