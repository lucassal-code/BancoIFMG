package bancoifmg;

import java.util.Scanner;

public class Propriedade extends Casa {

    private final int compra;
    private int aluguel;
    private boolean isPousada;
    private boolean isHotel;
    private final int pousada;
    private final int hotel;
    private Jogador dono;
    Scanner entrada = new Scanner(System.in);

    public Propriedade(String nome, int compra, int aluguel, int pousada, int hotel) {
        super(nome);
        this.aluguel = aluguel;
        this.compra = compra;
        this.pousada = pousada;
        this.hotel = hotel;
    }

    @Override
    public void acao(Jogador naCasa) {
        //Mostra que alguem pisou na casa
        System.out.println("\nBem Vindo(a) a " + this.nome + ", Sr(a). " + naCasa.getNome() + "\n");
        //Verifica se o Jogador da Vez é o dono da Propriedade
        if (this.dono != null && this.dono.equals(naCasa)) {
            //Se ele for, verifica se a Propriedade é um Hotel
            if (!this.isHotel) {
                //Se não for, ela pode ser aprimorada
                
                //Laço de Repetição para evitar entradas inválidas
                String resposta = "";
                do{
                    System.out.println("Ola " + naCasa.getNome()
                        + " Deseja melhorar sua propriedade em " + this.nome + "? (S/N): ");
                    resposta = entrada.nextLine();
                }
                while (!resposta.equalsIgnoreCase("S") && !resposta.equalsIgnoreCase("N"));
                //Ignora a entrada de letras minúsculas ou maiúsculas
                if (resposta.equalsIgnoreCase("S")) {
                    //Verifica se o Jogador tem como pagar pela melhoria
                    
                    //Se sim, aumenta o aluguel da Propriedade a depender de seu nível
                    if (naCasa.getSaldo() >= this.aluguel) {
                        if (this.isPousada) {
                            this.aluguel = this.hotel;
                            System.out.println("\nSua Pousada em " + this.nome
                                + " foi aprimorada e se tornou um Hotel!\n");
                        } 
                        else {
                            this.aluguel = this.pousada;
                            System.out.println("\nSua Propriedade em " + this.nome
                                    + " se tornou uma Pousada!\n");
                        }
                    } 
                    //Alerta o Jogador de Saldo Insuficiente se for o caso
                    else {
                        System.out.println("""
                            Voce nao tem saldo suficiente para aprimorar essa Propriedade
                        """);
                    }
                } 
                //Se a resposta do Jogador for "Não", encerra a Ação
                else {
                    System.out.println("\nNenhuma modificacao foi feita em sua Propriedade!\n");
                }
            } 
            //Se a Propriedade é um Hotel, seu aluguel não pode mais ser alterado
            else {
                System.out.println("""
                    Voce retornou a um de seus Hoteis! Hoteis nao podem ser aprimorados, aproveite a estadia!
                """);
            }
        } 
        //Se a Propriedade tiver dono, mas não for o Jogador, ele paga aluguel ao dono
        else if (this.dono != null && !naCasa.equals(this.dono)) {
            System.out.println("\nOla, " + naCasa.getNome()
                + "! Voce esta na Propriedade de " + this.dono.getNome()
                + " e tera que pagar um aluguel de " + this.aluguel + " reais pela estadia!");
                //Desconta o valor do aluguel do Saldo do Jogador
                naCasa.setSaldo(naCasa.getSaldo() - this.aluguel);
                //Adiciona esse valor ao Saldo do Dono da Propriedade
                this.dono.setSaldo(this.dono.getSaldo() + this.aluguel);
        }
        //Se a Propriedade não tiver dono, o Jogador pode comprá-la
        else{
            System.out.println("\nAqui ha uma propriedade que nao possui dono!\n");
            String escolha;
            do{
                System.out.print("\nDeseja adquiri-la? (S/N): ");
                escolha = entrada.nextLine();
                if (!escolha.equalsIgnoreCase("S") && !escolha.equalsIgnoreCase("N")){
                    System.out.println("\nResposta Invalida! Digite S (Sim) ou N (Nao)!\n");
                }
            }
            while(!escolha.equalsIgnoreCase("S") && !escolha.equalsIgnoreCase("N"));
            
            //Se o Jogador responder "Sim", o código verifica seu Saldo
            if (escolha.equalsIgnoreCase("S")){
                //Se houver Saldo o suficiente, ele compra a Propriedade
                if (naCasa.getSaldo() >= this.compra){
                    naCasa.setSaldo(naCasa.getSaldo() - this.compra);
                    this.setDono(naCasa);
                    naCasa.registrarPropriedade(this);
                    System.out.println("\n" + naCasa.getNome() + " adquiriu uma Propriedade "
                        + " no(a) " + this.nome + "\n");
                }
                //Se não, é alertado
                else{
                    System.out.println("\nVoce nao possui Saldo Suficiente para esta operacao!\n");
                }
            }
            //Se não desejar comprar, passa a vez
            else{
                System.out.println("\nAproveite o Passeio!\n");
            }
        }
    }

    public void setDono(Jogador dono) {
        this.dono = dono;
    }

}
