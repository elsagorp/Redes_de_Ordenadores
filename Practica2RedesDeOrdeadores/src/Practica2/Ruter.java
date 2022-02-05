package Practica2;

public class Ruter {
	private int[][] tabla;
	private int[][] transicion;
	private int[][] tablavalBaj;
	private int ruterId;
	private Grafo grafo;

	
	public Ruter(int ruterId, Grafo grafo) {
		this.ruterId = ruterId;
		this.grafo = grafo;
		this.tabla = new int[this.grafo.getNumberOfNodes()][this.grafo.getNumberOfNodes()];
		this.transicion = new int[this.grafo.getNumberOfNodes()][this.grafo.getNumberOfNodes()];
		this.tablavalBaj = new int[this.grafo.getNumberOfNodes()][this.grafo.getNumberOfNodes()];
		
		
		for(int i=0; i < this.grafo.getNumberOfNodes(); i++) {
			for(int j=0; j < this.grafo.getNumberOfNodes(); j++) {
				this.tabla[i][j] = 0;
			}
		}
		for(int i=0; i < this.grafo.getNumberOfNodes(); i++) {
			for(int j=0; j < this.grafo.getNumberOfNodes(); j++) {
				this.tablavalBaj[i][j] = 0;
			}
		}
		for(int i=0; i < this.grafo.getNumberOfNodes(); i++) {
			for(int j=0; j < this.grafo.getNumberOfNodes(); j++) {
				this.transicion[i][j] = 0;
			}
		}
	}
	
	
	public int getMinValue(int i, int j){
		return this.tablavalBaj[i][j];
	}
	public int[][] getNeighbours() {
		return this.tabla;
	}

	public int[][] getMinValues() {
		return this.tablavalBaj;
	}
	
	public int getVertices() {
		return this.tabla.length;
	}
	
	public int getId() {
		return this.ruterId;
	}
	
	public void actualizate() {
		for(int i=0; i < this.grafo.getNumberOfNodes(); i++) {
			for(int j=0; j < this.grafo.getNumberOfNodes(); j++) {
				this.tabla[i][j] = this.transicion[i][j];
			}
		}
	}
	public void setInternTable() {
		for(int col = 0; col < grafo.getNumberOfNodes(); col++) {
			if(grafo.tieneCost(ruterId, col)){
				this.tabla[col][col] = grafo.getNode(ruterId, col);
			}
		}
		for(int i=0; i < this.grafo.getNumberOfNodes(); i++) {
			for(int j=0; j < this.grafo.getNumberOfNodes(); j++) {
				this.transicion[i][j] = this.tabla[i][j];
			}
		}
		setLessValues();
	}
	public void setLessValues() {		
		for(int i=0; i < this.grafo.getNumberOfNodes(); i++) {
			int aux = 100000000; int row=0, col=0; boolean b=false;
			for(int j=0; j < this.grafo.getNumberOfNodes(); j++) {
				if(this.tabla[i][j] != 0) {
					if(this.tabla[i][j] <= aux) {
						b=true;
						row=i; 
						col=j; 
						aux=this.tabla[i][j];						
					}
				}
			}
			if(b) this.tablavalBaj[row][col] = aux; //!
		}
	}
	

	public boolean isNeigh(int id) {
		for(int i = 0; i < this.grafo.getNumberOfNodes(); i++) {
			if(tabla[i][id] != 0){
				return true;
			}
		}
	    return false;
	}
	
	
	public void actualizateTables(int id, int i, int j, int value) {
		if(transicion[i][id] != 0) {
			if(this.transicion[id][id] + value < transicion[i][id]) {
				this.transicion[i][id] = this.transicion[id][id] + value;
			}
		}else {
		this.transicion[i][id] = this.transicion[id][id] + value; 	
		}
		boolean encontrado = false;
		
		for(int col = 0; col < this.tablavalBaj.length; col++) {						
			if(!encontrado && this.tablavalBaj[i][col] != 0) {
				if(this.transicion[i][id] < this.tablavalBaj[i][col]) {
					this.tablavalBaj[i][id] = this.transicion[i][id];
					this.tablavalBaj[i][col] = 0;
					encontrado = true;
				}
				if(this.transicion[i][id] >= this.tablavalBaj[i][col]) {
					encontrado = true;
				}
			}
		}
		if(!encontrado) {
				this.tablavalBaj[i][id] = this.transicion[i][id];
		}	
	}

	
}
