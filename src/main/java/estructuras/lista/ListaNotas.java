package estructuras.lista;

import modelo.Nota;

public class ListaNotas {

    private NodoNota cabeza;

    public ListaNotas() {
        this.cabeza = null;
    }

    public boolean estaVacia(){
        return cabeza == null;
    }

    // Cumple: Insertar al inicio
    public void insertarInicio(Nota n){
        NodoNota nuevo = new NodoNota(n);
        // Uso de setSiguiente() y getSiguiente()
        nuevo.setSiguiente(cabeza);
        cabeza = nuevo;
    }

    // Cumple: Eliminar la primera coincidencia
    public Nota eliminar(int id){ // <-- ¡Cambio de retorno de boolean a Nota!
        if (estaVacia()) return null;

        // Caso 1: Eliminar la cabeza
        if(cabeza.getDato().getId()==id){
            Nota notaEliminada = cabeza.getDato(); // 1. Capturar el objeto a eliminar
            cabeza = cabeza.getSiguiente();
            return notaEliminada;                  // 2. Retornarlo
        }

        NodoNota actual = cabeza;
        // Uso de los getters corregidos (getSiguiente() y getDato())
        while(actual.getSiguiente()!=null && actual.getSiguiente().getDato().getId()!=id){
            actual=actual.getSiguiente();
        }

        if(actual.getSiguiente() == null) return null; // No se encontró

        // Caso 2: Eliminar nodo intermedio/final
        Nota notaEliminada = actual.getSiguiente().getDato(); // 1. Capturar el objeto
        actual.setSiguiente(actual.getSiguiente().getSiguiente());
        return notaEliminada;                            // 2. Retornarlo
    }

    // Cumple: Recorrer para listar
    public void mostrar(){
        if(estaVacia()){
            System.out.println("La lista se encuentra vacia");
            return;
        }

        NodoNota aux = cabeza;

        while(aux!=null){
            // Uso de getDato() y getSiguiente()
            System.out.println("   " + aux.getDato());
            aux = aux.getSiguiente();
        }
    }
}