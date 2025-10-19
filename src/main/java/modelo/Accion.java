package modelo;

import java.time.LocalDateTime;

public class Accion {

    private String tipo;
    private String descripcion;
    private LocalDateTime fechahora;
    private String object;

    //Uso del constructor

    public Accion(String tipo, String descripcion, LocalDateTime fechahora, String object) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fechahora = fechahora;
        this.object = object;
    }

    //Uso de un Getter and Setter

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechahora() {
        return fechahora;
    }

    public void setFechahora(LocalDateTime fechahora) {
        this.fechahora = fechahora;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    //Uso de un toString

    @Override
    public String toString() {
        return "Accion{" +
                "tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechahora=" + fechahora +
                ", object='" + object + '\'' +
                '}';
    }
}

