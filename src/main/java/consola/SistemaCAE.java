package consola;

import servicio.GestorTickets;
import modelo.Estado;
import java.util.Scanner;
import java.util.InputMismatchException;

public class SistemaCAE {

    private static final GestorTickets gestor = new GestorTickets();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ejecutarMenuPrincipal();
    }

    private static void ejecutarMenuPrincipal() {
        int opcion;
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea

                switch (opcion) {
                    case 1:
                        recibirNuevoCaso();
                        break;
                    case 2:
                        gestor.listarCasosEnEspera();
                        break;
                    case 3:
                        iniciarAtencion();
                        break;
                    case 4:
                        gestorAtencion(); // Sub-menú para el caso en atención
                        break;
                    case 5:
                        consultarHistorial();
                        break;
                    case 0:
                        System.out.println("Saliendo del Sistema CAE. ¡Adiós!");
                        salir = true;
                        break;
                    default:
                        System.err.println("Opción no válida. Intente de nuevo.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine(); // Limpiar el buffer de entrada
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n===== CENTRO DE ATENCIÓN AL ESTUDIANTE (CAE) =====");
        System.out.println("1. Recepción de Nuevo Caso (Encolar)");
        System.out.println("2. Consultar Casos en Espera");
        System.out.println("3. Iniciar Atención del Siguiente Caso");
        System.out.println("4. Gestionar Caso EN ATENCIÓN (Notas, Estado, Undo/Redo)");
        System.out.println("5. Consultar Historial de Caso Finalizado");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void recibirNuevoCaso() {
        System.out.print("Ingrese nombre o descripción del cliente/trámite: ");
        String cliente = scanner.nextLine();
        gestor.recibirNuevoCaso(cliente);
    }

    private static void iniciarAtencion() {
        gestor.iniciarAtencion();
    }

    private static void consultarHistorial() {
        System.out.print("Ingrese ID del Ticket a consultar (debe estar finalizado): ");
        try {
            int id = scanner.nextInt();
            scanner.nextLine();
            gestor.consultarHistorial(id);
        } catch (InputMismatchException e) {
            System.err.println("ID inválido.");
            scanner.nextLine();
        }
    }

    // --- Sub-Menú para Caso en Atención ---

    private static void gestorAtencion() {
        if (gestor.getTicketEnAtencion() == null) {
            System.err.println(" No hay ningún ticket en atención activa. Inicie uno (Opción 3) primero.");
            return;
        }

        int opcion;
        boolean volver = false;

        // Asumiendo que has añadido los métodos undoCount() y redoCount() a GestorTickets para mostrar el conteo.
        while (!volver) {
            System.out.println("\n--- GESTIÓN DEL TICKET #" + gestor.getTicketEnAtencion().getId() + " (" + gestor.getTicketEnAtencion().getEstado() + ") ---");
            System.out.println("1. Registrar Nota/Observación");
            System.out.println("2. Eliminar Nota (por ID)");
            System.out.println("3. Cambiar Estado del Ticket");
            // Se asume la existencia de gestor.undoCount() y gestor.redoCount()
            System.out.println("4. Deshacer última acción (Undo)");
            System.out.println("5. Rehacer acción deshecha (Redo)");
            System.out.println("6. FINALIZAR Caso");
            System.out.println("0. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        registrarNotaEnAtencion();
                        break;
                    case 2:
                        eliminarNotaEnAtencion();
                        break;
                    case 3:
                        cambiarEstadoEnAtencion();
                        break;
                    case 4:
                        gestor.deshacerAccion();
                        break;
                    case 5:
                        gestor.rehacerAccion();
                        break;
                    case 6:
                        finalizarCasoEnAtencion();
                        volver = true;
                        break;
                    case 0:
                        volver = true;
                        break;
                    default:
                        System.err.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Ingrese un número.");
                scanner.nextLine();
            }
        }
    }

    private static void registrarNotaEnAtencion() {
        System.out.print("Escriba la observación (nota): ");
        String nota = scanner.nextLine();
        gestor.registrarNota(nota);
    }

    private static void eliminarNotaEnAtencion() {
        System.out.println("\n--- Notas Actuales del Ticket #" + gestor.getTicketEnAtencion().getId() + " ---");
        gestor.getTicketEnAtencion().getListaNotas().mostrar();
        System.out.print("Ingrese ID de la Nota a eliminar: ");

        try {
            int id = scanner.nextInt();
            scanner.nextLine();
            gestor.eliminarNota(id);
        } catch (InputMismatchException e) {
            System.err.println("ID inválido.");
            scanner.nextLine();
        }
    }

    private static void cambiarEstadoEnAtencion() {
        System.out.println("\n--- ESTADOS DISPONIBLES ---");
        int i = 1;
        Estado[] estados = {Estado.EN_PROCESO, Estado.PENDIENTE_DOCS, Estado.COMPLETADO};
        for (Estado e : estados) {
            System.out.println(i++ + ". " + e.toString());
        }

        System.out.print("Seleccione el nuevo estado: ");
        try {
            int indice = scanner.nextInt();
            scanner.nextLine();
            if (indice >= 1 && indice <= estados.length) {
                gestor.cambiarEstadoInterno(estados[indice - 1]);
            } else {
                System.err.println("Selección fuera de rango.");
            }
        } catch (InputMismatchException e) {
            System.err.println("Entrada inválida.");
            scanner.nextLine();
        }
    }

    private static void finalizarCasoEnAtencion() {
        gestor.finalizarCaso(gestor.getTicketEnAtencion().getEstado());
    }
}
