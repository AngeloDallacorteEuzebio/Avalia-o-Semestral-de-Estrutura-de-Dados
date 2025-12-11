public class Propriedade {
    private String nome;
    private double precoCompra;
    private double aluguel;
    private Jogador proprietario;
    private boolean hipotecada = false;

    public Propriedade(String nome, double preco, double aluguel) {
        this.nome = nome;
        this.precoCompra = preco;
        this.aluguel = aluguel;
    }

    public String getNome() { return nome; }
    public double getPrecoCompra() { return precoCompra; }
    public double getAluguel() { return aluguel; }
    public Jogador getProprietario() { return proprietario; }
    public void setProprietario(Jogador p) { this.proprietario = p; }
    public boolean estaHipotecada() { return hipotecada; }
    public void hipotecar() { hipotecada = true; }
    public void deshipotecar() { hipotecada = false; }
}
