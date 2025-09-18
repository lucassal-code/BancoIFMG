
package bancoifmg;

import java.util.Random;
import java.util.ArrayList;

public class Jogador {
    private final String nome;
    private int saldo;
    private final ArrayList<Propriedade> propriedades;
    private final ArrayList<Companhia> companhias;
    private int posicao;
    private boolean preso;
    private boolean dadosIguais;
    private int turnosNaPrisao;
    private int posicaoAnterior;
    
    public Jogador(String nome){
        this.nome = nome;
        this.saldo = 1500;
        this.posicao = 1;
        this.propriedades = new ArrayList<>();
        this.companhias = new ArrayList<>();
        this.preso = false;
        this.dadosIguais = false;
        this.turnosNaPrisao = 0;
    }
    
    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public String getNome() {
        return nome;
    }

    public int getSaldo() {
        return saldo;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public void setPreso(boolean preso) {
        this.preso = preso;
    }

    public boolean isPreso() {
        return preso;
    }

    public int getPosicaoAnterior() {
        return posicaoAnterior;
    }

    public int getTurnosNaPrisao() {
        return turnosNaPrisao;
    }

    public void setTurnosNaPrisao(int turnosNaPrisao) {
        this.turnosNaPrisao = turnosNaPrisao;
    }

    public ArrayList<Propriedade> getPropriedades() {
        return propriedades;
    }
    
    
    public void preso(){
        if (this.isPreso()){
            this.turnosNaPrisao = 3;
        }
    }
    
    public int jogarDados(){
        Random random = new Random();
        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;
        int soma = dado1 + dado2;
        
        if (dado1 == dado2){
            this.dadosIguais = true;
        }
        return soma;
    }

    public boolean isDadosIguais() {
        return dadosIguais;
    }
   
    public void mover(int passosParaAndar, ArrayList<Casa> tabuleiro){
        int tamanhoTabuleiro = tabuleiro.size();
        //Soma a posição atual do Jogador ao número tirado no Dado, simulando movimento
        //Armazena a posicao que ele estava antes de rolar os dados
        posicaoAnterior = this.posicao;
        //Move o Jogador no Tabuleiro
        this.posicao += passosParaAndar;
        //Se o destino estiver "fora" do Tabuleiro, anda o restante do movimento a partir do início
        if (this.posicao > tamanhoTabuleiro){
            //Como ele terá dado uma volta pelo Tabuleiro, recebe 200 reais de bonificação
            this.posicao -= tamanhoTabuleiro;
            this.saldo += 200;
        }
        System.out.println(this.nome + " rolou os dados e deu " + passosParaAndar + " passos, "
            + "indo da Casa " + posicaoAnterior + " para a " + this.posicao);
    }
    
    //Métodos para armazenar Propriedades/Companhias que o Jogador se torna dono
    public void registrarPropriedade(Propriedade novaPropriedade){
        this.propriedades.add(novaPropriedade);
    }
    public void registrarCompanhia(Companhia novaCompanhia){
        this.companhias.add(novaCompanhia);
    }

    public void setDadosIguais(boolean dadosIguais) {
        this.dadosIguais = dadosIguais;
    }
}
