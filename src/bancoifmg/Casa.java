package bancoifmg;

public abstract class Casa {

    protected String nome;
    
    public Casa(String nome){
        this.nome = nome;
    }
    
    public abstract void acao(Jogador naCasa);
}
