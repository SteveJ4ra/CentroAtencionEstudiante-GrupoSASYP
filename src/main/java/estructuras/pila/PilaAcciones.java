package estructuras.pila;

public class PilaAcciones {
  private NodoPila tope;
    private int size = 0;

    public void push(Accion accion) {
        tope = new NodoPila(accion, tope);
        size++;
    }

    public Accion pop() {
        if (tope == null) return null;
        Accion a = tope.dato;
        tope = tope.siguiente;
        size--;
        return a;
    }

    public Accion peek() {
        return tope == null ? null : tope.dato;
    }

    public boolean estaVacia() {
        return tope == null;
    }

    public int getSize() {
        return size;
    }

    public void limpiar() {
        tope = null;
        size = 0;
    }

}
