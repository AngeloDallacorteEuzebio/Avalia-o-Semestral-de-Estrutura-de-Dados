public class CasaPropriedade extends Casa {
    private Propriedade propriedade;

    public CasaPropriedade(Propriedade p) {
        super(p.getNome(), "PROPRIEDADE");
        this.propriedade = p;
    }

    public Propriedade getPropriedade() { return propriedade; }
}
