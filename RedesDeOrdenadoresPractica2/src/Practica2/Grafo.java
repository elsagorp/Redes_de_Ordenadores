package Practica2;

public class Grafo {
    private int n; //numero de vertices
    private Nodo[] listaAdy;
    //El grafo siempre es no dirigido
    
    public Grafo(int size){
        n = size;
        listaAdy = new Nodo[n];
    }
    
    /**
     * 
     * Este metodo permite agregar un lado al grafo, lo que es un enlace entre 2 routers
     * @param x Router origen del enlace
     * @param y Router destino del enlace
     * @param peso Peso del enlace
     */
    
    public boolean hayRoutersSinConex(){
        for(Nodo router: this.listaAdy){
            if(router == null){
                return true;
            }
        }
        return false;
    }
    
    public void agregarLado(int x, int y, int peso){
        Nodo temp = new Nodo(x, y, peso);
        temp.setSig(listaAdy[x]);
        listaAdy[x] = temp;
        
        
        Nodo temp2 = new Nodo(y, x, peso);
        temp2.setSig(listaAdy[y]);
        listaAdy[y] = temp2;
    }
    
    /**
     * 
     * @param x Uno de los routers del enlace
     * @param y El otro router del enlace
     * @return Devuelve el lado conformado por los 2 routers de entrada y el peso del enlace
     */
    public Nodo obtenerLado(int x, int y){
        Nodo temp = listaAdy[x];
        while(temp != null){
            if(temp.getDestino() == y){
                break;
            }
            temp = temp.getSig();
        }
        return temp;
    }
    
    /**
     * 
     * @param x Uno de los routers del enlace o lado
     * @param y El otro router que conforma el enlace
     * @return Verdadero si existe un enlace entre ambos routers, falso de lo contrario
     */
    public boolean existeLado(int x, int y){
        Nodo aux = listaAdy[x];
        while(aux!=null){
            if(aux.getDestino()==y){
                return true;
            }
            aux = aux.getSig();
        }
        return false;
    }

    /**
     * Imprime los lados del grafo en consola
     */
    public void mostrarLados(){
        //ScreenMain menu = new ScreenMain();
        
        for(int i=0; i<listaAdy.length; i++){
            Nodo temp = listaAdy[i];
            while(temp != null){
                //JOptionPane.showMessageDialog(null, "(" + temp.getOrigen() + "," +temp.getDestino()+"): " + temp.getPeso());
                
                System.out.println("(" + temp.getOrigen() + "," +temp.getDestino()+"): " + temp.getPeso());
                temp = temp.getSig();
            }
        }
    }

    /**
     * 
     * @return La lista de adyacencia que representa al grafo
     */
    public Nodo[] getListaAdy() {
        return listaAdy;
    }

    public void setListaAdy(Nodo[] listaAdy) {
        this.listaAdy = listaAdy;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
