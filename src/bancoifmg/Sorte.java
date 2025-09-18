package bancoifmg;

import java.util.Random;

public class Sorte extends Casa {
    
    public Sorte(String nome){
        super(nome);
    }
    
    @Override
    public void acao(Jogador naCasa) {
        Random random = new Random();
        int valorSorteado = random.nextInt(-180, 251);
        naCasa.setSaldo(naCasa.getSaldo() + valorSorteado);
        if (valorSorteado < 0){
            System.out.println(naCasa.getNome() + " perdeu " + (valorSorteado * 1) + " reais!");
        }
        else{
            System.out.println(naCasa.getNome() + " ganhou " + valorSorteado + " reais!");
        }
    }
}
