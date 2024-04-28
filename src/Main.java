package Projeto;

import Classes.Banco;
import Classes.Cliente;
import Classes.Funcionario;
import Classes.Loja;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        Banco banco = new Banco();
        Lock bancoLock = new ReentrantLock();

        Loja loja1 = new Loja("Loja 1");
        Loja loja2 = new Loja("Loja 2");

        Loja[] lojasDisponiveis = {loja1, loja2};

        Funcionario funcionario1 = new Funcionario("Caio", loja1, banco);
        Funcionario funcionario2 = new Funcionario("Júlia", loja1, banco);
        Funcionario funcionario3 = new Funcionario("Bento", loja2, banco);
        Funcionario funcionario4 = new Funcionario("Morgana", loja2, banco);

        List<Funcionario> funcionariosLoja1 = new ArrayList<>();
        funcionariosLoja1.add(funcionario1);
        funcionariosLoja1.add(funcionario2);

        List<Funcionario> funcionariosLoja2 = new ArrayList<>();
        funcionariosLoja2.add(funcionario3);
        funcionariosLoja2.add(funcionario4);

        Cliente cliente1 = new Cliente("cliente 1", lojasDisponiveis, banco, bancoLock);
        Cliente cliente2 = new Cliente("cliente 2", lojasDisponiveis, banco, bancoLock);
        Cliente cliente3 = new Cliente("cliente 3", lojasDisponiveis, banco, bancoLock);
        Cliente cliente4 = new Cliente("cliente 4", lojasDisponiveis, banco, bancoLock);
        Cliente cliente5 = new Cliente("cliente 5", lojasDisponiveis, banco, bancoLock);
        cliente1.start();
        cliente2.start();
        cliente3.start();
        cliente4.start();
        cliente5.start();

        try {
            cliente1.join();
            cliente2.join();
            cliente3.join();
            cliente4.join();
            cliente5.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("---------------------------------------------------------------------------------------");
      System.out.println(" (Thread Funcionarios) - Realizando pagamentos das Lojas aos Funcionários e investindo em Fundos ");
        System.out.println("-------------------------------------------------------------------------------------\n");
        funcionario1.start();
        funcionario2.start();
        funcionario3.start();
        funcionario4.start();

        try {
            funcionario1.join();
            funcionario2.join();
            funcionario3.join();
            funcionario4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n--------------------------------");
        System.out.println("             Saldo final          ");
        System.out.println("----------------------------------");

        System.out.println("Conta dos clientes:");
        System.out.println("- " + cliente1.getNome() + ": " + cliente1.getContaCliente().getSaldo());
        System.out.println("- " + cliente2.getNome() + ": " + cliente2.getContaCliente().getSaldo());
        System.out.println("- " + cliente3.getNome() + ": " + cliente3.getContaCliente().getSaldo());
        System.out.println("- " + cliente4.getNome() + ": " + cliente4.getContaCliente().getSaldo());
        System.out.println("- " + cliente5.getNome() + ": " + cliente5.getContaCliente().getSaldo());
        System.out.println();

        System.out.println("Conta das Lojas:");
        System.out.println("- " + loja1.getNome() + ": R$" + loja1.getContaLoja().getSaldo());
        System.out.println("- " + loja2.getNome() + ": R$" + loja2.getContaLoja().getSaldo());

        System.out.println("\nConta dos Funcionários:\nLoja 01:");

        for (Funcionario funcionario : funcionariosLoja1) {
            System.out.println(" Conta do salário(líquido) " + funcionario.getNome() + ": R$" + funcionario.getContaSalario().getSaldo());
            System.out.println(" Conta de investimento" + funcionario.getNome() + ": R$" + funcionario.getContaInvestimento().getSaldo());
        }
        System.out.println("\nLoja 02:");
        for (Funcionario funcionario : funcionariosLoja2) {
            System.out.println("Conta do salário(líquido) " + funcionario.getNome() + ": R$" + funcionario.getContaSalario().getSaldo());
            System.out.println("Conta de investimento " + funcionario.getNome() + ": R$" + funcionario.getContaInvestimento().getSaldo());
        }
    }
}
