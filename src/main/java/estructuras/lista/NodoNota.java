package estructuras.lista;

import modelo.Nota;

public class NodoNota {

    Nota dato;
    NodoNota siguiente;

    //Nodo para actualizar la ubicacion del nodo
    public NodoNota(Nota dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}
