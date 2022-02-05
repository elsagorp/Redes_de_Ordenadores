package Practica2;

public class VectorDistancia {
	private int matrizDistancia[][];
    private int matrizSgteSalto[][];
    private int n;
    private Grafo grafo;
    private Nodo[] listaAdy;

    /**
     * 
     * Metodo constructor
     * Matriz distancia contiene los vectores distancia de todos los routers de la topologia
     * Matriz sgte salto contiene los vectores con informacion del sgte salto de cada router para llegar a todos los demas routers
     * @param grafo Grafo no dirigido
     */
    public VectorDistancia(Grafo grafo){
        this.n = grafo.getN();
        this.grafo = grafo;
        matrizDistancia = new int[n][n];
        matrizSgteSalto = new int[n][n];
        listaAdy = grafo.getListaAdy();
    }
    
    /**
     * 
     * Este metodo inicia los atributos de la clase
     * Todas las distancias se incian a 1000 para representar una distancia infinita
     * Todos los siguientes saltos se inician en -1 para representar que no se conoce el sgte salto
     */
    public void inicializarMatrices(){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                matrizDistancia[i][j] = 1000;
                matrizSgteSalto[i][j] = -1;
            }
            matrizDistancia[i][i] = 0;
        }
    }
    
    /**
     * 
     * Este metodo imprime las tablas con informacion de distancias y siguientes saltos de todos los routers en consola
     */
    public void mostrarMatrices(){
        for(int i=0; i<n; i++) {
            System.out.println("Router " + i + ":");
            System.out.println("Nodos   Dist.   Sgte.");
            for (int j = 0; j < n; j++) {
                System.out.println(j + "       " + matrizDistancia[i][j] + "       "
                        + matrizSgteSalto[i][j]);
                //JOptionPane.showMessageDialog(null, j + "       " + matrizDistancia[i][j] + "       "
                      //  + matrizSgteSalto[i][j]);
            }
        }
    }

    
    /**
     * 
     * Este metodo ejecuta toda la logica necesaria para calcular los vectores distancia, de todos los routers simultaneamente
     */
    public void ejecutarVector(){
        for(int i=0; i<n-1; i++){
            //Calculamos en cada paso i el vector distancia de cada router
            
            for(int v=0; v<matrizDistancia.length; v++){//Para cada router:
                    Nodo temp = listaAdy[v];
                    //Recorremos solo los lados conectados al router v
                    while(temp != null){
                        for(int w=0; w<matrizDistancia[temp.getDestino()].length; w++){
                            //Recorremos el vector distancia unicamente de los routers conectados al router v
                            if(temp.getPeso() + matrizDistancia[temp.getDestino()][w] < matrizDistancia[v][w]){
                                //Si encontramos una ruta menor a traves de un router conectado al router v, actualizamos el vector distancia
                                matrizDistancia[v][w] = temp.getPeso() + matrizDistancia[temp.getDestino()][w];
                                matrizSgteSalto[v][w] = temp.getDestino();
                            }
                        }
                        temp = temp.getSig();
                    }
            }
            //Opcional: mostrar vectores en el tiempo i
            //System.out.println("--------------");
            //System.out.println("T = " + i+"\n");
            //mostrarMatrices();
        }
    }

    
    public int[][] getMatrizDistancia() {
        return matrizDistancia;
    }

    public int[][] getMatrizSgteSalto() {
        return matrizSgteSalto;
    }

    public int getN(){
        return n;
    }
    
    public boolean hayRoutersSinConex(){
        return this.grafo.hayRoutersSinConex();
    }
    
}
