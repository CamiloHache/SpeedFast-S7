package modelo;

public class Pedido {

    private int id;
    private String direccionEntrega;
    private TipoPedido tipo;
    private EstadoPedido estado;
    private String repartidor;

    public Pedido(int id, String direccionEntrega, TipoPedido tipo) {
        this.id = id;
        this.direccionEntrega = direccionEntrega;
        this.tipo = tipo;
        this.estado = EstadoPedido.PENDIENTE;
        this.repartidor = "-";
    }

    public int getId() {
        return id;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public TipoPedido getTipo() {
        return tipo;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public String getRepartidor() {
        return repartidor;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public void setRepartidor(String repartidor) {
        this.repartidor = repartidor;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pedido #" + id + " - " + direccionEntrega + " - " + tipo + " - " + estado;
    }
}