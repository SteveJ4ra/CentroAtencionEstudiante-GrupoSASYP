package consola;

import servicio.GestorTickets;
import modelo.Estado;

public class DemoFuncionalidadCAE {

    // Método main para ejecutar la demostración de forma independiente
    public static void main(String[] args) {
        // Creamos una nueva instancia del gestor para aislar la prueba de cualquier sesión interactiva previa.
        GestorTickets gestorDePrueba = new GestorTickets();
        ejecutarDemo(gestorDePrueba);
    }

    /**
     * Ejecuta una secuencia de comandos automatizada para probar todas las estructuras
     * y el mecanismo Undo/Redo.
     * @param gestor La instancia de GestorTickets a utilizar.
     */
    public static void ejecutarDemo(GestorTickets gestor) {
        System.out.println("\n\n#####################################################");
        System.out.println("--- EJECUTANDO DEMOSTRACIÓN AUTOMÁTICA DE PRUEBA ---");
        System.out.println("#####################################################");

        // 1. Recepción de casos
        System.out.println("--- PRUEBA DE RECEPCIÓN (COLA FIFO) ---");
        gestor.recibirNuevoCaso("Ana Lopez - Certificado"); // T1
        gestor.recibirNuevoCaso("Carlos Mena - Homologación"); // T2
        gestor.listarCasosEnEspera();

        // 2. Atención y Undo/Redo (Ticket 1)
        System.out.println("\n--- PRUEBA DE ATENCIÓN Y UNDO/REDO ---");
        gestor.iniciarAtencion();

        // a) Prueba Undo/Redo: Agregar Nota
        System.out.println("\n--- UNDO/REDO: AÑADIR NOTA ---");
        gestor.registrarNota("Nota 1: Documentación entregada completa.");
        gestor.deshacerAccion();
        gestor.rehacerAccion();

        // b) Prueba Undo/Redo: Cambio de Estado
        System.out.println("\n--- UNDO/REDO: CAMBIO DE ESTADO ---");
        gestor.cambiarEstadoInterno(Estado.EN_PROCESO);
        gestor.deshacerAccion();
        gestor.rehacerAccion();

        gestor.registrarNota("Nota 2: Esperando firma de Director.");

        // c) Prueba Undo/Redo: Eliminar Nota (Requiere ID)
        System.out.println("\n--- UNDO/REDO: ELIMINAR NOTA (Nota 2) ---");
        gestor.eliminarNota(2); // Elimina Nota 2
        gestor.deshacerAccion(); // Undoes (Reinserta Nota 2)
        gestor.rehacerAccion(); // Redoes (Re-elimina Nota 2)

        // 3. Finalización y Consulta
        gestor.finalizarCaso(Estado.COMPLETADO);

        // 4. Atender el siguiente (Ticket 2)
        gestor.iniciarAtencion();
        gestor.registrarNota("Nota 3: Falta documento de identidad.");
        gestor.finalizarCaso(Estado.PENDIENTE_DOCS);

        // 5. Consulta de Historial
        System.out.println("\n--- CONSULTA DE HISTORIAL (Validación SLL y Conservación) ---");
        gestor.consultarHistorial(1); // T1: Solo debe tener Nota 1 (Nota 2 fue eliminada por la última acción de Redo)
        gestor.consultarHistorial(2); // T2: Debe tener Nota 3

        System.out.println("#####################################################");
        System.out.println("--- FIN DE DEMOSTRACIÓN EXITOSA ---");
        System.out.println("#####################################################");
    }
}