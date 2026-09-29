package modelo;

public class Entrega {

    private int id;
    private int pedidoId;
    private int repartidorId;
    private String fecha;
    private String hora;

    public Entrega(int id, int pedidoId, int repartidorId, String fecha, String hora) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.repartidorId = repartidorId;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public int getPedidoId() {
        return pedidoId;
    }

    public int getRepartidorId() {
        return repartidorId;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }
}