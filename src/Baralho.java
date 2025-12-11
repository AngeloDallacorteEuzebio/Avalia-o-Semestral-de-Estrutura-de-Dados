import java.util.*;

public class Baralho {
    private Deque<Carta> cartas;

    public Baralho() {
        cartas = new ArrayDeque<>();
    }

    public void iniciarBaralhoPadrao() {
        cartas.clear();
        cartas.add(new Carta("GANHAR", "Receba salario do banco", 1000));
        cartesAddSimple("PERDER", "Pague uma multa", 300);
        cartesAddSimple("GANHAR", "Receba premiação", 500);
        cartesAddSimple("IRPARA", "Praça C", 0);
        List<Carta> lista = new ArrayList<>(cartas);
        Collections.shuffle(lista);
        cartas = new ArrayDeque<>(lista);
    }

    private void cartesAddSimple(String tipo, String texto, double valor) {
        cartas.add(new Carta(tipo, texto, valor));
    }

    public Carta comprar() {
        if (cartas.isEmpty()) return new Carta("VAZIO", "Nenhuma carta", 0);
        Carta c = cartas.pollFirst();
        cartas.addLast(c); // ciclo
        return c;
    }
}
