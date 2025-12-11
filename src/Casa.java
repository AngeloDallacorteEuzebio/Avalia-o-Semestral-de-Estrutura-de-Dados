public abstract class Casa {
    protected String nome;
    protected String tipo;
    protected boolean inicio = false;

    public Casa(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getNome() { return nome; }
    public String getTipo() { return tipo; }
    public boolean isInicio() { return inicio; }
    public void setInicio(boolean v) { inicio = v; }
}
