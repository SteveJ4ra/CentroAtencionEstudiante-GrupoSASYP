package modelo;

import estructuras.lista.ListaNotas;
import java.util.concurrent.atomic.AtomicInteger;

public class Ticket {
    private static final AtomicInteger GENERADOR_ID = new AtomicInteger(1); //sirve para aumentar de 1 en 1 cada ves que se lo use
    private int id;
    private String nombreCliente;
    private String estado;
    private ListaNotas listaNotas;


    public Ticket(String nombreCliente, String estadoInicial) {
        this.id = GENERADOR_ID.getAndIncrement();
        this.nombreCliente = nombreCliente;
        this.estado = estadoInicial;
        this.listaNotas = new ListaNotas();
    }

    //Metodo para gr
    public void agregarNota(String texto){
        int idNota = (int)(Math.random()*1000);
        Nota nueva = new Nota(idNota, texto);
        listaNotas.insertarInicio(nueva);
        System.out.println("Nota:" + idNota + "agregada al ticket: " + this.id);
    }

    public void eliminarNota(int idNota){
        boolean ok = listaNotas.eliminar(idNota);

        if(ok){
            System.out.println("Nota eliminada correctamente del ticket #"+ id);
        }else{
            System.out.println("No se encontro la nota con el ID #" + idNota);
        }
    }

    public void cambiarEstado(String nuevoEstado){
        this.estado = nuevoEstado;
        System.out.println("Ticket #"+ id + "ahora esta en estado: " + nuevoEstado);
    }

    public void mostrarHistorial(){
        System.out.println("Historial de notas del ticket #" + id + ":");
        listaNotas.mostrar();
    }

    @Override
    public String toString() {
        return String.format("Ticket #%d | Cliente: %s | Estado: %s", id, nombreCliente, estado);
    }

    public int getId() {
        return id;
    }
    public String getNombreCliente() {
        return nombreCliente;
    }
    public String getEstado() {
        return estado;
    }
}
