package Practica2;

public class Grafo {
	private int[][] adjMatrix;
    private final int numVertices;

    public Grafo(int numVertices, int[][] adj) {
        this.numVertices = numVertices;
        this.adjMatrix = adj;
    }

    public void addNode(int i, int j, int value) {
        adjMatrix[i][j] = value;
    }

    public void removeNode(int i, int j) {
        adjMatrix[i][j] = 0;
    }

    public boolean tieneCost(int i, int j) {
        return adjMatrix[i][j] != 0;
    }
    
    public int getNumberOfNodes() {
    	return numVertices;
    }
    
    public int getNode(int i, int j) {
    	return adjMatrix[i][j];
    }

    public String toString() {
        String s = "Grafo \n";
        for (int i = 0; i < this.numVertices; i++){
            s= s + (i + ": ");
            for(int j: adjMatrix[i]){
                s= s + (j + " ");
            }
            s= s +("\n");
        }
        return s;
    }
}
