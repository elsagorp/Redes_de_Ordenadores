package Practica2;

public class Nodo {
	    private int origen, destino;
	    private int peso;
	    private Nodo sig;

	    /**
	     * Metodo constructor
	     * @param x Router origen del enlace
	     * @param y Router destino del enlace
	     * @param peso Peso del enlace
	     */
	    public Nodo(int x, int y, int peso){
	       this.origen = x;
	       this.destino = y;
	       this.peso = peso;
	       sig = null;
	    }

	    public int getOrigen() {
	        return origen;
	    }

	    public void setOrigen(int origen) {
	        this.origen = origen;
	    }

	    public int getDestino() {
	        return destino;
	    }

	    public void setDestino(int destino) {
	        this.destino = destino;
	    }

	    public int getPeso() {
	        return peso;
	    }

	    public void setPeso(int peso) {
	        this.peso = peso;
	    }

	    public Nodo getSig() {
	        return sig;
	    }

	    public void setSig(Nodo sig) {
	        this.sig = sig;
	    }
	}
