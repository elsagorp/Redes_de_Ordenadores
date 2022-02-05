package Practica2;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class Inundacion {
	private Grafo grafo;
	private int nodoinicid;
	private int nodofinid;
	private int[] yavisitado;
	private ArrayList<ArrayList<Integer>> paquetesRecibidos;  
	private ArrayList<ArrayList<Integer>> paquetesGenerados;	
	private int tiempovida;


	public Inundacion(Grafo network, int nodoinicid, int nodofinid) { 
		this.grafo = network;
		this.nodoinicid = nodoinicid;
		this.nodofinid = nodofinid;
		this.yavisitado = new int [network.getNumberOfNodes()];
		this.paquetesRecibidos = new ArrayList<ArrayList<Integer>>();
		this.paquetesGenerados = new ArrayList<ArrayList<Integer>>();
		this.tiempovida = sacartiempo();


		for(int i = 0; i < network.getNumberOfNodes() ; i++) {
			yavisitado[i]= 0;
		}

		for(int j = 0; j < this.tiempovida; j++) {
			ArrayList<Integer> a1 = new ArrayList<Integer>();
			ArrayList<Integer> a2 = new ArrayList<Integer>();
			for(int i = 0; i < grafo.getNumberOfNodes() ; i++) {
				a1.add(0);
				a2.add(0);
			}
			paquetesRecibidos.add(a1);
			paquetesGenerados.add(a2);
		}
	}


	private int sacartiempo() {
		int tiempo= 0;
		for(int i = 0; i < grafo.getNumberOfNodes() ; i++) {
			for(int j = 0; j < grafo.getNumberOfNodes() ; j++) {
				if(grafo.getNode(i, j)==grafo.getNode(j, i)) {
					if(i <= j) {
						tiempo = tiempo + grafo.getNode(i, j);
					}
				}
			}
		}
		return tiempo;
	}

	public int vecinosInun(int router, int iterar) { 
		int num = 0; 
		int x = 0;
		int y = 0;
		int result = router;
		for(int routerVisitado = 0; routerVisitado < grafo.getNumberOfNodes(); routerVisitado++) {
			if( grafo.tieneCost(router, routerVisitado) && yavisitado[routerVisitado]== 0) {
				num = num + 1;
				paquetesGenerados.get(iterar-1).remove(router);
				paquetesGenerados.get(iterar-1).add(router, num);

				if(iterar >= grafo.getNode(router, routerVisitado)) {
					if(iterar == grafo.getNode(this.nodoinicid, routerVisitado)) {
						paquetesRecibidos.get(iterar-1).remove(routerVisitado);
						paquetesRecibidos.get(iterar-1).add(routerVisitado, 1);
						yavisitado[routerVisitado] = 1;
						result = routerVisitado;
					}else {
						if(grafo.tieneCost(router, this.nodoinicid)) {
							x = (grafo.getNode(this.nodoinicid,router) + grafo.getNode(router,routerVisitado));
							if(iterar == x){
								paquetesRecibidos.get(iterar-1).remove(routerVisitado);
								paquetesRecibidos.get(iterar-1).add(routerVisitado, 1);
								yavisitado[routerVisitado] = 1;
								result = routerVisitado;
							}
						}

						for(int i = 0; i < grafo.getNumberOfNodes() ; i++) {
							if(yavisitado[i]== 1 && grafo.tieneCost(router, i) && grafo.tieneCost(this.nodoinicid, i)) {
								y = (grafo.getNode(this.nodoinicid,i) + grafo.getNode(router,routerVisitado)) + grafo.getNode(router,i);
								if(iterar == y){
									paquetesRecibidos.get(iterar-1).remove(routerVisitado);
									paquetesRecibidos.get(iterar-1).add(routerVisitado, 1);
									yavisitado[routerVisitado] = 1;
									result = routerVisitado;
								}
							}
						}
					}
				}

			}

		}
		return result;
	}

	public void inun() throws FileNotFoundException{
		int iteraciones = 1;
		int nodo = this.nodoinicid;
		yavisitado[this.nodoinicid]= 1;
		while(iteraciones <= this.tiempovida ) {
			for(int i = 0; i < grafo.getNumberOfNodes() ; i++) {
				if(yavisitado[i]== 1) {
					nodo =vecinosInun(i, iteraciones);
					if(nodo == this.nodofinid) {
						break;	
					}
				}
			}
			if(nodo == this.nodofinid) {
				break;	
			}
			iteraciones++;

		}
		int s = paquetesRecibidos.size()-1;
		for(int i = s; i >= iteraciones; i--) {
			paquetesRecibidos.remove(i);
			paquetesGenerados.remove(i);
		}

		printRouterFinalTablesInundacion();

	}



	public void printRouterFinalTablesInundacion() throws FileNotFoundException  {
		String idFiche= "Inundacion.txt";
		int total = 0;
		PrintWriter ficheroSal = new PrintWriter(idFiche);
		ficheroSal.print("\nOrigen: " +this.nodoinicid +" \n"); 
		ficheroSal.print("\nDestino: " +this.nodofinid +" \n");
		ficheroSal.print("\nPaquetes recibidos: \n");  
		for(int i=0; i<paquetesRecibidos.size(); i++) {
			ficheroSal.print( " Iteracion "+ (i+1) + ": ");
			for(int j=0; j<paquetesRecibidos.get(i).size(); j++) {
				ficheroSal.print( paquetesRecibidos.get(i).get(j) + "|");
				total = total + paquetesRecibidos.get(i).get(j);
			}
			ficheroSal.print( "\n ");
		}
		ficheroSal.println();
		ficheroSal.print("\nPaquetes generados: \n");
		for(int i=0; i<paquetesGenerados.size(); i++) {
			ficheroSal.print( " Iteracion "+ (i+1) + ": ");
			for(int j=0; j<paquetesGenerados.get(i).size(); j++) {
				ficheroSal.print( paquetesGenerados.get(i).get(j) + "|");

			}
			ficheroSal.print(  "\n");
		}
		ficheroSal.print(  "\n");
		ficheroSal.print("\nPaquetes recibidos en total:" + total + "\n");
		ficheroSal.close();
	}
}
