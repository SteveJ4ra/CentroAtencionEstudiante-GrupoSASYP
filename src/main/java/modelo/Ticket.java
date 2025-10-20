package modelo;

import estructuras.lista.ListaNotas;
import java.util.concurrent.atomic.AtomicInteger;

public class Ticket {
    // Generador de ID para el ticket
    private static final AtomicInteger GENERADOR_ID = new AtomicInteger(1);
    private int id;
    private String nombreCliente;
    // Uso del enum Estado (corrección)
    private Estado estado;
    private ListaNotas listaNotas;
    // Generador de ID para Notas, dentro del ticket para asegurar unicidad por ticket
    private AtomicInteger generadorIdNota = new AtomicInteger(1);


    public Ticket(String nombreCliente, Estado estadoInicial) {
        this.id = GENERADOR_ID.getAndIncrement();
        this.nombreCliente = nombreCliente;
        this.estado = estadoInicial;
        this.listaNotas = new ListaNotas();
    }

    // Metodos para gestionar notas (sin la lógica de impresión para separar dominio de E/S)
    public Nota agregarNota(String texto){
        // ID de nota incremental por ticket
        int idNota = generadorIdNota.getAndIncrement();
        Nota nueva = new Nota(idNota, texto);
        listaNotas.insertarInicio(nueva); // Inserción al inicio (Requisito SLL )
        return nueva;
    }

    public Nota eliminarNota(int idNota){ // <-- ¡Cambio de retorno de void a Nota!
        // listaNotas.eliminar ahora devuelve la Nota eliminada.
        return listaNotas.eliminar(idNota);
    }

    public void cambiarEstado(Estado nuevoEstado){
        this.estado = nuevoEstado;
    }

    // Getters y toString (corrección de tipo de estado)
    @Override
    public String toString() {
        return String.format("Ticket #%d | Cliente: %s | Estado: %s", id, nombreCliente, estado.toString());
    }

    public int getId() { return id; }
    public String getNombreCliente() { return nombreCliente; }
    public Estado getEstado() { return estado; }
    // Nuevo getter esencial para que las acciones puedan modificar la lista de notas
    public ListaNotas getListaNotas() { return listaNotas; }
}