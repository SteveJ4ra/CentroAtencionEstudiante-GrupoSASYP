package estructuras.cola;

import modelo.Ticket;
import modelo.Estado; // Necesario para el estado inicial

public class QuequeCAE {
    private NodoCola frente; // Inicio de la cola (por donde se saca)
    private NodoCola fin;    // Fin de la cola (por donde se inserta)
    private int tamanio;

    // Insertar un nuevo ticket al final de la cola
    public void enqueque(Ticket ticket) {
        NodoCola nuevo = new NodoCola(ticket);
        if (estaVacia()) {
            frente = nuevo;
        } else {
            fin.setSiguiente(nuevo);
        }
        fin = nuevo; // El nuevo nodo siempre es el nuevo fin
        tamanio++;
        ticket.cambiarEstado(Estado.EN_COLA); // Establecer estado inicial
    }

    // Sacar el ticket del frente de la cola (FIFO)
    public Ticket dequeue() {
        if (estaVacia()) {
            return null; // Caso borde: cola vacía [cite: 27]
        }
        Ticket ticketAtendido = frente.getDato();
        frente = frente.getSiguiente(); // Mover el frente al siguiente
        if (frente == null) {
            fin = null; // Si la cola queda vacía, el fin también es null
        }
        tamanio--;
        return ticketAtendido;
    }

    // Ver el ticket del frente sin sacarlo
    public Ticket peek() {
        return estaVacia() ? null : frente.getDato();
    }

    // Recorrer y listar los tickets en espera
    public void listar() {
        if (estaVacia()) {
            System.out.println("No hay casos en espera.");
            return;
        }
        NodoCola actual = frente;
        int i = 1;
        while (actual != null) {
            // Se usa el toString de Ticket
            System.out.println(String.format("%d. %s", i++, actual.getDato().toString()));
            actual = actual.getSiguiente();
        }
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int getTamanio() {
        return tamanio;
    }
}