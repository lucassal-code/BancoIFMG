package bancoifmg;

public class Especial extends Casa{

    private final int posicao;
    
    public Especial(String nome, int posicao){
        super(nome);
        this.posicao = posicao;
    }

    @Override
    public void acao(Jogador naCasa) {
        switch (this.posicao) {
            //O Jogador pisou na Cadeia e é preso
            case 11:
                if (naCasa.getPosicaoAnterior() != this.posicao){
                    System.out.println(naCasa.getNome() + " foi PRESO");
                    naCasa.setPreso(true);
                    naCasa.setTurnosNaPrisao(3);
                }
                break;
            //Lucros ou Dividendos
            case 19:
                naCasa.setSaldo(naCasa.getSaldo() + 200);
                System.out.println(naCasa.getNome() + " lucrou R$200");
                break;
            //O Jogador pisou na Parada Livre    
            case 21:
                System.out.println("Parada Livre! Aproveite seu descanso " + naCasa.getNome() + "!");
                break;
            //O Jogador deverá pagar Imposto de Renda
            case 25:
                if(naCasa.getSaldo() >= 200){
                    naCasa.setSaldo(naCasa.getSaldo() - 200);
                    System.out.println(naCasa.getNome() + " pagou R$200 de imposto");
                }
                break;
            //O Jogador pode ser enviado à Cadeia
            case 31:
                naCasa.setPosicao(11);
                System.err.println(naCasa.getNome() + " foi PRESO");
                naCasa.setPreso(true);
                naCasa.setTurnosNaPrisao(3);
                break;             
        }
    }  
}
