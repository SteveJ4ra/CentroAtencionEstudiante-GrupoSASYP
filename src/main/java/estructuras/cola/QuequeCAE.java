package estructuras.cola;

import modelo.Ticket;
import modelo.Estado; // Necesario para el estado inicial

public class QuequeCAE {
    private NodoCola frente; // Inicio de la cola (el primer dato a salir)
    private NodoCola fin;  // Fin de la cola (por donde se inserta primeramente datos)
    private int tamanio; // Almacena cuántos tickets hay en la cola

    // Insertar un nuevo ticket al final de la cola
    public void enqueque(Ticket ticket) {
        NodoCola nuevo = new NodoCola(ticket); // crea un nuevo nodo con el ticket
        if (estaVacia()) {
            frente = nuevo; // si está vacía la cola, el nuevo es el primero
        } else {
            fin.setSiguiente(nuevo);
        }
        fin = nuevo;
         // El nuevo nodo siempre es el nuevo fin
        tamanio++;
        ticket.cambiarEstado(Estado.EN_COLA); // Establecer estado inicial
    }

    // Sacar el ticket del frente de la cola (FIFO)
    public Ticket dequeue() {
        if (estaVacia()) {
            return null; // La cola está vacía si no hay ni un solo valor al frente
        }
        Ticket ticketAtendido = frente.getDato();
        frente = frente.getSiguiente(); // Mover el frente al siguiente
        if (frente == null) {
            fin = null; // Si la cola queda vacía, el fin también es null
        }
        tamanio--;
        return ticketAtendido;
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
            System.out.println("ID: " + actual.getDato().getId() +
                    " | Nombre: " + actual.getDato().getNombreCliente() +
                    " | Estado: " + actual.getDato().getEstado());

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