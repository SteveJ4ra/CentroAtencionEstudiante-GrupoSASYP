package estructuras.pila;

import modelo.Accion;

public class UndoRedoManager {
    // Uso de dos pilas para el mecanismo undo/redo [cite: 20]
    private final PilaAcciones pilaUndo = new PilaAcciones();
    private final PilaAcciones pilaRedo = new PilaAcciones();

    // Registra la acción: la pone en Undo y limpia Redo (historial corto)
    public void registrarAccion(Accion accion) {
        pilaUndo.push(accion);
        pilaRedo.limpiar(); // Al hacer una nueva acción, se pierde el historial de Redo
    }

    // Deshace la última acción. Devuelve true si se pudo deshacer.
    public boolean deshacer() {
        Accion accion = pilaUndo.pop();
        if (accion == null) {
            // Caso borde: Deshacer sin acciones previas [cite: 27]
            System.err.println("No hay acciones para deshacer.");
            return false;
        }

        // Se usa el método 'deshacer' de la clase Accion (patrón Command)
        accion.deshacer();
        pilaRedo.push(accion); // Mueve la acción a Redo para poder rehacerla
        return true;

    }

    // Rehace la última acción deshecha. Devuelve true si se pudo rehacer.
    public boolean rehacer() {
        Accion accion = pilaRedo.pop();
        if (accion == null) {
            // Caso borde: Rehacer sin acciones previas [cite: 27]
            System.err.println("No hay acciones para rehacer.");
            return false;
        }

        // Se usa el método 'ejecutar' de la clase Accion (patrón Command)
        accion.ejecutar();
        pilaUndo.push(accion); // Mueve la acción de vuelta a Undo
        return true;

    }

    public boolean hayUndo() { return !pilaUndo.estaVacia(); }
    public boolean hayRedo() { return !pilaRedo.estaVacia(); }
    public int undoCount() { return pilaUndo.getSize(); }
    public int redoCount() { return pilaRedo.getSize(); }

    public void limpiarHistorial() {
        pilaUndo.limpiar();
        pilaRedo.limpiar();
    }
}