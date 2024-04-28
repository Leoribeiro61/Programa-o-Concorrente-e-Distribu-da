package Classes;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cliente extends Thread {
    private String nome;
    private Conta contaCliente;
    private Loja[] lojas;
    private Loja ultimaLojaEscolhida; 
    private Banco banco;
    private final Lock lock = new ReentrantLock();

    public Cliente(String nome, Loja[] lojas, Banco banco) {
        this.nome = nome;
        this.contaCliente = new Conta(nome, 1000); 
        this.lojas = lojas;
        this.banco = banco;
    }

    public void run() {
        Random random = new Random();
        while (contaCliente.getSaldo() > 0) {
            double valorCompra = random.nextDouble() < 0.5 ? 100.0 : 200.0;

            Loja primeiraLojaEscolhida;
            do {
                int indiceLoja = random.nextInt(lojas.length);
                primeiraLojaEscolhida = lojas[indiceLoja];
            } while (primeiraLojaEscolhida == ultimaLojaEscolhida); 
            ultimaLojaEscolhida = primeiraLojaEscolhida; 

            if (contaCliente.getSaldo() == 100.0 && valorCompra == 200.0) {
                valorCompra = 100.0;
            }

            // Transfere o valor da compra para a conta da loja ... fluxo: cliente -> loja
            lock.lock();
            try {
                banco.transferir(contaCliente, ultimaLojaEscolhida.getContaLoja(), valorCompra);

                // o print a baixo mostra o fluxo das threads entre cliente e loja
                System.out.println(getThreadName() + " - " + contaCliente.getNome() + " realizou uma compra de R$" + valorCompra + " na " + ultimaLojaEscolhida.getNome() + ". (Saldo atual - "+nome+": R$"+ getContaCliente().getSaldo()+")");
            } finally {
                lock.unlock();
            }
        }
        System.out.println(getThreadName() + " ( " + nome + " ) terminou suas compras.\n");
    }

    public Conta getContaCliente() {
        return contaCliente;
    }

    private static String getThreadName() {
        return Thread.currentThread().getName();
    }

    public String getNome() {
        return nome;
    }
}
