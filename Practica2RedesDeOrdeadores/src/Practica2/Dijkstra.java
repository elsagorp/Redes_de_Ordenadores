package Practica2;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.PriorityQueue;

public class Dijkstra {
	private Grafo grafo;
	private int nodoinicid;
	private int nodofinid;
	private int[] yavisitado;
	private int[] camino;
	private int[][] matrizcostes;  
	private int[][] matrizconsolid; 	 
	private int costetotal;

	public Dijkstra(Grafo network, int nodoinicid, int nodofinid) { 
		this.grafo = network;
		this.nodoinicid = nodoinicid;
		this.nodofinid = nodofinid;
		this.yavisitado = new int [grafo.getNumberOfNodes()];
		this.camino = new int [grafo.getNumberOfNodes()];
		this.matrizcostes = new int[this.grafo.getNumberOfNodes()][this.grafo.getNumberOfNodes()];
		this.matrizconsolid  = new int[this.grafo.getNumberOfNodes()][this.grafo.getNumberOfNodes()];


		for(int i = 0; i < grafo.getNumberOfNodes() ; i++) {
			yavisitado[i]= 0;
		}
		for(int i = 0; i < grafo.getNumberOfNodes() ; i++) {
			camino[i]= -1;
		}

		for(int i = 0; i < grafo.getNumberOfNodes() ; i++) {
			for(int j = 0; j < grafo.getNumberOfNodes() ; j++) {
				matrizcostes[i][j]= 0;
				matrizconsolid[i][j]= 0;
			}
		}
	}


	public int camino(int router, int iterar) { 
		int num = 0; 
		int y = 0;
		int[] vecinos = new int [grafo.getNumberOfNodes()];
		int result = router;
		for(int routerVisitado = 0; routerVisitado < grafo.getNumberOfNodes(); routerVisitado++) {
			if( grafo.tieneCost(router, routerVisitado) && yavisitado[routerVisitado]== 0 ) {
				vecinos[routerVisitado]= 1;
				if(iterar == 1) {
					matrizcostes[iterar-1][routerVisitado]= grafo.getNode(router, routerVisitado);
				}else {
					matrizcostes[iterar-1][routerVisitado]= grafo.getNode(router, routerVisitado) + matrizcostes[iterar-2][router];
				}
			}else if(iterar > 1){	
				matrizcostes[iterar-1][routerVisitado]= matrizcostes[iterar-2][routerVisitado];
			}
		}
		int comp = 0;
		for(int k = 0; k < grafo.getNumberOfNodes(); k++) {
			if(grafo.tieneCost(router, k)&& yavisitado[k]== 0) {
				comp = matrizcostes[iterar-1][k];
				result = k;
				break;
			}
		}

		for(int i = 1; i < grafo.getNumberOfNodes(); i++) {
			if(matrizconsolid[iterar-1][i]== 0 &&  grafo.tieneCost(router, i) && yavisitado[i]== 0) {
				if(matrizcostes[iterar-1][i] < comp ) {
					comp = matrizcostes[iterar-1][i];
					result = i;
				}
			}
			if(iterar > 1) {
				if(matrizconsolid[iterar-2][i]== 1) matrizconsolid[iterar-1][i]= 1;
			}
		}

		camino[iterar -1] = result;
		matrizconsolid[iterar-1][result]= 1;
		yavisitado[result] = 1;

		return result;
	}

	public void dijk() throws FileNotFoundException{
		int iteraciones = 1;
		int nodo = this.nodoinicid;
		yavisitado[this.nodoinicid]= 1;
		this.matrizconsolid[0][this.nodoinicid]= 1;
		camino[iteraciones-1] = this.nodoinicid;
		nodo =camino(nodo, iteraciones);
		while(iteraciones <= grafo.getNumberOfNodes()) {


			if(matrizconsolid[iteraciones-1][nodo] != 1) {
				nodo =camino(nodo, iteraciones);
				camino[iteraciones-1] = nodo;
			}

			if(nodo == this.nodofinid) {
				break;	
			}
			iteraciones++;

		}

		this.costetotal = matrizcostes[iteraciones-1][this.nodofinid];
		printRouterFinalTablDijks();

	}



	public void printRouterFinalTablDijks() throws FileNotFoundException  {
		String idFiche= "Dijkstra.txt";
		PrintWriter ficheroSal = new PrintWriter(idFiche);
		ficheroSal.print("\nOrigen: " +this.nodoinicid +" \n"); 
		ficheroSal.print("\nDestino: " +this.nodofinid +" \n");
		ficheroSal.print("\nMatriz de costes: \n");  
		for(int i=0; i<grafo.getNumberOfNodes(); i++) {
			ficheroSal.print( " Iteracion "+ (i+1) + ": ");
			for(int j=0; j<grafo.getNumberOfNodes(); j++) {
				ficheroSal.print( matrizcostes[i][j] + "|");
			}
			ficheroSal.print( "\n ");
		}
		ficheroSal.println();
		ficheroSal.print("\nMatriz de consolidados: \n");
		for(int i=0; i<grafo.getNumberOfNodes(); i++) {
			ficheroSal.print( " Iteracion "+ (i+1) + ": ");
			for(int j=0; j< grafo.getNumberOfNodes(); j++) {
				ficheroSal.print( matrizconsolid[i][j] + "|");

			}
			ficheroSal.print(  "\n");
		}
		ficheroSal.print(  "\n");
		ficheroSal.print("\nCoste total de la ruta mas corta:" + this.costetotal + "\n");
		ficheroSal.close();
	}

	public int[] dijkist(int inicio) {
		int[] distancia = new int[this.grafo.getNumberOfNodes() ];
		int[] padre = new int[grafo.getNumberOfNodes()];
		boolean[] visto = new boolean[grafo.getNumberOfNodes()];
		for (int i = 0; i < grafo.getNumberOfNodes(); i++) {
			distancia[i] = 1200000000;
			padre[i] = -1;
			visto[i] = false;
		}
		distancia[inicio]=0;
		PriorityQueue<Integer> pila = new PriorityQueue<>();
		pila.add(distancia[inicio]);
		while (!pila.isEmpty()) {
			int u = pila.poll();
			visto[u] = true;
			for (int i = 0; i < grafo.getNumberOfNodes(); i++) {
				if (grafo.getNode(u, i) != 0) {
					// aqui es donde se debe tratar de editar para que nos incluya el parametro gas que es un arreglo de strings
					if (distancia[i] > distancia[u] + grafo.getNode(u, i) ) {
						distancia[i] = distancia[u] + grafo.getNode(u, i) ;
						padre[i] = u;
						pila.add(i);
					}
				}
			}
		}
		return distancia;
	}

}

