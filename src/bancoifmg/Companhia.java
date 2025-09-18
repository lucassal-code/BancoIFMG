package bancoifmg;

import java.util.Scanner;

public class Companhia extends Casa{
    
    private final int compra;
    private int aluguel;
    private Jogador dono;
    Scanner entrada = new Scanner(System.in);
    
    public Companhia(String nome, int compra, int aluguel){
        super(nome);
        this.compra = compra;
        this.aluguel = aluguel;
    }
    
    @Override
    public void acao(Jogador naCasa) {
        System.out.println("Bem Vindo(a) a " + this.nome + ", Sr(a) " + naCasa.getNome() + '!');
        if (this.dono == null) {
            String resposta = "";
            do {
                System.out.println("Essa companhia nao possui dono. Deseja compra-la? (S/N): ");
                resposta = entrada.nextLine();
            } 
            while (!resposta.equalsIgnoreCase("S") && !resposta.equalsIgnoreCase("N"));

            if (resposta.equalsIgnoreCase("S")) {
                if (naCasa.getSaldo() >= this.compra) {
                    naCasa.setSaldo(naCasa.getSaldo() - this.compra);
                    this.dono = naCasa;
                    naCasa.registrarCompanhia(this);
                    System.out.println(naCasa.getNome() + " adquiriu a " + this.nome);
                }
                else{
                    System.out.println("Voce nao possui Saldo Suficiente para essa Operacao");
                }
            } 
            else {
                System.out.println("A compra nao foi realizada.");
            }
        } 
        else if (naCasa.equals(this.dono)) {
            System.out.println("Seja Bem-Vindo de Volta, Sr(a) + " + this.dono.getNome() + ", Aproveite a estadia!");
        } 
        else {
            System.out.println("Voce esta numa companhia que pertence a " + this.dono.getNome() 
                + " e devera pagar um aluguel pela estadia!");
            int dados = naCasa.jogarDados();
            int valor = dados * this.aluguel;
            System.out.println(naCasa.getNome() + " tirou " + dados 
                + " nos dados e pagou uma taxa de " + valor + " reais para " + this.dono.getNome() + '!');
            naCasa.setSaldo(naCasa.getSaldo() - valor);
            this.dono.setSaldo(this.dono.getSaldo() + valor);
        }
    }
    public void setAluguel(int aluguel) {
        this.aluguel = aluguel;
    }

    public int getCompra() {
        return compra;
    }

    public int getAluguel() {
        return aluguel;
    }

    public Jogador getDono() {
        return dono;
    }

    public void setDono(Jogador dono) {
        this.dono = dono;
    }  
}
