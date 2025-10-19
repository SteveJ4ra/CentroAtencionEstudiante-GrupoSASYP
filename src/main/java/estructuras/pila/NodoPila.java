package estructuras.pila;

public class NodoPila {
   Accion dato;
    NodoPila sig;

    public NodoPila(Accion dato) {
        this.dato = dato;
        this.sig = null;
    }
}
