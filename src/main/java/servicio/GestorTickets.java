package servicio;

import estructuras.cola.QuequeCAE;
import estructuras.pila.UndoRedoManager;
import modelo.Ticket;
import modelo.Estado;
import modelo.Nota;
import servicio.acciones.AccionAgregarNota;
import servicio.acciones.AccionCambiarEstado;
import java.util.HashMap;
import java.util.Map;
import servicio.acciones.AccionEliminarNota;

public class GestorTickets {

    private final QuequeCAE colaTickets; // Almacena casos en espera (FIFO)
    private Ticket ticketEnAtencion; // Solo un caso a la vez [cite: 14]
    private final UndoRedoManager undoRedoManager; // Gestiona el historial de acciones [cite: 20]
    // Almacena tickets finalizados para consulta posterior (id -> Ticket) [cite: 16]
    private final Map<Integer, Ticket> ticketsFinalizados;

    public GestorTickets() {
        this.colaTickets = new QuequeCAE();
        this.undoRedoManager = new UndoRedoManager();
        this.ticketsFinalizados = new HashMap<>();
    }

    // --- Métodos de Gestión de Cola (Recepción de casos) ---

    // Recepción de un nuevo caso [cite: 13]
    public void recibirNuevoCaso(String nombreCliente) {
        // El estado inicial es EN_COLA (set en QuequeCAE.enqueque)
        Ticket nuevoTicket = new Ticket(nombreCliente, Estado.EN_COLA);
        colaTickets.enqueque(nuevoTicket);
        System.out.println("✅ Nuevo ticket recibido: " + nuevoTicket);
    }

    public void listarCasosEnEspera() {
        System.out.println("\n--- Casos en Espera (" + colaTickets.getTamanio() + ") ---");
        colaTickets.listar();
        System.out.println("---------------------------------");
    }

    // --- Métodos de Atención de Caso ---

    // Tomar el siguiente caso de la cola para atención [cite: 14]
    public boolean iniciarAtencion() {
        if (ticketEnAtencion != null) {
            System.err.println("⚠️ Ya hay un ticket en atención (#" + ticketEnAtencion.getId() + "). Finalice primero.");
            return false;
        }
        Ticket siguiente = colaTickets.dequeue();
        if (siguiente == null) {
            System.err.println("⚠️ La cola de tickets está vacía. No hay casos para atender.");
            return false;
        }
        ticketEnAtencion = siguiente;
        ticketEnAtencion.cambiarEstado(Estado.EN_ATENCION);
        undoRedoManager.limpiarHistorial(); // Nuevo ticket, nuevo historial de undo/redo
        System.out.println("\n--- Iniciando Atención ---");
        System.out.println("▶️ Ticket en atención: " + ticketEnAtencion);
        System.out.println("--------------------------");
        return true;
    }

    // Finalización de la atención de un caso [cite: 16]
    public boolean finalizarCaso(Estado estadoFinal) {
        if (ticketEnAtencion == null) {
            System.err.println("⚠️ No hay caso en atención para finalizar.");
            return false;
        }

        // Se usa la acción para registrar el cambio de estado (aunque se pierda el historial de undo/redo al finalizar)
        // Se puede registrar una última acción de cambio de estado final
        Estado estadoAnterior = ticketEnAtencion.getEstado();
        AccionCambiarEstado accionFinal = new AccionCambiarEstado(ticketEnAtencion, estadoAnterior, estadoFinal);
        accionFinal.ejecutar(); // Ejecuta el cambio de estado final

        ticketsFinalizados.put(ticketEnAtencion.getId(), ticketEnAtencion);
        System.out.println("\n--- Caso Finalizado ---");
        System.out.println("✔️ Ticket #" + ticketEnAtencion.getId() + " finalizado en estado: " + estadoFinal);
        // El historial de notas está conservado en el objeto Ticket
        ticketEnAtencion = null;
        undoRedoManager.limpiarHistorial(); // Limpiar el historial corto al finalizar
        return true;
    }

    // --- Métodos de Operación en Atención (Observaciones/Notas) ---

    // Registrar una observación (nota) [cite: 14]
    public boolean registrarNota(String texto) {
        if (ticketEnAtencion == null) {
            System.err.println("⚠️ No hay caso en atención. Inicie uno primero.");
            return false;
        }
        Nota nuevaNota = ticketEnAtencion.agregarNota(texto);
        // Registrar la acción para poder deshacer/rehacer [cite: 20, 48]
        AccionAgregarNota accion = new AccionAgregarNota(ticketEnAtencion, nuevaNota);
        undoRedoManager.registrarAccion(accion);
        System.out.println("📝 Nota agregada y acción registrada para Undo.");
        return true;
    }

    public int undoCount() {
        return undoRedoManager.undoCount();
    }

    public int redoCount() {
        return undoRedoManager.redoCount();
    }

    public boolean deshacerAccion() {
        if (ticketEnAtencion == null) {
            System.err.println("⚠️ No hay caso en atención para deshacer acciones.");
            return false;
        }
        if (undoRedoManager.deshacer()) {
            System.out.println("↩️ Acción deshecha con éxito.");
            return true;
        }
        return false;
    }

    public boolean eliminarNota(int idNota) {
        if (ticketEnAtencion == null) {
            System.err.println("⚠️ No hay caso en atención. Inicie uno primero.");
            return false;
        }

        // El método ticketEnAtencion.eliminarNota(id) debe devolver la Nota eliminada (Nota o null)
        // Para que esto funcione, ListaNotas debe haber sido corregida para devolver Nota, NO boolean.
        Nota notaEliminada = ticketEnAtencion.eliminarNota(idNota);

        if (notaEliminada != null) {
            // Registrar la acción para deshacer/rehacer
            AccionEliminarNota accion = new AccionEliminarNota(ticketEnAtencion.getListaNotas(), notaEliminada);
            undoRedoManager.registrarAccion(accion);

            System.out.println("❌ Nota ID " + idNota + " eliminada y acción registrada para Undo.");
            return true;
        } else {
            System.err.println("⚠️ No se encontró la nota con el ID #" + idNota);
            return false;
        }
    }

    public boolean rehacerAccion() {
        if (ticketEnAtencion == null) {
            System.err.println("⚠️ No hay caso en atención para rehacer acciones.");
            return false;
        }
        if (undoRedoManager.rehacer()) {
            System.out.println("↪️ Acción rehecha con éxito.");
            return true;
        }
        return false;
    }

    // --- Métodos de Consulta ---

    // Consulta del historial de un caso específico [cite: 17]
    public void consultarHistorial(int idTicket) {
        Ticket ticket = ticketsFinalizados.get(idTicket);
        if (ticket == null) {
            System.err.println("⚠️ El ticket #" + idTicket + " no se encuentra en el historial de finalizados.");
            return;
        }
        System.out.println("\n--- Historial del Ticket #" + idTicket + " ---");
        System.out.println(ticket.toString());
        ticket.getListaNotas().mostrar(); // El método mostrar está en ListaNotas (SLL)
        System.out.println("----------------------------------------");
    }

    // Getters
    public Ticket getTicketEnAtencion() {
        return ticketEnAtencion;
    }

    public boolean cambiarEstadoInterno(Estado nuevoEstado) {
        if (ticketEnAtencion == null) {
            System.err.println("⚠️ No hay caso en atención para cambiar el estado.");
            return false;
        }

        Estado estadoAnterior = ticketEnAtencion.getEstado();

        // Evitar registrar una acción si el estado es el mismo
        if (estadoAnterior == nuevoEstado) {
            System.out.println("El estado ya es: " + nuevoEstado);
            return true;
        }

        // 1. Ejecutar el cambio de estado
        ticketEnAtencion.cambiarEstado(nuevoEstado);

        // 2. Registrar la acción para deshacer/rehacer
        AccionCambiarEstado accion = new AccionCambiarEstado(ticketEnAtencion, estadoAnterior, nuevoEstado);
        undoRedoManager.registrarAccion(accion);

        System.out.println("✏️ Estado del Ticket #" + ticketEnAtencion.getId() + " cambiado a " + nuevoEstado + " y acción registrada.");
        return true;
    }
}