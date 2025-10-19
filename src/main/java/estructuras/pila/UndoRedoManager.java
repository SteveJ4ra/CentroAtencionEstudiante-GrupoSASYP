package estructuras.pila;

public class UndoRedoManager {
  private final PilaAcciones pilaUndo = new PilaAcciones();
    private final PilaAcciones pilaRedo = new PilaAcciones();

  //Resgistro de la accion que vamos a estar ejecutando dando con el pila de undo y el redo de pila

    public void registrarAccion(Accion accion) {
        pilaUndo.push(accion);
        pilaRedo.limpiar();
    }

  //Esto Deshace la ultima accion que se hizo y devuelve un true si se hace el deshizo
    public boolean deshacer() {
        Accion accion = pilaUndo.pop();
        if (accion == null) return false;
        try {
            accion.undo();
            pilaRedo.push(accion);
            return true;
        } catch (Exception e) {
            // Manejo de errores: si la deshacer falla, podrías querer registrar/loggear
            System.err.println("Error al deshacer: " + e.getMessage());
            return false;
        }
    }

  //Esto es para rehacer la accion que se halla deshechado y devuelve un true si se rehace algo
  
    public boolean rehacer() {
        Accion accion = pilaRedo.pop();
        if (accion == null) return false;
        try {
            accion.execute();
            pilaUndo.push(accion);
            return true;
        } catch (Exception e) {
            System.err.println("Error al rehacer: " + e.getMessage());
            return false;
        }
    }

    public boolean hayUndo() { return !pilaUndo.estaVacia(); }
    public boolean hayRedo() { return !pilaRedo.estaVacia(); }

    public int undoCount() { return pilaUndo.getSize(); }
    public int redoCount() { return pilaRedo.getSize(); }

  // Esto se hace para limipar ambos historiales con el undo y redo
    
    public void limpiarHistorial() {
        pilaUndo.limpiar();
        pilaRedo.limpiar();
    }

}
