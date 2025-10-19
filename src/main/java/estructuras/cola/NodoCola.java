package estructuras.cola;//Indica el paquete que almacena el archivo

// Para cada nodo de la cola
public class Nodo {
    int valor;     // Contador de nodos
    Nodo sig;      // Enlaza un nodo con el que le sigue

    public Nodo(int valor) {//Para incrementar nodos
        this.valor = valor;//Cambiamos el valor actual
        this.sig = null;//Y dejamos el siguiente como nulo
    }
}
