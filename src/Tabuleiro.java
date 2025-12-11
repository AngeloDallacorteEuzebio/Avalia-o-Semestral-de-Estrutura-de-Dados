import java.util.*;

public class Tabuleiro {
    private List<Casa> casas;
    private Casa casaInicial;
    private Casa casaPrisao;

    public Tabuleiro() {
        casas = new ArrayList<>();
        construirVazio();
    }

    public void reset() {
        casas.clear();
        construirVazio();
    }

    private void construirVazio() {
        casas = new ArrayList<>();
        casaInicial = new CasaEspecial("Inicio", "INICIO", true);
        casas.add(casaInicial);
    }

    public void construirPadrao(List<Propriedade> cadastro) {
        reset();
        casas.add(casaInicial);
        for (Propriedade p : cadastro) {
            casas.add(new CasaPropriedade(p));
        }
        casas.add(new CasaEspecial("Imposto", "IMPUESTO", false));
        casas.add(new CasaEspecial("Sorte/Revés", "SORTE", false));
        casaPrisao = new CasaEspecial("Prisao", "PRISAO", false);
        casas.add(casaPrisao);
        while (casas.size() < 20) {
            casas.add(new CasaEspecial("Casa Vazia " + casas.size(), "VAZIO", false));
        }
    }

    public Casa getCasaInicial() {
        return casaInicial;
    }

    public Casa getCasaPrisao() {
        return casaPrisao;
    }

    public Casa avancarCasa(Casa atual, int passos) {
        int idx = casas.indexOf(atual);
        if (idx == -1) idx = 0;
        int novo = (idx + passos) % casas.size();
        return casas.get(novo);
    }

    public Casa buscarCasaPorNome(String nome) {
        for (Casa c : casas) if (c.getNome().equalsIgnoreCase(nome)) return c;
        return null;
    }
}
