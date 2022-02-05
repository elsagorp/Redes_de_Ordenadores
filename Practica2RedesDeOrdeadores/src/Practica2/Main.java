package Practica2;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

public class Main {
	private int vertices;
	private int[][] graph;

	public Main(int tam) {
		this.vertices = tam;
		this.graph = new int [tam][tam];
	}
	void inputGraph(String txtfile) throws FileNotFoundException {
		File ficheroEntrada = new File (txtfile);

		try { 
			Scanner datosFichero = new Scanner(ficheroEntrada);
			int i = 0;
			while (i < this.vertices) {
				String []numerosFichero = datosFichero.next().split(",");
				for (int j=0; j < numerosFichero.length; j++) {
					this.graph[i][j] = Integer.parseInt(numerosFichero[j]);
				}

				i++;
			}  
			datosFichero.close();
		} catch(FileNotFoundException e) {

			System.out.println("Error: fichero no encontrado");
			System.out.println(e.getMessage());

		}catch (Exception  e) {

			System.out.println("Error de lectura del fichero");
			System.out.println(e.getMessage());

		} 

	}

	int getVertices() {
		return this.vertices;
	}	

	public int[][] getGraph() {
		return graph;
	}
	public static void main(String[] args) throws FileNotFoundException{
		String text = "Grafo.txt";
		
		Main sim = new Main(6);
		sim.inputGraph(text);
		
		Grafo grafo = new Grafo(sim.getVertices(), sim.getGraph());
		
		Dijkstra algori = new Dijkstra(grafo,3,0);
		Inundacion algor = new Inundacion(grafo,4,0);
		VectorDistancia algorithm = new VectorDistancia(grafo);
		
		System.out.println(grafo.toString());

		for(int i=0; i < sim.getGraph().length; i++) {
			Ruter r = new Ruter(i, grafo);
			r.setInternTable();
			algorithm.addRouters(r);
		}
		algori.dijk();
		algor.inun();
		algorithm.applyDistanceVector();

	}


}

