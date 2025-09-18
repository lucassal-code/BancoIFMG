package bancoifmg;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Jogo {

    private final ArrayList<Jogador> jogadores;
    private final ArrayList<Casa> tabuleiro;
    private int vez;
    Scanner entrada = new Scanner(System.in);

    public Jogo() {
        this.jogadores = new ArrayList<>();
        this.tabuleiro = new ArrayList<>();
        this.vez = 0;
    }

    public void criarTabuleiro() {
        this.tabuleiro.add(new Especial("Inicio", 1));
        this.tabuleiro.add(new Propriedade("Leblon", 80, 8, 24, 240));
        this.tabuleiro.add(new Sorte("Cassino"));
        this.tabuleiro.add(new Propriedade("Av. Presidente Vargas", 80, 6, 18, 180));
        this.tabuleiro.add(new Propriedade("Av. Nossa Senhora de Copacabana", 80, 7, 21, 210));
        this.tabuleiro.add(new Companhia("Ferroviaria", 200, 50));
        this.tabuleiro.add(new Propriedade("Av. Brigadeiro Faria Lima", 110, 13, 39, 390));
        this.tabuleiro.add(new Companhia("Viacao", 200, 40));
        this.tabuleiro.add(new Propriedade("Av. Reboucas", 110, 11, 33, 330));
        this.tabuleiro.add(new Propriedade("Av. 9 de Julho", 110, 12, 36, 360));
        this.tabuleiro.add(new Especial("Cadeia", 11));
        this.tabuleiro.add(new Propriedade("Avenida Europa", 100, 10, 30, 300));
        this.tabuleiro.add(new Sorte("Tigrinho"));
        this.tabuleiro.add(new Propriedade("Rua Augusta", 100, 9, 27, 270));
        this.tabuleiro.add(new Propriedade("Avenida Pacaembu", 100, 10, 30, 300));

    }

    public void iniciarJogo() {

        
        int quantidade = 0;
        
        //Uso do Laço de Repetição e Try-Catch para garantia de entradas válidas
        do {
            try {
                System.out.print("Quantos jogadores vao participar (2 a 6): ");
                quantidade = entrada.nextInt();
                if (quantidade < 2 || quantidade > 6) {
                    System.out.println("\nQuantidade invalida! Devem haver de 2 a 6 jogadores!\n");
                }
                entrada.nextLine();
            } 
            catch (InputMismatchException e) {
                System.out.println("\nA resposta esperada e um NUMERO INTIERO entre 2 e 6!\n");
                entrada.nextLine();
            }
        } 
        while (quantidade < 2 || quantidade > 6);

        //Criação dos Jogadores
        for (int i = 0; i < quantidade; i++) {
            System.out.print("\nInforme seu nome, Jogador [" + (i + 1) + "]: ");
            String nome = entrada.nextLine();
            this.jogadores.add(new Jogador(nome));
        }
        
        //Carregamento das Casas do Tabuleiro
        criarTabuleiro();
        
        //CÓDIGO PRINCIPAL
        while (this.jogadores.size() > 1) {
            
            //O jogo dura até só sobrar 1 jogador em condições estáveis
            //Jogadores falidos são removidas para maior dinamicidade
            for (int i = this.jogadores.size() - 1; i >= 0; i--) {
                if (this.jogadores.get(i).getSaldo() == 0) {
                    System.out.println(this.jogadores.get(i).getNome() + " faliu!");
                    this.jogadores.remove(i);
                }
            }
            
            //Seleciona o Jogador do Turno
            Jogador daVez = this.jogadores.get(this.vez);
            System.out.println("\nE a vez de " + daVez.getNome());
            
            //Verifica se o Jogador está preso
          
            //Se estiver, pode escolher entre tentar se libertar ou perder a vez
            if (daVez.isPreso()){
                System.out.println("\n" + daVez.getNome() + " esta PRESO!\n");
                
                System.out.println(
                    """
                    ===== OPCOES =====     
                    1- Tirar a Sorte (Se Liberta com Dados Iguais)
                    2- Pagar Fianca (R$ 50)
                    3- Esperar ser solto
                    """);
                
                //Entrada de Dados com tratamento de exceções
                int escolha = 0;
                do{
                    try{
                        System.out.print("Informe sua Escolha: ");  
                        escolha = entrada.nextInt();
                    }
                    catch(InputMismatchException e){
                        System.out.println("\nA resposta esperada e um NUMERO INTEIRO (1, 2 ou 3)!");
                    }
                    if (escolha < 1 || escolha > 3){
                        System.out.println("\nRESPOSTA INVALIDA! Espera-se APENAS 1, 2 ou 3!");
                    }
                }
                while(escolha < 1 || escolha > 3);
               
                switch (escolha) {
                    case 1:
                        //O Jogador pode tentar "tirar a sorte" para ser solto
                        
                        int dados = daVez.jogarDados();
                        //Se rolar 2 dados de mesmo número, é solto
                        if (daVez.isDadosIguais()){
                            System.out.println(daVez.getNome() + " tirou Dados Iguais "
                                + "(" + dados / 2 + ") e se LIBERTOU da Prisao!");
                            daVez.setTurnosNaPrisao(0);
                            
                            //Após a soltura, joga normalmente
                            daVez.mover(dados, this.tabuleiro);
                            daVez.setDadosIguais(false);
                            int posicao = daVez.getPosicao();
                            posicao--;
                            this.tabuleiro.get(posicao).acao(daVez);
                        }
                        //Se não tirar 2 dados iguais, permanece preso
                        else{
                            daVez.setTurnosNaPrisao(daVez.getTurnosNaPrisao() - 1);
                            System.out.println(daVez.getNome() + " tirou Dados Diferentes"
                                + " e PERMANECERA na Prisao por mais " + daVez.getTurnosNaPrisao() 
                                + " turnos!");
                        }  
                        break;
                    case 2:
                        //O Jogador também pode pagar sua fiança para ser solto
                        System.out.println(daVez.getNome() + " PAGOU sua fiança e se LIBERTOU da Prisao!");
                        //Desconta a taxa do Saldo do Jogador e joga seu turno normalmente
                        daVez.setSaldo(daVez.getSaldo() - 50);
                        daVez.setTurnosNaPrisao(0);
                        daVez.mover(daVez.jogarDados(), this.tabuleiro);
                        daVez.setDadosIguais(false);
                        int posicao = daVez.getPosicao();
                        posicao--;
                        this.tabuleiro.get(posicao).acao(daVez);
                        break;
                    case 3:
                        //Se o Jogador não escolher esssas, perde o turno. A prisão dura 3 turnos no máximo
                        System.out.println(daVez.getNome() + " PERMANECEU na Prisao e perdeu o turno!");
                        daVez.setTurnosNaPrisao(daVez.getTurnosNaPrisao() - 1);
                        System.out.println("Turnos ate ser Liberto: " + daVez.getTurnosNaPrisao());
                        break;
                    default:
                        break;
                }
            }
            //Se o Jogador estiver solto, joga seu turno normalmente
            else{
                daVez.mover(daVez.jogarDados(), this.tabuleiro);
                daVez.setDadosIguais(false);
                int posicao = daVez.getPosicao();
                posicao--;
                this.tabuleiro.get(posicao).acao(daVez);
            }
            
            //Revezamento de Turnos
            this.vez++;
            if (this.vez == this.jogadores.size()) {
                this.vez = 0;
            }
        }
    }
}
