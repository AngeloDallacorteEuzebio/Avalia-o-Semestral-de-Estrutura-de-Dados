public class Carta {
    private String tipo;
    private String texto;
    private double valor;

    public Carta(String tipo, String texto, double valor) {
        this.tipo = tipo;
        this.texto = texto;
        this.valor = valor;
    }

    public String getTipo() { return tipo; }
    public String getTexto() { return texto; }
    public double getValor() { return valor; }
}
