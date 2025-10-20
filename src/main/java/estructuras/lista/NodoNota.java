package estructuras.lista;

import modelo.Nota;

public class NodoNota {
    private Nota dato; // Debe ser private
    private NodoNota siguiente; // Debe ser private

    public NodoNota(Nota dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    // Getters y Setters necesarios para acceder desde ListaNotas
    public Nota getDato() { return dato; }
    public NodoNota getSiguiente() { return siguiente; }
    public void setSiguiente(NodoNota siguiente) { this.siguiente = siguiente; }
}