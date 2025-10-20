package modelo;

import java.time.LocalDateTime;

// Clase abstracta para el patrón Command, esencial para Undo/Redo
// (Registrar acciones mínimas: añadir nota, eliminar nota, cambio de estado) [cite: 47, 48]
public abstract class Accion {

    private final String tipo;
    private final String descripcion;
    private final LocalDateTime fechaHora;

    public Accion(String tipo, String descripcion) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fechaHora = LocalDateTime.now();
    }

    // Método a implementar para ejecutar la acción (usado en Redo)
    public abstract void ejecutar();

    // Método a implementar para deshacer la acción (usado en Undo)
    public abstract void deshacer();

    // Getters...
    public String getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    public LocalDateTime getFechaHora() { return fechaHora; }

    @Override
    public String toString() {
        return String.format("%s: %s (%s)", tipo, descripcion, fechaHora);
    }
}