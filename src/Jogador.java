import java.util.*;

public class Jogador {
    private String nome;
    private double saldo;
    private Casa posicao;
    private boolean falido = false;
    private boolean preso = false;
    private int turnosPrisao = 0;
    private List<Propriedade> propriedades;

    public Jogador(String nome, double saldoInicial) {
        this.nome = nome;
        this.saldo = saldoInicial;
        this.propriedades = new ArrayList<>();
    }

    public void prepararParaJogo(Casa inicio, double saldoInicial) {
        this.posicao = inicio;
        this.saldo = saldoInicial;
        this.falido = false;
        this.propriedades.clear();
        this.preso = false;
        this.turnosPrisao = 0;
    }

    public String getNome() { return nome; }
    public double getSaldo() { return saldo; }
    public void creditar(double valor) { this.saldo += valor; }
    public boolean isFalido() { return falido; }
    public void falir() { this.falido = true; }
    public boolean estaPreso() { return preso; }
    public int getTurnosPrisao() { return turnosPrisao; }
    public void irParaPrisao(Casa prisao) { this.posicao = prisao; this.preso = true; this.turnosPrisao = 0; }
    public void sairPrisao() { this.preso = false; this.turnosPrisao = 0; }
    public void incrementarTurnoPrisao() { this.turnosPrisao++; }
    public void setPosicao(Casa c) { this.posicao = c; }
    public Casa getPosicao() { return posicao; }

    public void comprar(Propriedade p) {
        if (p.getProprietario() == null && this.saldo >= p.getPrecoCompra()) {
            this.creditar(-p.getPrecoCompra());
            p.setProprietario(this);
            propriedades.add(p);
        }
    }

    public void listarPropriedades() {
        if (propriedades.isEmpty()) System.out.println("Nenhuma propriedade.");
        else {
            for (Propriedade p : propriedades) {
                System.out.printf("- %s (Hipotecada: %s)\n", p.getNome(), p.estaHipotecada() ? "SIM" : "NAO");
            }
        }
    }

    public Propriedade buscarPropriedadePorNome(String nome) {
        for (Propriedade p : propriedades) if (p.getNome().equalsIgnoreCase(nome)) return p;
        return null;
    }

    public void hipotecar(Propriedade p) {
        if (p != null && !p.estaHipotecada()) {
            p.hipotecar();
            double valor = p.getPrecoCompra() * 0.5;
            this.creditar(valor);
            System.out.println("Hipoteca realizada. Recebeu R$ " + valor);
        }
    }

    public void pagarHipoteca(Propriedade p) {
        if (p != null && p.estaHipotecada()) {
            double custo = p.getPrecoCompra() * 0.55; // juros
            if (this.saldo >= custo) {
                this.creditar(-custo);
                p.deshipotecar();
                System.out.println("Hipoteca paga. Pago R$ " + custo);
            } else {
                System.out.println("Saldo insuficiente para quitar hipoteca.");
            }
        }
    }

    public boolean tentarRecuperar() {
        for (Propriedade p : propriedades) {
            if (!p.estaHipotecada()) {
                hipotecar(p);
                if (this.saldo >= 0) return true;
            }
        }
        return this.saldo >= 0;
    }

    public void exibirStatus() {
        System.out.println("Jogador: " + nome);
        System.out.printf("Saldo: R$ %.2f\n", saldo);
        System.out.println("Posição: " + (posicao != null ? posicao.getNome() : "Indefinida"));
        System.out.println("Preso: " + (preso ? "SIM" : "NAO"));
        listarPropriedades();
    }
    public double getSaldoParaOrdenacao() { return saldo; }
}
