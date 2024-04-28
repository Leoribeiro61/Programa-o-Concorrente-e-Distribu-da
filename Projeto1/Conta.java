package Classes;

public class Conta {
    private final String nome;
    private double saldo;

    public Conta(String nome, double saldoInicial) {
        this.nome = nome;
        this.saldo = saldoInicial;
    }

    public double getSaldo() {
        return saldo;
    }

    public void debitar(double valor) {
        synchronized (this) {
            saldo -= valor;
        }
    }

    public void creditar(double valor) {
        synchronized (this) {
            saldo += valor;
        }
    }

    public String getNome() {
        return nome;
    }
}
