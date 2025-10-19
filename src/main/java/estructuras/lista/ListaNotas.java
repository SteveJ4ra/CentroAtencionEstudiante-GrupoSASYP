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

    public void insertarInicio(Nota n){
        NodoNota nuevo = new NodoNota(n);
        nuevo.siguiente = cabeza;
        cabeza = nuevo;
    }

    public boolean eliminar(int id){
        if (estaVacia()) return false;

        if(cabeza.dato.getId()==id){
            cabeza=cabeza.siguiente;
            return true;
        }

        NodoNota actual = cabeza;
        while(actual.siguiente!=null && actual.siguiente.dato.getId()!=id){
            actual=actual.siguiente;
        }

        if(actual.siguiente == null) return false;

        actual.siguiente = actual.siguiente.siguiente;
        return true;
    }

    public void mostrar(){
        if(estaVacia()){
            System.out.println("La lista se encuentra vacia");
            return;
        }

        NodoNota aux = cabeza;

        while(aux!=null){
            System.out.println("   " + aux.dato);
            aux = aux.siguiente;
        }
    }
}
