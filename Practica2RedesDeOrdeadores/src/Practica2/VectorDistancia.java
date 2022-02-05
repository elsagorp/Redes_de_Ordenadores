package Practica2;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class VectorDistancia {
	private Grafo grafo;
	private List<Ruter> routers ;

	public VectorDistancia(Grafo grafo) {
		this.grafo = grafo;
		this.routers = new ArrayList<Ruter>();
	}
	public void addRouters(Ruter r) {
		routers.add(r);
	}

	public void applyDistanceVector() throws  FileNotFoundException {
		for(int iteracion=0; iteracion<routers.size(); iteracion++) {
			for(int i=0; i<routers.size(); i++) {
				sendNeighbours(routers.get(i).getId());
			}
			for(int r=0; r<routers.size(); r++) {
				routers.get(r).actualizate();
			}
		}
		printVectorDist();
	}

	public void sendNeighbours(int idRouter) {
		for(int i=0; i<routers.size(); i++) {
			if(idRouter != routers.get(i).getId()){ 			
				if(routers.get(idRouter).isNeigh(routers.get(i).getId())) {	
					int[][]aux = routers.get(i).getNeighbours();	
					for(int row=0; row<aux.length; row++){
						if(row != idRouter)
							for(int col=0; col<aux.length; col++) {		
								if(col != idRouter) { 					
									if(aux[row][col] != 0) {			
										int value = aux[row][col];		
										routers.get(idRouter).actualizateTables(routers.get(i).getId(),row, col, value); 
									}													
								}
							}
					}
				}
			}
		}
	}

	public void printVectorDist() throws FileNotFoundException  {
		String idFichero= "VectorDist.txt";
		PrintWriter ficheroSalida = new PrintWriter(idFichero);
		for(int i=0; i<routers.size(); i++) {
			ficheroSalida.println("Enrutador: " + i);ficheroSalida.println();
			int[][]aux = routers.get(i).getMinValues();
			for(int row=0; row<routers.size(); row++) {
				for(int col=0; col<routers.size(); col++) {
					ficheroSalida.print(" " +aux[row][col] + " | ");
				}
				ficheroSalida.println();
			}
			ficheroSalida.println();			
		}
		ficheroSalida.close();
	}
}
