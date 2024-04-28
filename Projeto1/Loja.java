package Classes;

public class Loja {
    private final String nome;
    private final Conta contaLoja;

    public Loja(String nome) {
        this.nome = nome;
        this.contaLoja = new Conta(nome, 0); 
    }

    public Conta getContaLoja() {
        return contaLoja;
    }

    public String getNome() {
        return nome;
    }
}
